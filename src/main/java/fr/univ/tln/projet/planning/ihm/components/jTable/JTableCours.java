package fr.univ.tln.projet.planning.ihm.components.jTable;


import fr.univ.tln.projet.planning.modele.etudes.Cours;

public class  JTableCours  extends ModeleDynamiqueObject  {


    public  JTableCours(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:return ((Cours) rows.get(rowIndex)).getModule().getLibelle();
            case 1:return ((Cours) rows.get(rowIndex)).getModule().getIdentifiant();
            default:return  "";
        }

    }
}