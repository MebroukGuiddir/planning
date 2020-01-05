package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JButtonAdapter;
import fr.univ.tln.projet.planning.ihm.components.JLabelAdapter;
import fr.univ.tln.projet.planning.modele.etudes.Domaine;
import fr.univ.tln.projet.planning.modele.etudes.Formation;
import fr.univ.tln.projet.planning.modele.utilisateurs.Responsable;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ResponsableAffecterVue extends  JPanel{
    private AbstractControler controler;
    List<Formation> formations;

    JComboBox formation = new JComboBox();

    public ResponsableAffecterVue(AbstractControler controler,  Utilisateur utilisateur){
        this.controler=controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        JPanel userPanel=new JPanel(new GridLayout(4,1));
        userPanel.add(new JLabel(utilisateur.getPrenom()+" "+utilisateur.getNom()));
        userPanel.add(new JLabelAdapter("email:  "+utilisateur.getEmail()));
        userPanel.add(new JLabelAdapter("usename:  "+utilisateur.getUsername()));
        userPanel.add(new JLabelAdapter("adresse:  "+utilisateur.getAdresse()));

        this.add(userPanel);

        List<Domaine> domaines =controler.selectDomaines();

        JComboBox domaine = new JComboBox();
        for (Domaine d :domaines){
            domaine.addItem(d.toString());
        }

        domaine.setFont(new Font("Arial", Font.BOLD, 13));
        domaine.setSize(150, 25);
        domaine.setPreferredSize(new Dimension(150, 25));
        domaine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                formations=controler.selectFormations( Domaine.getDomaine(domaines,domaine.getSelectedItem().toString()).getIdDomaine());
                formation.setModel(new DefaultComboBoxModel(formations.toArray()));
            }});


        formation.setFont(new Font("Arial", Font.BOLD, 13));
        formation.setSize(150, 25);
        formation.setPreferredSize(new Dimension(150, 25));
        formation.setOpaque(false);

        JPanel affecterPanel=new JPanel(new GridLayout(3,2) );
        Border blackline = BorderFactory.createTitledBorder("Affecter");
        affecterPanel.setBorder(blackline);
        affecterPanel.add(new JLabelAdapter("Domaine"));
        JPanel pane1=new JPanel();
        pane1.add(domaine);
        affecterPanel.add(pane1);
        affecterPanel.add(new JLabelAdapter("Formation"));
        JPanel pane2=new JPanel();
        pane2.add(formation);
        affecterPanel.add(pane2);
        JButtonAdapter affecter=new JButtonAdapter("Affecter");
        JPanel pane5=new JPanel();
        pane5.add(affecter);
        affecter.addActionListener((actionEvent) -> {
            int idFormation=formation.getSelectedIndex();
            System.out.println(((Responsable)utilisateur).getIdResponsable()+"fffffff     ");
            if(idFormation!=-1)idFormation= formations.get(formation.getSelectedIndex()).getIdFormation();

            JSONObject response= controler.setResponsable((Responsable)utilisateur,idFormation);

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
        });
        affecterPanel.add(pane5);
        this.add(affecterPanel);

    }
}


