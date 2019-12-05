package fr.univ.tln.projet.planning.ihm.components;
import lombok.Getter;

import javax.swing.*;

import java.awt.*;
@Getter
public class SearchBox extends JPanel {
    private JTextField searchable = new JTextField(30);
    private JButton searchB ;

    private JPanel panel = new JPanel();



    public SearchBox(String button) throws HeadlessException {
        super();
        searchB = new JButton(button);
        setSize(600, 600);
        addComponents();


    }

    private void addComponents() {
        panel.add(searchable);
        panel.add(searchB);

        add(panel);
    }

}
