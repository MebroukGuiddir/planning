package fr.univ.tln.projet.planning.ihm.vue.event;

import java.awt.*;

public interface ObservableJfram {
    public void addObserver(ObserverJFrame obs);
    public void removeObserver();
    public void notifyObserver(Dimension dimension);
}
