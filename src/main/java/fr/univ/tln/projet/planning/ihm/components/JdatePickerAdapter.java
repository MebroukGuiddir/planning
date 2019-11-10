package fr.univ.tln.projet.planning.ihm.components;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        model.setDate(1997,04,20);
        Properties properties  = new Properties();
        properties.put("text.day", "Day");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,properties);
        jDatePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
        this.add(jDatePicker);
    }
    public Date getDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        String dateInString =this.jDatePicker.getModel().getValue().toString();
        //Date date = format.parse(dateInString);
        Date date=new Date();
        System.out.println(date);
        return  date;

    }
    public class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {

        private String datePattern = "dd-MM-yyyy";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value)   {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }

    }
}
