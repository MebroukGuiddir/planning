package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.ihm.components.jTable.*;
import fr.univ.tln.projet.planning.modele.AdminModele;
import fr.univ.tln.projet.planning.modele.etudes.*;
import fr.univ.tln.projet.planning.modele.observer.Observer;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.regex.Pattern;

public class EtudesVue extends JPanel implements Observer {

    private AbstractControler controler;
    private AdminModele modele;
    private ModeleDynamiqueObject domainesModel=new JTableDomaine(new String[]{"Domaine"});
    private ModeleDynamiqueObject formationsModel=new JTableFormation(new String[]{"Formation","Niveau"});
    private ModeleDynamiqueObject promotionsModel=new JTablePromotion(new String[]{"Promotion"});
    private ModeleDynamiqueObject sectionsModel=new JTableSection(new String[]{"Section"});
    private ModeleDynamiqueObject groupesModel=new JTableGroupe(new String[]{"Groupe"});
    private JTable domaines;
    private JTable formations;
    private JTable promotions;
    private JTable sections;
    private JTable groupes;

    public EtudesVue(AbstractControler controler,AdminModele modele) {
        super();
        this.controler = controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.modele=modele;
        modele.addObserver(this);

        domainesModel.updateModel(controler.selectDomaines());
        domaines=new JTable(domainesModel);
        formations=new JTable(formationsModel);
        promotions=new JTable(promotionsModel);
        sections=new JTable(sectionsModel);
        groupes=new JTable(groupesModel);
        domaines.setPreferredSize(new Dimension(300,400));
        domaines.setPreferredScrollableViewportSize(domaines.getPreferredSize());
        domaines.setFillsViewportHeight(true);
        formations.setPreferredSize(new Dimension(300,400));
        formations .setPreferredScrollableViewportSize(formations.getPreferredSize());
        formations.setFillsViewportHeight(true);
        promotions.setPreferredSize(new Dimension(100,400));
        promotions.setPreferredScrollableViewportSize(promotions.getPreferredSize());
        promotions.setFillsViewportHeight(true);
        sections.setPreferredSize(new Dimension(100,400));
        sections.setPreferredScrollableViewportSize(sections.getPreferredSize());
        sections.setFillsViewportHeight(true);
        groupes.setPreferredSize(new Dimension(100,400));
        groupes.setPreferredScrollableViewportSize(groupes.getPreferredSize());
        groupes.setFillsViewportHeight(true);
        domaines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        domaines.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = domaines.getSelectedRow();

                        if (viewRow < 0) {
                        } else {

                           formationsModel.updateModel( controler.selectFormations( ((Domaine)domainesModel.getRow(domaines.getSelectedRow())).getIdDomaine()));
                        }
                    }
                });
        formations.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = formations.getSelectedRow();
                        int viewColumn = formations.getSelectedColumn();
                        if (viewRow < 0) {
                        } else {
                            System.out.println(formationsModel.getValueAt(viewRow,viewColumn));

                        }
                    }
                });
        promotions.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = promotions.getSelectedRow();
                        int viewColumn = promotions.getSelectedColumn();
                        if (viewRow < 0) {
                        } else {
                            System.out.println(promotionsModel.getValueAt(viewRow,viewColumn));

                        }
                    }
                });
        sections.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = sections.getSelectedRow();
                        int viewColumn = sections.getSelectedColumn();
                        if (viewRow < 0) {
                        } else {
                            modele.selectGroupes((String)sectionsModel.getValueAt(viewRow,viewColumn));
                            System.out.println(sectionsModel.getValueAt(viewRow,viewColumn));

                        }
                    }
                });
        groupes.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        int viewRow = groupes.getSelectedRow();
                        int viewColumn = groupes.getSelectedColumn();
                        if (viewRow < 0) {
                        } else {
                            System.out.println(groupesModel.getValueAt(viewRow,viewColumn));

                        }
                    }
                });
        EtudesDefaultPanel addPanel=new EtudesDefaultPanel(controler);
        addPanel.getButton().addActionListener((actionEvent -> {
            if( Pattern.compile("^[\\p{L} ]+$").matcher( addPanel.getTextField().getText()).matches()){
                JSONObject response;
                switch ( addPanel.getComboBox().getSelectedItem().toString()){
                    case "Domaine": response=  controler.addDomaine(addPanel.getTextField().getText());break;
                    case "Formation":response=  controler.addFormation(addPanel.getTextField().getText(),addPanel.getNiveau().getSelectedItem().toString(),((Domaine)domainesModel.getRow(domaines.getSelectedRow())).getIdDomaine());
                    break;
                    case "Promotion":;
                    case "Section":;
                    case "Groupe":;
                    default:response=new JSONObject();
                }


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
            else JOptionPane.showMessageDialog(new JFrame(), "veuillez respectez le format","erreur", JOptionPane.INFORMATION_MESSAGE);

        }));
        JPanel listPanel=new JPanel();
        listPanel.add(new JScrollPane(domaines));
        listPanel.add(new JScrollPane(formations));
        listPanel.add(new JScrollPane(promotions));
        listPanel.add(new JScrollPane(sections));
        listPanel.add(new JScrollPane(groupes));
        listPanel.setSize(600,400);
        this.add(listPanel);
        this.add(addPanel);
    }



    @Override
    public void update(Object object, Changement changement) {
        System.out.println(changement);
        if(changement.type==Changement.Type.ADD)
         switch (changement.section){
            case DOMAINE:   domainesModel.updateModel(modele.selectDomaines());break;
             case FORMATION:
                 if (domaines.getSelectedRow() > 0)
                 formationsModel.updateModel( controler.selectFormations( ((Domaine)domainesModel.getRow(domaines.getSelectedRow())).getIdDomaine()));

             case PROMOTION:
            case SECTION:
             case GROUPE:
         }



    }
}
