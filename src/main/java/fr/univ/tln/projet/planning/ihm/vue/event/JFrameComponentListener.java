package fr.univ.tln.projet.planning.ihm.vue.event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class JFrameComponentListener extends JFrame implements Observable  {
    private ArrayList<Observer> listObserver = new ArrayList<Observer>();
    public JFrameComponentListener(String title) {
        super(title);
        final JFrame jFrame=this;
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                // notify components

                notifyObserver(jFrame.getSize());
                System.out.println(jFrame.getSize());
            }
        });
        this.addWindowStateListener(new WindowStateListener() {
            public void windowStateChanged(WindowEvent event) {

                    int oldState = event.getOldState();
                    int newState = event.getNewState();


                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0) {
                        notifyObserver(screenSize);
                        System.out.println("Frame was maximized"+screenSize);

                    } else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0) {
                        jFrame.getSize();
                       // notifyObserver(jFrame.getSize());
                        System.out.println("Frame was minimized");
                        System.out.println(jFrame.getSize());
                    }



            }
        });

    }


    //Implémentation du pattern observer
    public void addObserver(Observer obs) {
        this.listObserver.add(obs);
    }

    public void notifyObserver(Dimension dimension) {


        for(Observer obs : listObserver)
            obs.update(dimension);
    }

    public void removeObserver() {
        listObserver = new ArrayList<Observer>();
    }
}
