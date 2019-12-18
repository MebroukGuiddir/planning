package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.etudes.Domaine;
import fr.univ.tln.projet.planning.modele.etudes.Promotion;

public class JTablePromotion extends ModeleDynamiqueObject  {
    public JTablePromotion(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String year=((Promotion) rows.get(rowIndex)).getAnnee();
        return year+"-"+ (Integer.valueOf(year)+1);
    }
}
