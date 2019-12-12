package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.etudes.Domaine;
import fr.univ.tln.projet.planning.modele.etudes.Formation;

public class JTableFormation extends ModeleDynamiqueObject  {


    public JTableFormation(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: return ((Formation) rows.get(rowIndex)).getIntitule();
            case 1: return ((Formation) rows.get(rowIndex)).getNiveau();
            default: return null;
        }

    }
}