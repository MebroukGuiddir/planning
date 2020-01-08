package fr.univ.tln.projet.planning.ihm.components;

import javax.swing.*;
import java.awt.*;


public class JSpinnerHour extends JPanel {
    SpinnerDateModel model;
    JSpinner spinner;


    public JSpinnerHour() {

        String [] list={"08:00","08:15","08:30","08:45","09:00","09:15","09:30","09:45","10:00","10:15","10:30","10:45",
                        "11:00","11:15","11:30","11:45","12:00","12:15","12:30","12:45","13:00","13:15","13:30","13:45",
                        "14:00","14:15","14:30","14:45","15:00","15:15","15:30","15:45","16:00","16:15","16:30","16:45",
                        "17:00","17:15","17:30","17:45","18:00","18:15","18:30","18:45","19:00"
        };
        SpinnerListModel model = new SpinnerListModel(list);

        spinner = new JSpinner(model);
        spinner.setSize(200, 30);
        spinner.setPreferredSize(new Dimension(200,30));
        spinner.setMinimumSize(new Dimension(200,30));


        this.add(spinner);
    }
    public String getValue(){
        return spinner.getValue().toString();
    }
}
