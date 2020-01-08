package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JdatePickerAdapter;
import fr.univ.tln.projet.planning.ihm.components.jTable.JTablePlanningEnseignant;
import fr.univ.tln.projet.planning.ihm.components.jTable.ModeleDynamiqueObject;
import fr.univ.tln.projet.planning.ihm.vue.etudesVue.AddSeanceVue;
import fr.univ.tln.projet.planning.modele.etudes.Seance;
import fr.univ.tln.projet.planning.modele.utilisateurs.Enseignant;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PlanningEnseignantVue extends JPanel {

    private AbstractControler controler;
    private ModeleDynamiqueObject seancesModel=new JTablePlanningEnseignant(new String[]{"Date","Heure","Salle","Cours","Status"});
    private JTable seances;
    PlanningEnseignantVue(AbstractControler controler, Enseignant enseignant) {
        super();
        this.controler = controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        seancesModel.updateModel(controler.selectSeanceEnseignant(enseignant.getIdEnseignant(),new Date(),0));
        seances=new JTable(seancesModel);
        JPanel jHeader =new JPanel();
        JButton creer=new JButton("Ajouter une Séance");
        creer.addActionListener((actionEvent) -> {
            JPanel addSeancePanel=new AddSeanceVue(controler,enseignant);
            JFrame addSeanceFrame=new JFrame();
            addSeanceFrame.setSize(new Dimension(1000,500));
            addSeanceFrame.setContentPane(addSeancePanel);
            addSeanceFrame.setVisible(true);
        });
        JButton annuler=new JButton("Annuler une Séance");

        jHeader.add(creer);
        jHeader.add(annuler);

        JPanel controlePanel=new JPanel();
        JButton beffor=new JButton(" << ");

        Calendar calendar = Calendar.getInstance();
        JdatePickerAdapter date =new JdatePickerAdapter("",Integer.valueOf(new SimpleDateFormat("yyyy").format(calendar.getTime())),
                Integer.valueOf(new SimpleDateFormat("MM").format(calendar.getTime())),
                Integer.valueOf(new SimpleDateFormat("dd").format(calendar.getTime())));
        JButton after=new JButton(" >> ");

        JComboBox periode =new JComboBox(new String[]{"Jour", "Semaine", "Mois"});
        periode.setFont(new Font("Arial", Font.BOLD, 13));
        periode.setSize(200, 30);
        periode.setPreferredSize(new Dimension(200,30));
        periode.setMinimumSize(new Dimension(200,30));
        periode.setOpaque(false);
        periode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                seancesModel.updateModel(controler.selectSeanceEnseignant(enseignant.getIdEnseignant(),date.getDate(),periode.getSelectedIndex()));
            }});
        beffor.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                date.setDate(-1);
                 seancesModel.updateModel(controler.selectSeanceEnseignant(enseignant.getIdEnseignant(),date.getDate(),periode.getSelectedIndex()));
            }});
        after.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                date.setDate(1);
                  seancesModel.updateModel(controler.selectSeanceEnseignant(enseignant.getIdEnseignant(),date.getDate(),periode.getSelectedIndex()));
            }});
        date.getJDatePicker().addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        seancesModel.updateModel(controler.selectSeanceEnseignant(enseignant.getIdEnseignant(),date.getDate(),periode.getSelectedIndex()));

       }});
        annuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(seances.getSelectedRow()<0)
                    JOptionPane.showMessageDialog(new JFrame(),"Veuillez selectionner une séance", "Erreur", JOptionPane.ERROR_MESSAGE);
                else if(((Seance)seancesModel.getRow(seances.getSelectedRow())).getStatus()==-1 || ((Seance)seancesModel.getRow(seances.getSelectedRow())).getStatus()==1) {
                    JSONObject response=controler.annulerSeance(((Seance) seancesModel.getRow(seances.getSelectedRow())).getIdSeance());
                    switch ((int)response.get("code")){
                        case 200:
                            JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                            seancesModel.updateModel(controler.selectSeanceEnseignant(enseignant.getIdEnseignant(), date.getDate(), periode.getSelectedIndex()));
                            break;
                        case 500:
                            JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                    }}
                else  JOptionPane.showMessageDialog(new JFrame(),"La séance est déja annulé", "Erreur", JOptionPane.ERROR_MESSAGE);


                }
            });
        controlePanel.add(beffor);
        controlePanel.add(date);
        controlePanel.add(after);
        controlePanel.add(periode);

        this.add(jHeader);
        this.add(controlePanel);
        this.add(new JScrollPane(seances));
    }
}
