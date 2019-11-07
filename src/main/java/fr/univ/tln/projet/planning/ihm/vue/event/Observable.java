package fr.univ.tln.projet.planning.ihm.vue.event;

import java.awt.*;

public interface Observable {
    public void addObserver(Observer obs);
    public void removeObserver();
    public void notifyObserver(Dimension dimension);
}
