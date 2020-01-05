package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JButtonAdapter;
import fr.univ.tln.projet.planning.ihm.components.JLabelAdapter;
import fr.univ.tln.projet.planning.ihm.components.jTable.*;
import fr.univ.tln.projet.planning.modele.AdminModele;
import fr.univ.tln.projet.planning.modele.etudes.Module;
import fr.univ.tln.projet.planning.modele.utilisateurs.Enseignant;
import fr.univ.tln.projet.planning.modele.etudes.Module;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import org.json.simple.JSONObject;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


public class EnseignantAffecterVue extends JPanel {


    private AbstractControler controler;
    private AdminModele modele;

    private ModeleDynamiqueObject modulesModel=new JTableModule(new String[]{"Module","Identifiant"});
    private JTable modules;

    private ModeleDynamiqueObject coursModel=new JTableCours(new String[]{"Cours","Identifiant"});
    private JTable cours;


    public EnseignantAffecterVue(AbstractControler controler,  Utilisateur utilisateur){
        this.controler=controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        modulesModel.updateModel(controler.selectModules());
        coursModel.updateModel(controler.selectCours(((Enseignant)utilisateur).getIdEnseignant()));
        modules=new JTable(modulesModel);
        cours=new JTable(coursModel);
        JPanel userPanel=new JPanel(new GridLayout(4,1));
        userPanel.add(new JLabel(utilisateur.getPrenom()+" "+utilisateur.getNom()));
        userPanel.add(new JLabelAdapter("email:  "+utilisateur.getEmail()));
        userPanel.add(new JLabelAdapter("usename:  "+utilisateur.getUsername()));
        userPanel.add(new JLabelAdapter("adresse:  "+utilisateur.getAdresse()));
        this.add(userPanel);








        JPanel affecterPanel=new JPanel(new BorderLayout() );
        Border blackline = BorderFactory.createTitledBorder("Affecter");
        affecterPanel.setBorder(blackline);


        affecterPanel.add(new JScrollPane(modules),BorderLayout.WEST);

        JButtonAdapter affecter=new JButtonAdapter("   Affecter -->  ");
        JPanel pane3=new JPanel();
        pane3.add(affecter,BorderLayout.CENTER);
        affecter.addActionListener((actionEvent) -> {

            System.out.println(((Enseignant)utilisateur).getIdEnseignant());
            if(modules.getSelectedRow()!=-1) {
               int idModule= ((Module) modulesModel.getRow(modules.getSelectedRow())).getIdModule();
                JSONObject response= controler.addCours(((Enseignant)utilisateur).getIdEnseignant(),idModule);

                if(response!=null)
                    switch ((int)response.get("code")){
                        case 200:
                            JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                            coursModel.updateModel(controler.selectCours(((Enseignant)utilisateur).getIdEnseignant()));
                            break;
                        case 409:
                            JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                        case 500:
                            JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                    }
            }

            JSONObject response= null;

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
        affecterPanel.add(pane3);

        affecterPanel.add(new JScrollPane(cours),BorderLayout.EAST);
        this.add(affecterPanel);

    }
}

