package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.etudes.Groupe;

public class JTableGroupe extends ModeleDynamiqueObject  {

    public JTableGroupe(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return "G"+((Groupe) rows.get(rowIndex)).getIdentifiant();
    }
}
