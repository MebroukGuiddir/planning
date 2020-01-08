package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.*;
import fr.univ.tln.projet.planning.modele.etudes.Cours;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import fr.univ.tln.projet.planning.modele.utilisateurs.Enseignant;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddSeanceVue extends JPanel {
    private AbstractControler controler;

    private JComboBox cours;
    private List<Cours> coursList;
    private JdatePickerAdapter date;
    private JSpinnerHour dateDebut ;
    private JSpinnerHour dateFin ;
    private JButton submit;

    List<Batiment> batimentsList;
    List<Salle> sallesList;
    private JComboBox batiments;
    private JComboBox salles;
    public AddSeanceVue(AbstractControler controler, Enseignant enseignant) {
        super();
        this.controler=controler;
        this.setLayout(new BorderLayout());
        GridLayout gridLayout=new GridLayout(6, 2);
        gridLayout.setHgap(8);
        gridLayout.setVgap(4);

        JPanel panelForm=new JPanel(gridLayout);
        panelForm.setBorder(BorderFactory.createRaisedBevelBorder());
        panelForm.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel coursLabel = new JLabel("Cours");
        coursLabel.setFont(new Font("Arial", Font.BOLD, 12));
        coursLabel.setSize(100, 20);
        coursLabel.setPreferredSize(new Dimension(100, 20));

        coursList=controler.selectCours(enseignant.getIdEnseignant());
        cours =new JComboBox( coursList.toArray());
        cours.setFont(new Font("Arial", Font.BOLD, 13));
        cours.setSize(200, 30);
        cours.setPreferredSize(new Dimension(200,30));
        cours.setMinimumSize(new Dimension(200,30));
        cours.setOpaque(false);
        panelForm.add(coursLabel);
        JPanel coursPanel=new JPanel();
        coursPanel.add(cours);
        panelForm.add(coursPanel);


        JLabel dateLabel = new JLabel("Date");
        dateLabel.setFont(new Font("Arial", Font.BOLD, 12));
        dateLabel.setSize(100, 20);
        dateLabel.setPreferredSize(new Dimension(100, 20));
        Calendar calendar = Calendar.getInstance();
        date =new JdatePickerAdapter("",Integer.valueOf(new SimpleDateFormat("yyyy").format(calendar.getTime())),
                Integer.valueOf(new SimpleDateFormat("MM").format(calendar.getTime())),
                Integer.valueOf(new SimpleDateFormat("dd").format(calendar.getTime())));


        panelForm.add(dateLabel);
        panelForm.add(date);

        JLabel heureDebutLabel = new JLabel("Heure debut");
        heureDebutLabel.setFont(new Font("Arial", Font.BOLD, 12));
        heureDebutLabel.setSize(100, 20);
        heureDebutLabel.setPreferredSize(new Dimension(100, 20));
        dateDebut =new JSpinnerHour();


        JLabel heureFinLabel = new JLabel("Heure fin");
        heureFinLabel.setFont(new Font("Arial", Font.BOLD, 12));
        heureFinLabel.setSize(100, 20);
        heureFinLabel.setPreferredSize(new Dimension(100, 20));

        dateFin =new JSpinnerHour();

        panelForm.add(heureDebutLabel);
        panelForm.add(heureFinLabel);
        panelForm.add(dateDebut);
        panelForm.add(dateFin);

        batimentsList=controler.selectBatiments();
        batiments=new JComboBox( batimentsList.toArray());
        batiments.setSize(200, 30);
        batiments.setPreferredSize(new Dimension(200,30));
        batiments.setMinimumSize(new Dimension(200,30));
        salles=new JComboBox();
        salles.setSize(200, 30);
        salles.setPreferredSize(new Dimension(200,30));
        salles.setMinimumSize(new Dimension(200,30));
        batiments.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sallesList=controler.selectSalles(
                       batimentsList.get(batiments.getSelectedIndex()).getIdentifiant());
                System.out.println(sallesList);
                salles.setModel(new DefaultComboBoxModel(sallesList.toArray()));
            }});

        JLabel batimentLabel = new JLabel("Batiment");
        batimentLabel.setFont(new Font("Arial", Font.BOLD, 12));
        batimentLabel.setSize(100, 20);
        batimentLabel.setPreferredSize(new Dimension(100, 20));


        JLabel salleLabel = new JLabel("Salle");
        salleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        salleLabel.setSize(100, 20);
        salleLabel.setPreferredSize(new Dimension(100, 20));
        panelForm.add(batimentLabel);

        JPanel batimentPanel=new JPanel();
        batimentPanel.add(batiments);
        panelForm.add(batimentPanel);
        panelForm.add(salleLabel);
        JPanel sallePanel=new JPanel();
        sallePanel.add(salles);
        panelForm.add(sallePanel);
       submit =new JButtonAdapter("Enregistrer");
       this.add(panelForm,BorderLayout.CENTER);
       this.add(new JLabelAdapter("Ajouter une séance"),BorderLayout.NORTH);
       this.add(submit,BorderLayout.SOUTH);

       submit.addActionListener(e -> {
           String error="";
           if( batiments.getSelectedIndex()==-1 || salles.getSelectedIndex()==-1 ) error ="Veuillez selectionner une salle";
           if(new Date().getTime()-24*3600 >date.getDate().getTime() )error="Veuillez choisir une date";
           if( cours.getSelectedIndex()==-1 )error="Veuillez selectionner un cours";
           if(dateDebut.getValue() .compareTo(dateFin.getValue())>=0 )error="L'heure de debut doit étre inférieure à l'heure de fin";
           System.out.println(error);
           System.out.println(dateDebut.getValue());
           System.out.println(dateFin.getValue());

           if(!error.equals(""))
               JOptionPane.showMessageDialog(new JFrame(),error, "Erreur", JOptionPane.ERROR_MESSAGE);
           else{
               JSONObject response= controler.addSeance( LocalTime.of(Integer.valueOf( dateDebut.getValue().split("\\:")[0]),Integer.valueOf(  dateDebut.getValue().split("\\:")[1])),
                         LocalTime.of(Integer.valueOf( dateFin.getValue().split("\\:")[0]),Integer.valueOf(  dateFin.getValue().split("\\:")[1]))
                       ,date.getDate(),sallesList.get(salles.getSelectedIndex()).getIdSalle(),coursList.get(cours.getSelectedIndex()).getIdCours(),enseignant.getIdEnseignant());

               if(response!=null)
                   switch ((int)response.get("code")){
                       case 200:
                           JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                           break;
                       case 409:
                           JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                       case 500:
                           JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                   }
           }
       });
    }


}
