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
    public JdatePickerAdapter(String label,int year,int month,int day){
        super();
        UtilDateModel model = new UtilDateModel();
        model.setDate(year,month-1,day);
        model.setSelected(true);
        Properties properties  = new Properties();
        properties.put("text.day", "Day");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,properties);
        jDatePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
        jDatePicker.setSize(200, 30);
        jDatePicker.setPreferredSize(new Dimension(200,30));
        jDatePicker.setMinimumSize(new Dimension(200,30));
        this.add(jDatePicker);
    }
    public JdatePickerAdapter(String label){
        super();
        this.label = new JLabel(label);
        this.label.setFont(new Font("Arial", Font.BOLD, 12));
        this.label.setSize(100, 20);
        this.label.setPreferredSize(new Dimension(100, 20));
        UtilDateModel model = new UtilDateModel();
        model.setDate(1997,11,6);
        model.setSelected(true);
        Properties properties  = new Properties();
        properties.put("text.day", "Day");
        properties.put("text.month", "Month");
        properties.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model,properties);
        jDatePicker = new JDatePickerImpl(datePanel,new DateLabelFormatter());
        this.add(this.label);
        this.add(jDatePicker);
    }
    public Date getDate(){
        Date selectedValue =(Date)  this.jDatePicker.getModel().getValue();
        System.out.println(selectedValue);
        return  selectedValue;
    }
    public void setDate(int day){
        this.jDatePicker.getModel().addDay(day);
    }
    public JDatePickerImpl getJDatePicker(){
        return this.jDatePicker;
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
