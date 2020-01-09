package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.ihm.components.jTable.*;
import fr.univ.tln.projet.planning.modele.ModeleClass;
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
    private ModeleClass modele;
    private ModeleDynamiqueObject domainesModel=new JTableDomaine(new String[]{"Domaine"});
    private ModeleDynamiqueObject formationsModel=new JTableFormation(new String[]{"Formation","Niveau"});
    private ModeleDynamiqueObject promotionsModel=new JTablePromotion(new String[]{"Promotion"});
    private ModeleDynamiqueObject sectionsModel=new JTableSection(new String[]{"Section"});
    private ModeleDynamiqueObject groupesModel=new JTableGroupe(new String[]{"Groupe"});
    private ModeleDynamiqueObject modulesModel=new JTableModule(new String[]{"Module","Identifiant"});
    private JTable domaines;
    private JTable formations;
    private JTable promotions;
    private JTable sections;
    private JTable groupes;
    private JTable modules;

    public EtudesVue(AbstractControler controler, ModeleClass modele) {
        super();
        this.controler = controler;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.modele=modele;
        modele.addObserver(this);
        EtudesDefaultPanel addPanel=new EtudesDefaultPanel(controler);
        domainesModel.updateModel(controler.selectDomaines());
        domaines=new JTable(domainesModel);
        formations=new JTable(formationsModel);
        promotions=new JTable(promotionsModel);
        sections=new JTable(sectionsModel);
        groupes=new JTable(groupesModel);
        modules=new JTable(modulesModel);
        domaines.setPreferredSize(new Dimension(300,400));
        domaines.setPreferredScrollableViewportSize(domaines.getPreferredSize());
        domaines.setFillsViewportHeight(true);
        formations.setPreferredSize(new Dimension(300,400));
        formations .setPreferredScrollableViewportSize(formations.getPreferredSize());
        formations.setFillsViewportHeight(true);
        promotions.setPreferredSize(new Dimension(100,200));
        promotions.setPreferredScrollableViewportSize(promotions.getPreferredSize());
        promotions.setFillsViewportHeight(true);
        sections.setPreferredSize(new Dimension(100,200));
        sections.setPreferredScrollableViewportSize(sections.getPreferredSize());
        sections.setFillsViewportHeight(true);
        groupes.setPreferredSize(new Dimension(100,200));
        groupes.setPreferredScrollableViewportSize(groupes.getPreferredSize());
        groupes.setFillsViewportHeight(true);
        modules.setPreferredSize(new Dimension(300,180));
        modules.setPreferredScrollableViewportSize(groupes.getPreferredSize());
        modules.setFillsViewportHeight(true);
        domaines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        domaines.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (domaines.getSelectedRow() >= 0)
                           formationsModel.updateModel( controler.selectFormations( ((Domaine)domainesModel.getRow(domaines.getSelectedRow())).getIdDomaine()));
                           promotionsModel.removeAllRows();
                           sectionsModel.removeAllRows();
                           groupesModel.removeAllRows();
                           modulesModel.removeAllRows();
                    }
                });
        formations.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {

                        if (formations.getSelectedRow()>= 0)
                            promotionsModel.updateModel(controler.selectPromotions(((Formation)formationsModel.getRow(formations.getSelectedRow())).getIdFormation()));
                            modulesModel.updateModel(controler.selectModules(((Formation)formationsModel.getRow(formations.getSelectedRow())).getIdFormation()));
                            sectionsModel.removeAllRows();
                            groupesModel.removeAllRows();


                    }
                });
        promotions.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                        if (promotions.getSelectedRow() >= 0)
                            sectionsModel.updateModel(controler.selectSections(((Promotion)promotionsModel.getRow(promotions.getSelectedRow())).getIdPromotion()));
                            groupesModel.removeAllRows();
                    }
                });
        sections.getSelectionModel().addListSelectionListener(
                new ListSelectionListener() {
                    public void valueChanged(ListSelectionEvent event) {
                            if (sections.getSelectedRow() >= 0)
                                groupesModel.updateModel(controler.selectGroupes(((Section)sectionsModel.getRow(sections.getSelectedRow())).getIdSection()));
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

        addPanel.getButton().addActionListener((actionEvent -> {

                JSONObject response=null;
                switch ( addPanel.getComboBox().getSelectedItem().toString()){
                    case "Domaine": {

                        if( Pattern.compile("^[\\p{L} ]+$").matcher( addPanel.getIdentifiant().getText()).matches())
                        {response=  controler.addDomaine(addPanel.getIdentifiant().getText());
                       }
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "veuillez respectez le format","erreur", JOptionPane.INFORMATION_MESSAGE);
                        }
                        break;
                    }
                    case "Formation":{
                        if( Pattern.compile("^[\\p{L} ]+$").matcher( addPanel.getIdentifiant().getText()).matches())
                        {
                            response=  controler.addFormation(addPanel.getIdentifiant().getText(),addPanel.getNiveau().getSelectedItem().toString(),((Domaine)domainesModel.getRow(domaines.getSelectedRow())).getIdDomaine());

                        }
                        else if(domaines.getSelectedRow()<0)addPanel.getTitle().setVisible(true);
                        else{
                            JOptionPane.showMessageDialog(new JFrame(), "veuillez respectez le format","erreur", JOptionPane.INFORMATION_MESSAGE);
                            addPanel.getTitle().setVisible(false);
                        }
                    }
                    break;
                    case "Promotion":
                        if(formations.getSelectedRow()<0)addPanel.getTitle().setVisible(true);
                        else {
                            response = controler.addPromotion(((Formation) formationsModel.getRow(formations.getSelectedRow())).getIdFormation());
                            addPanel.getTitle().setVisible(false);
                        }
                    break;
                    case "Section":
                        if(promotions.getSelectedRow()<0)addPanel.getTitle().setVisible(true);
                        else {
                            response = controler.addSection(((Promotion) promotionsModel.getRow(promotions.getSelectedRow())).getIdPromotion());
                            addPanel.getTitle().setVisible(false);
                        }
                        break;
                    case "Groupe":
                        if(sections.getSelectedRow()<0)addPanel.getTitle().setVisible(true);
                        else {
                            response = controler.addGroupe(((Section)sectionsModel.getRow(sections.getSelectedRow())).getIdSection());
                            addPanel.getTitle().setVisible(false);
                        }
                        break;

                    case "Module":
                        if(formations.getSelectedRow()<0)addPanel.getTitle().setVisible(true);
                        else {
                            System.out.println("module");
                            response = controler.addModule(addPanel.getIdentifiant().getText(),addPanel.getModule().getText(),((Formation) formationsModel.getRow(formations.getSelectedRow())).getIdFormation());
                            addPanel.getTitle().setVisible(false);
                            break;
                        }
                    default:response=null;
                }

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



        }));

        JPanel listPanel=new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy= 0;
        c.gridheight=2;
        c.ipadx=3;
        c.ipady=3;
        listPanel.add(new JScrollPane(domaines),c);
        c.gridx = 1;
        listPanel.add(new JScrollPane(formations),c);
        c.gridx = 2;
        c.gridheight=1;
        listPanel.add(new JScrollPane(promotions),c);
        c.gridx = 3;
        listPanel.add(new JScrollPane(sections),c);
        c.gridx = 4;
        listPanel.add(new JScrollPane(groupes),c);
        c.gridx = 2;
        c.gridy =1;
        c.gridwidth=3;
        c.fill=GridBagConstraints.HORIZONTAL;
        listPanel.add(new JScrollPane(modules),c);
        listPanel.setSize(600,400);
        this.add(listPanel);
        this.add(addPanel);
    }



    @Override
    public void update(Object object, Changement changement) {
        System.out.println(changement);
        if(changement.type==Changement.Type.ADD)
         switch (changement.section){
            case DOMAINE:
                domainesModel.updateModel(modele.selectDomaines());
                break;
            case FORMATION:
                 if (domaines.getSelectedRow() >= 0)
                 formationsModel.updateModel( controler.selectFormations( ((Domaine)domainesModel.getRow(domaines.getSelectedRow())).getIdDomaine()));
                 break;
            case PROMOTION:
                if (formations.getSelectedRow() >= 0)
                promotionsModel.updateModel(controler.selectPromotions(((Formation)formationsModel.getRow(formations.getSelectedRow())).getIdFormation()));
                break;
             case SECTION:
                 if (promotions.getSelectedRow() >= 0)
                     sectionsModel.updateModel(controler.selectSections(((Promotion)promotionsModel.getRow(promotions.getSelectedRow())).getIdPromotion()));
                break;
             case GROUPE:
                 if (sections.getSelectedRow() >= 0)
                     groupesModel.updateModel(controler.selectGroupes(((Section)sectionsModel.getRow(sections.getSelectedRow())).getIdSection()));
                 break;
             case MODULE:
                 if (formations.getSelectedRow() >= 0)
                     modulesModel.updateModel(controler.selectModules(((Formation)formationsModel.getRow(formations.getSelectedRow())).getIdFormation()));
                 break;

         }



    }
}
