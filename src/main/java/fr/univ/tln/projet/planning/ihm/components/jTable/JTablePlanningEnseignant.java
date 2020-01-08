package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.etudes.Seance;

public class JTablePlanningEnseignant extends ModeleDynamiqueObject {
    public JTablePlanningEnseignant(String[] entetes) {
        super(entetes);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0: return ((Seance) rows.get(rowIndex)).getDate();
            case 1: return ((Seance) rows.get(rowIndex)).getHeureDebut()+"   "+((Seance) rows.get(rowIndex)).getHeureFin();
            case 2: return ((Seance) rows.get(rowIndex)).getSalle().getBatiment().getIdentifiant()+" "+((Seance) rows.get(rowIndex)).getSalle().getIdentifiant();
            case 3: return ((Seance) rows.get(rowIndex)).getCours().getModule().getIdentifiant()+" "+((Seance) rows.get(rowIndex)).getCours().getModule().getLibelle();
            case 4: switch (((Seance) rows.get(rowIndex)).getStatus()) {
                case -1: return "En Attente de validation";
                case 0: return "An Attente d'annulation";
                case 1 :return "Valider";
                case -2 :return  "Annuler";
            }

            default: return null;
        }
    }
}
