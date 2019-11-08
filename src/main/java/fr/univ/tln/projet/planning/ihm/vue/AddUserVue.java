package fr.univ.tln.projet.planning.ihm.vue;


import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JButtonAdapter;
import fr.univ.tln.projet.planning.ihm.components.JLabelAdapter;
import fr.univ.tln.projet.planning.ihm.components.JPasswordFieldAdapter;
import fr.univ.tln.projet.planning.ihm.components.JTextFieldAdapter;
import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class AddUserVue extends JPanelAdapter {
    // Components of the Form

    private AbstractControler adminControler;
    private JLabel firstNameLabel;
    private JTextField firstName;
    private JLabel lastNameLabel;
    private JTextField lastName;
    private JLabel usernameLabel;
    private JTextField username;
    private JLabel passwordLabel;
    private JPasswordField password;
    private JLabel emailLabel;
    private  JLabel statusLabel;
    private  JComboBox status;
    private JTextField email;
    private JLabel addressLabel;
    private JTextField address;
    private JLabel mobileLabel;
    private JTextField mobile;
    private JLabel gender;
    private JRadioButton male;
    private JRadioButton female;
    private ButtonGroup gengp;
    private JLabel dobLabel;
    private JComboBox date;
    private JComboBox month;
    private JComboBox year;


    private JButton submit;
    private JButton reset;

    private String dates[]
            = { "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15",
            "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25",
            "26", "27", "28", "29", "30",
            "31" };
    private String months[]
            = { "Jan", "feb", "Mar", "Apr",
            "May", "Jun", "July", "Aug",
            "Sup", "Oct", "Nov", "Dec" };
    private String years[]
            = { "1995", "1996", "1997", "1998",
            "1999", "2000", "2001", "2002",
            "2003", "2004", "2005", "2006",
            "2007", "2008", "2009", "2010",
            "2011", "2012", "2013", "2014",
            "2015", "2016", "2017", "2018",
            "2019" };

    // constructor, to initialize the components
    // with default values.
    public AddUserVue(TypePanel typePanel, AbstractControler controler)
    {    super(typePanel);
        adminControler=controler;
        //principal layout
        BorderLayout borderLayout=new BorderLayout();

        //Right layout
        GridLayout gridLayoutRight=new GridLayout(5, 2);
        gridLayoutRight.setHgap(2);
        gridLayoutRight.setVgap(2);
        //panel for Right layout
        JPanel panelRight=new JPanel(gridLayoutRight);
        panelRight.setBorder(BorderFactory.createRaisedBevelBorder());
        //Left layout
        GridLayout gridLayoutLeft=new GridLayout(6, 2);
        gridLayoutLeft.setHgap(2);
        gridLayoutLeft.setVgap(2);
        //panel for Left layout
        JPanel panelLeft=new JPanel(gridLayoutLeft);
        panelLeft.setBorder(BorderFactory.createRaisedBevelBorder());
        //Bottom layout
        FlowLayout flowLayoutBottom=new FlowLayout();
        //panel for Bottom layout
        JPanel panelBottom=new JPanel(flowLayoutBottom);
        this.setLayout(borderLayout);
        this.add(panelLeft,BorderLayout.WEST);
        this.add(panelRight,BorderLayout.CENTER);
        this.add(panelBottom,BorderLayout.SOUTH);

        firstNameLabel = new JLabelAdapter("Nom");
        addComponentWithPanel(firstNameLabel,panelLeft);

        firstName = new JTextFieldAdapter();
        addComponentWithPanel(firstName,panelLeft);

        firstNameLabel = new JLabelAdapter("Prénom");
        addComponentWithPanel(firstNameLabel,panelLeft);

        lastName = new JTextFieldAdapter();
        addComponentWithPanel(lastName,panelLeft);

        addressLabel = new JLabelAdapter("Adresse");
        addComponentWithPanel(addressLabel,panelLeft);

        address = new JTextFieldAdapter();
        addComponentWithPanel(address,panelLeft);

        mobileLabel = new JLabelAdapter("Mobile");
        addComponentWithPanel(mobileLabel,panelLeft);

        mobile = new JTextFieldAdapter();
        addComponentWithPanel(mobile,panelLeft);

        gender = new JLabelAdapter("Genre");
        addComponentWithPanel(gender,panelLeft);

        male = new JRadioButton("Male");
        male.setFont(new Font("Arial", Font.BOLD, 12));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setPreferredSize(new Dimension(75,20));
        male.setOpaque(false);


        female = new JRadioButton("Female");
        female.setFont(new Font("Arial", Font.BOLD, 12));
        female.setSelected(false);
        female.setSize(80, 20);
        female.setPreferredSize(new Dimension( 80, 20));
        female.setOpaque(false);


        gengp = new ButtonGroup();
        JPanel panelButton=new JPanel();
        panelButton.add(male);
        panelButton.add(female);
        panelButton.setOpaque(false);
        gengp.add(male);
        gengp.add(female);
        addComponentWithPanel(panelButton,panelLeft);

        dobLabel=new JLabelAdapter("Birthday");
        addComponentWithPanel(dobLabel,panelLeft);

        date = new JComboBox(dates);
        date.setFont(new Font("Arial", Font.BOLD, 15));
        date.setOpaque(false);


        month = new JComboBox(months);
        month.setFont(new Font("Arial", Font.BOLD, 15));
        month.setOpaque(false);


        year = new JComboBox(years);
        year.setFont(new Font("Arial", Font.BOLD, 15));
        year.setOpaque(false);

        JPanel panelDate=new JPanel();
        panelDate.add(date);
        panelDate.add(month);
        panelDate.add(year);
        panelDate.setOpaque(false);
        addComponentWithPanel(panelDate,panelLeft);

        ///right panel

        usernameLabel= new JLabelAdapter("Username");
        addComponentWithPanel(usernameLabel,panelRight);
        username=new JTextFieldAdapter();
        addComponentWithPanel(username,panelRight);

        emailLabel= new JLabelAdapter("Email");
        addComponentWithPanel(emailLabel,panelRight);
        email=new JTextFieldAdapter();
        addComponentWithPanel(email,panelRight);

        passwordLabel= new JLabelAdapter("Password");
        addComponentWithPanel(passwordLabel,panelRight);
        password=new JPasswordFieldAdapter();
        addComponentWithPanel(password,panelRight);

        statusLabel=new JLabelAdapter("Status");
        addComponentWithPanel(statusLabel,panelRight);

        status = new JComboBox(new String[]{"Etudiant", "Enseignant","Responsable","Admin"});
        status.setFont(new Font("Arial", Font.BOLD, 13));
        status.setOpaque(false);
        addComponentWithPanel(status,panelRight);

        submit = new JButtonAdapter("Submit");

        submit.addActionListener(e -> {

            if(     check( "^[a-zA-Z][a-zA-Z ]*$",firstName.getText())&&
                    check( "^[a-zA-Z][a-zA-Z ]*$",lastName.getText())&&
                   check( "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",email.getText())&&
                  //  check("[0-9]{1,3}(?:(?:[,. ]){1}[-a-zA-Zàâäéèêëïîôöùûüç]+)+",address.getText())&&
                   check("^0[0-9]{9}$",mobile.getText())&&
                    check( "^[a-zA-Z0-9]{5,}$",username.getText())&&
                    check("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",password.getPassword().toString())

            ) adminControler.controlerAddUser(firstName.getText(),lastName.getText(),email.getText(), password.getPassword().toString(),username.getText(),date.toString(),gender.getText(),address.getText(),mobile.getText(),status.getSelectedItem().toString());

            else {

                JDialog d = new JDialog( (JFrame) SwingUtilities.getWindowAncestor(this), "Erreur de saisie");
                d.add(new JLabel("Veuillez remplir correctement tous les champs!"));
                d.setSize(500, 200);
                d.setLocation(0,500);
                d.setVisible(true);
            }
        });
        addComponentWithPanel(submit,panelRight);

        reset = new JButtonAdapter("Reset");
        reset.addActionListener(actionEvent -> {
                   firstName.setText("");
                   lastName.setText("");
                   address.setText("");
                   email.setText("");
                   password.setText("");
                   username.setText("");
                   mobile.setText("");
        });
        addComponentWithPanel(reset,panelRight);



    }

    private void addComponentWithPanel(Component component,JPanel panel){
        JPanel subPanel= new JPanel();
        subPanel.add(component);
        subPanel.setOpaque(false);
        panel.add(subPanel);
    }
    private static boolean check(String regex,String text){
        return Pattern.compile(regex).matcher(text).matches();
    }


}
