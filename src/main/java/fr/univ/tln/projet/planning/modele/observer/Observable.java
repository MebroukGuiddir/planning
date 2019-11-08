package fr.univ.tln.projet.planning.modele.observer;

import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.modele.observer.Observer;

public interface Observable {
    public void addObserver(Observer obs);
    public void removeObserver();
    public void notifyObserver(Object object, Changement changement);
}
