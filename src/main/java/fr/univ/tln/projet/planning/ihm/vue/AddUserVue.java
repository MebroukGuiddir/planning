package fr.univ.tln.projet.planning.ihm.vue;


import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.*;
import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;
import org.apache.commons.lang3.RandomStringUtils;

import javax.swing.*;
import java.awt.*;
import java.util.regex.Pattern;

public class AddUserVue extends JPanelAdapter {
    // Components of the Form

    private AbstractControler adminControler;
    private JTextFieldAdapter firstName;
    private JTextFieldAdapter lastName;
    private JTextFieldAdapter username;
    private JPasswordFieldAdapter password;
    private  JComboBoxAdapter status;
    private JTextFieldAdapter email;
    private JTextFieldAdapter address;
    private JTextFieldAdapter mobile;
    private JRadioButtonAdapter genre;
    private JdatePickerAdapter dateNaissance ;

    private JButtonAdapter generate ;
    private JButton submit;
    private JButton reset;



    // constructor, to initialize the components
    // with default values.
    public AddUserVue(TypePanel typePanel, AbstractControler controler)
    {    super(typePanel);
        adminControler=controler;
        //principal layout
        BorderLayout borderLayout=new BorderLayout();

        //Right layout
        GridLayout gridLayout=new GridLayout(6, 2);
        gridLayout.setHgap(2);
        gridLayout.setVgap(2);
        //panel for Right layout
        JPanel panelForm=new JPanel(gridLayout);
        panelForm.setBorder(BorderFactory.createRaisedBevelBorder());



        //Bottom layout
        FlowLayout flowLayoutBottom=new FlowLayout();
        //panel for Bottom layout
        JPanel panelBottom=new JPanel(flowLayoutBottom);
        panelBottom.setMinimumSize(new Dimension(100,100));
        this.setLayout(borderLayout);

        this.add(panelForm,BorderLayout.CENTER);
        this.add(panelBottom,BorderLayout.SOUTH);



        firstName = new JTextFieldAdapter("FirstName","error");
        addComponentWithPanel(firstName,panelForm);

        username=new JTextFieldAdapter("Username","veuillez respecter le format");
        addComponentWithPanel(username,panelForm);

        lastName = new JTextFieldAdapter("LastName","error");
        addComponentWithPanel(lastName,panelForm);

        email=new JTextFieldAdapter("Email","error");
        addComponentWithPanel(email,panelForm);

        address = new JTextFieldAdapter("Address","error");
        addComponentWithPanel(address,panelForm);

        password=new JPasswordFieldAdapter("Password","error");
        addComponentWithPanel(password,panelForm);

        mobile = new JTextFieldAdapter("Mobile","error");
        addComponentWithPanel(mobile,panelForm);

        status = new JComboBoxAdapter("Status",new String[]{"Etudiant", "Enseignant","Responsable","Admin"});
        addComponentWithPanel(status,panelForm);


        genre = new JRadioButtonAdapter("Genre", new String[]{"Male", "Female"});
        addComponentWithPanel(genre,panelForm);

        generate = new JButtonAdapter("Auto-Generate");
        generate.addActionListener(actionEvent -> {
             String statusText;
            if(status.getSelectedItem().equals("Responsable")|| status.getSelectedItem().equals("Enseignant") )
                statusText="ens";
            else if(status.getSelectedItem().equals("Etudaiant"))
                statusText="etud";
            else statusText="admin";
          if(lastName.getText().length()!=0 && firstName.getText().length()!=0)  email.setText(lastName.getText()+firstName.getText()+"@"+statusText+".univ-tln.fr");

            password.setText(RandomStringUtils.randomAlphabetic(8));
           if(lastName.getText().length()!=0 && firstName.getText().length()!=0) username.setText(lastName.getText().substring(0,1)+firstName.getText());

        });
        addComponentWithPanel(generate,panelForm);


        dateNaissance =new JdatePickerAdapter("Birthday");
        addComponentWithPanel(dateNaissance,panelForm);








        submit = new JButtonAdapter("Submit");

        submit.addActionListener(e -> {
            boolean error=false;
         if(!check( "^[a-zA-Z][a-zA-Z ]*$",firstName.getText()) ){firstName.showError();error=true;}
         else firstName.hideError();
         if(!check( "^[a-zA-Z][a-zA-Z ]*$",lastName.getText())){lastName.showError();error=true;}
         else lastName.hideError();
         if(!check( "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",email.getText())){email.showError();error=true;}
         else email.hideError();
         if(!check("[0-9]{1,3}(?:(?:[,. ]){1}[-a-zA-Zàâäéèêëïîôöùûüç]+)+",address.getText())){address.showError();error=true;}
         else address.hideError();
         if(!check("^0[0-9]{9}$",mobile.getText())){mobile.showError();error=true;}
         else mobile.hideError();
         if(!check( "^[a-zA-Z0-9]{5,}$",username.getText()))   username.showError();
         else username.hideError();
         if(check("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",password.getPassword())){ password.showError();error=true;}
         else password.hideError();
          if(!error) adminControler.controlerAddUser(firstName.getText(),lastName.getText(),email.getText(), password.getPassword(),username.getText(),dateNaissance.getDate(),genre.getSelected(),address.getText(),mobile.getText(),status.getSelectedItem());

        });
        addComponentWithPanel(submit,panelBottom);

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
        addComponentWithPanel(reset,panelBottom);



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
