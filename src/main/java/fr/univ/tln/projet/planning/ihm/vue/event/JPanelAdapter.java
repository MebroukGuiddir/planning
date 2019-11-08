package fr.univ.tln.projet.planning.ihm.vue.event;

import javax.swing.*;
import java.awt.*;

public  class JPanelAdapter extends JPanel implements ObserverJFrame {

    TypePanel typePanel;

    public JPanelAdapter(TypePanel typePanel){
      super();
      this.typePanel=typePanel;
    }
    @Override
    public void update(Dimension dimension) {
       int width=100;
       int height=100;
       switch (typePanel){
           case BODY:width=100;height=80;break;
           case HEADER:width=100;height=10;break;
           case FOOTER:width=100;height=10;break;
           case MENU:width=30;height=80;break;
           case SECTION:width=70;height=80;break;
       }
        this.setMinimumSize(new Dimension(dimension.width*width/100,dimension.height*height/100));
        this.setPreferredSize(new Dimension(dimension.width*width/100,dimension.height*height/100));
    }

    public enum TypePanel{
        HEADER,FOOTER,BODY,SECTION,MENU
    }
}
