package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JComboBoxAdapter;
import fr.univ.tln.projet.planning.ihm.components.SearchBox;
import fr.univ.tln.projet.planning.modele.infrastructure.Batiment;
import fr.univ.tln.projet.planning.modele.infrastructure.Salle;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class InfrastructuresVue extends JPanel {


    private AbstractControler controler;
    private  JList batiments=new JList();
    private JList salles;
    InfrastructuresVue(AbstractControler controler ){
        super();
        this.controler =controler;
        this.setLayout(new BorderLayout());
        SearchBox creer =new SearchBox("créer");
        JComboBox  infrastructure = new JComboBox ( new String[]{"Batiment", "Salle"});
        JPanel jHeader =new JPanel();
        jHeader.add(infrastructure);
        jHeader.add(creer);
        creer.getSearchB().addActionListener(actionEvent -> {
             if(infrastructure.getSelectedItem().equals("Batiment")){
                 if( Pattern.compile("[A-Z]{1,2}[0-9]{0,2}").matcher(creer.getSearchable().getText()).matches()){
                     JSONObject response= controler.addBatiment(creer.getSearchable().getText());
                     switch ((int)response.get("code")){
                         case 200:
                             JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);

                         this.setBatiments((ArrayList)controler.selectBatiments());
                             break;
                         case 409:
                             JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                         case 500:
                             JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                     }

                 }
                 else JOptionPane.showMessageDialog(new JFrame(), "veuillez respectez le format","erreur", JOptionPane.INFORMATION_MESSAGE);
             }
             else if (batiments.getSelectedValue()==null)
                 JOptionPane.showMessageDialog(new JFrame(), "il faut choisir un batiment","erreur", JOptionPane.INFORMATION_MESSAGE);

             else if (!Pattern.compile("[0-9]{1,3}").matcher(creer.getSearchable().getText()).matches())
                 JOptionPane.showMessageDialog(new JFrame(), "veuillez respectez le format","erreur", JOptionPane.INFORMATION_MESSAGE);
             else{
                 JSONObject response=    controler.addSalle(creer.getSearchable().getText(), (String) batiments.getSelectedValue());

                 switch ((int)response.get("code")){
                     case 200:
                         JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                         this.setSalles((ArrayList)controler.selectSalles((String) batiments.getSelectedValue()));
                         break;
                     case 409:
                         JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                     case 500:
                         JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                 }
             }
        });


        //List batiments
        JPanel panelEst=new JPanel();
        this.setBatiments((ArrayList)controler.selectBatiments());
        panelEst.setLayout(new BoxLayout(panelEst, BoxLayout.PAGE_AXIS));
        batiments.setPreferredSize(new Dimension(300,1000));
        batiments.setSize(new Dimension(300,1000));
        batiments.setMinimumSize(new Dimension(300,1000));
        batiments.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setSalles((ArrayList)controler.selectSalles((String) batiments.getSelectedValue()));
            }
        });
        JScrollPane sp1 = new JScrollPane(batiments);
        JButton supprimerBatiment=new JButton("Supprimer");
        supprimerBatiment.addActionListener((actionEvent) -> {
            Object selection =  batiments.getSelectedValue();
            if(selection==null)  JOptionPane.showMessageDialog(new JFrame(),"Veuillez selectionnez un batiment", "error", JOptionPane.INFORMATION_MESSAGE);

            else{
                JSONObject response=this.controler.deleteBatiment((String)selection);
                switch ((int)response.get("code")){
                    case 200:
                        JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                        this.setBatiments((ArrayList)controler.selectBatiments());
                        break;
                    case 500:
                        JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                }
            }



        });
        panelEst.add(new JLabel("Batiments"));
        panelEst.add(sp1);
        panelEst.add(supprimerBatiment);

        //Gestion des salles
        JPanel panelOuest=new JPanel();
        panelOuest.setLayout(new BoxLayout(panelOuest, BoxLayout.PAGE_AXIS));
        salles=new JList();

        salles.setPreferredSize(new Dimension(300,1000));
        salles.setSize(new Dimension(300,1000));
        salles.setMinimumSize(new Dimension(300,1000));
        JScrollPane sp2 = new JScrollPane(salles);
        sp2.setSize(new Dimension(300,700));
        JButton supprimerSalle=new JButton("Supprimer");
        supprimerSalle.addActionListener((actionEvent) -> {
        });
        panelOuest.add(new JLabel("Salles"));
        panelOuest.add(sp2);
        panelOuest.add(supprimerSalle);



        this.add(jHeader,BorderLayout.NORTH);
        this.add(panelEst,BorderLayout.CENTER);

        this.add(panelOuest,BorderLayout.EAST);


    }

    public void setBatiments(ArrayList<Batiment> batiments){

        DefaultListModel<String> lstBatiments = new DefaultListModel();
        for(Batiment batiment : batiments ){
            lstBatiments.addElement(batiment.getIdentifiant());
        }

        this.batiments.setModel(lstBatiments);

    }
    public void setSalles(ArrayList<Salle> Salles ){

        DefaultListModel<String> lstBatiments = new DefaultListModel();
        for(Salle salle : Salles){
            lstBatiments.addElement(salle.getIdentifiant());
        }

        this.salles.setModel(lstBatiments);

    }





}
