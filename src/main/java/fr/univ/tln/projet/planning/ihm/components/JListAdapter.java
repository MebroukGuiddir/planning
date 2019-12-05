package fr.univ.tln.projet.planning.ihm.components;

import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;

public class JListAdapter<T> extends JPanel {
    JLabelAdapter label;
    JList jList;
    DefaultListModel<T> model = new DefaultListModel();
    public JListAdapter(String label){
        this.label=new JLabelAdapter(label);
        this.jList=new JList();

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.jList.setPreferredSize(new Dimension(300,1000));
        this.jList.setSize(new Dimension(300,1000));
        this.jList.setMinimumSize(new Dimension(300,1000));
        this.jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

            }
        });
        JScrollPane sp1 = new JScrollPane(this.jList);

        this.add(this.label);
        this.add(sp1);
    }


    public void setModel(ArrayList<T> list ){

        for(Object item : list){
            model.addElement((T) item);
        }

        this.jList.setModel(model);

    }
}
