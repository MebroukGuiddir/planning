package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.ihm.components.SearchBox;
import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import fr.univ.tln.projet.planning.modele.observer.Observer;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUserVue extends JPanel implements Observer {
    private ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet(new String []{"Prénom", "Nom", "Email","Login","Adresse","Date Naissance"});
    private JTable list;
    private AbstractControler controler;

    ListUserVue(AbstractControler controler ){
        super();
        this.controler =controler;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        JComboBox status = new JComboBox(new String[]{"Etudiant", "Enseignant","Responsable","Admin"});
        modele.updateModel(controler.selectUsers("",status.getSelectedItem().toString()));
        JPanel jHeader =new JPanel();
        SearchBox recherche =new SearchBox("Recherche");
        jHeader.add(status);
        jHeader.add(recherche);
        recherche.getSearchB().addActionListener(actionEvent -> {
            modele.updateModel(  this.controler.selectUsers(recherche.getSearchable().getText(),status.getSelectedItem().toString()));
        });
        JPanel boutons = new JPanel();
        JButton supprimer=new JButton("Supprimer");
        supprimer.addActionListener((actionEvent) -> {
            int[] selection = list.getSelectedRows();
            JSONObject response=this.controler.deleteUser(modele.getValueAt(selection[0],3).toString());
            switch ((int)response.get("code")){
                case 200:
                    JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                    for(int i = selection.length - 1; i >= 0; i--){
                        modele.removeRow(selection[i]);
                    }break;
                case 500:
                    JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
            }

        });
        JButton creer=new JButton("créer");
        creer.addActionListener((actionEvent) -> {
            JPanel addUserPanel=new AddUserVue(controler);
            JFrame addUserFrame=new JFrame();
            addUserFrame.setSize(new Dimension(1000,500));
            addUserFrame.setContentPane(addUserPanel);
            addUserFrame.setVisible(true);
        });
        boutons.add(supprimer);
        boutons.add(creer);

        list = new JTable(modele);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        this.add(boutons);
         this.add(jHeader);
        this.add(scrollPane);


    }



    @Override
    public void update(Object object, Changement changement) {
         if(changement.type==Changement.Type.ADD){}
    }

    public class ModeleDynamiqueObjet <T> extends AbstractTableModel {
        private final List<T> rows = new ArrayList<>();
        private final String[] entetes;
        public ModeleDynamiqueObjet(String [] entetes) {
            super();
            this.entetes=entetes;
        }
        public int getRowCount() {
            return rows.size();
        }
        public int getColumnCount() {
            return entetes.length;
        }
        public String getColumnName(int columnIndex) {
            return entetes[columnIndex];
        }
        public Object getValueAt(int rowIndex, int columnIndex ) {
            switch(columnIndex){

                case 0:
                    return ((Utilisateur)rows.get(rowIndex)).getPrenom();
                case 1:
                    return ((Utilisateur)rows.get(rowIndex)).getNom();
                case 2:
                    return ((Utilisateur)rows.get(rowIndex)).getEmail();
                case 3:
                    return  ((Utilisateur)rows.get(rowIndex)).getUsername();
                case 4:
                    return ((Utilisateur)rows.get(rowIndex)).getAdresse();
                case 5:
                    return ((Utilisateur)rows.get(rowIndex)).getDateNaissance();
                default:
                    return null; //Ne devrait jamais arriver
            }
        }

        public void updateRows(T row) {
            rows.add(row);

            fireTableRowsInserted(rows.size() -1, rows.size() -1);
        }

        public void removeRow(int rowIndex) {
            rows.remove(rowIndex);

            fireTableRowsDeleted(rowIndex, rowIndex);
        }
        public void updateModel(Collection<T> rows){
            this.rows.clear();
            this.rows.addAll(rows);
            System.out.println(this.rows);
            fireTableDataChanged();
        }
    }



}
