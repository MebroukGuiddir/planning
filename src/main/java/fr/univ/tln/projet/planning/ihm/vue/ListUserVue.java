package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.ihm.components.SearchBox;
import fr.univ.tln.projet.planning.modele.Utilisateur;
import fr.univ.tln.projet.planning.modele.observer.Observer;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListUserVue extends JPanel implements Observer {
    private ModeleDynamiqueObjet modele = new ModeleDynamiqueObjet();
    private JTable list;
    private AbstractControler controler;

    ListUserVue(AbstractControler controler ){
        super();
        this.controler =controler;
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        modele.updateModel(controler.selectEtudiants(""));
        JPanel jHeader =new JPanel();
        JComboBox status = new JComboBox(new String[]{"Etudiant", "Enseignant","Responsable","Admin"});
        SearchBox recherche =new SearchBox();
        jHeader.add(status);
        jHeader.add(recherche);
        recherche.getSearchB().addActionListener(actionEvent -> {
            modele.updateModel(  this.controler.selectEtudiants(recherche.getSearchable().getText()));
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
                        modele.removeUtilisateur(selection[i]);
                    }break;
                case 500:
                    JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
            }

        });
        JButton rafraichir=new JButton("rafraîchir");
        rafraichir.addActionListener((actionEvent) -> {
           // this.controler.rafrichirListeUtilisateur();
        });
        boutons.add(supprimer);
        boutons.add(rafraichir);

        list = new JTable(modele);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(list);
        this.add(jHeader);
        this.add(scrollPane);
        this.add(boutons);

    }



    @Override
    public void update(Object object, Changement changement) {
         if(changement.type==Changement.Type.ADD){}
    }

    public class ModeleDynamiqueObjet extends AbstractTableModel {
        private final List<Utilisateur> utilisateurs = new ArrayList<Utilisateur>();
        private final String[] entetes = {"Prénom", "Nom", "Email","Login","Adresse","Date Naissance"};

        public ModeleDynamiqueObjet() {
            super();
        }

        public int getRowCount() {
            return utilisateurs.size();
        }

        public int getColumnCount() {
            return entetes.length;
        }

        public String getColumnName(int columnIndex) {
            return entetes[columnIndex];
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            switch(columnIndex){
                case 0:
                    return utilisateurs.get(rowIndex).getPrenom();
                case 1:
                    return utilisateurs.get(rowIndex).getNom();
                case 2:
                    return utilisateurs.get(rowIndex).getEmail();
                case 3:
                    return  utilisateurs.get(rowIndex).getUsername();
                case 4:
                    return utilisateurs.get(rowIndex).getAdresse();
                case 5:
                    return utilisateurs.get(rowIndex).getDateNaissance();
                default:
                    return null; //Ne devrait jamais arriver
            }
        }

        public void updateUtilisateur(Utilisateur utilisateur) {
            utilisateurs.add(utilisateur);

            fireTableRowsInserted(utilisateurs.size() -1, utilisateurs.size() -1);
        }

        public void removeUtilisateur(int rowIndex) {
            utilisateurs.remove(rowIndex);

            fireTableRowsDeleted(rowIndex, rowIndex);
        }
        public void updateModel(Collection<Utilisateur> utilisateurs){
            this.utilisateurs.clear();
            this.utilisateurs.addAll(utilisateurs);
            System.out.println(this.utilisateurs);
            fireTableDataChanged();
        }
    }
}
