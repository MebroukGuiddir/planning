package fr.univ.tln.projet.planning.ihm.components.jTable;
import fr.univ.tln.projet.planning.modele.etudes.Module;
public class JTableModule  extends ModeleDynamiqueObject  {


    public JTableModule(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:return ((Module) rows.get(rowIndex)).getIdentifiant();
            case 1:return ((Module) rows.get(rowIndex)).getLibelle();
            default:return  "";
        }

    }
}