package fr.univ.tln.projet.planning.ihm.components;
import lombok.Getter;

import javax.swing.*;

import java.awt.*;
@Getter
public class SearchBox extends JPanel {
    private JTextField searchable = new JTextField(30);
    private JButton searchB = new JButton("Search");

    private JPanel panel = new JPanel();



    public SearchBox() throws HeadlessException {
        super();
        setSize(600, 600);
        addComponents();


    }

    private void addComponents() {
        panel.add(searchable);
        panel.add(searchB);

        add(panel);
    }

}
