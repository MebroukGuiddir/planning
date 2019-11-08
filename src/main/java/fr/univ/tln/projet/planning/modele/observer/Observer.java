package fr.univ.tln.projet.planning.modele.observer;

import fr.univ.tln.projet.planning.controler.Changement;

public interface Observer {
    public void update(Object object, Changement changement);
}
