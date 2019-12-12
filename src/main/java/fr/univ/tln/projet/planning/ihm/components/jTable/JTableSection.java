package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.etudes.Domaine;
import fr.univ.tln.projet.planning.modele.etudes.Section;

public class JTableSection extends ModeleDynamiqueObject  {


    public JTableSection(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return ((Section) rows.get(rowIndex)).getIdentifiant();
    }
}
