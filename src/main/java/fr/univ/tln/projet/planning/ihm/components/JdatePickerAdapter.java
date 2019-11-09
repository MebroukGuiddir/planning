package fr.univ.tln.projet.planning.ihm.components;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

public class JdatePickerAdapter extends JPanel {
    private JLabel label;
    private JDatePickerImpl jDatePicker;

    public JdatePickerAdapter(String label){
        super();
        this.label = new JLabel(label);
        this.label.setFont(new Font("Arial", Font.BOLD, 12));
        this.label.setSize(100, 20);
        this.label.setPreferredSize(new Dimension(100, 20));
        UtilDateModel model = new UtilDateModel();
        model.setDate(20,04,2000);
        Properties properties  = new Properties();
        properties.put("text.today", "Today");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,properties);
        jDatePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
        this.add(jDatePicker);
    }
    public String getDate(){
      return   this.jDatePicker.getModel().getValue().toString();
    }
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
