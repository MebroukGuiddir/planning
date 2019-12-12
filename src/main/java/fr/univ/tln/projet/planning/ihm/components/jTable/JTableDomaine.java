package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.etudes.Domaine;

public class JTableDomaine extends ModeleDynamiqueObject  {
    public JTableDomaine(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Domaine) rows.get(rowIndex)).getIntitule();
    }
}
