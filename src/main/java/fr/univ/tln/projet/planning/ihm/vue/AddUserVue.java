package fr.univ.tln.projet.planning.ihm.vue;
/**
 * @autor GUIDDIR MEBROUL
 * @since 1.0
 */

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.exception.dao.DaoException;
import fr.univ.tln.projet.planning.ihm.components.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;
import java.util.regex.Pattern;

public class AddUserVue extends JPanel  {
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
    public AddUserVue(  AbstractControler controler)
    {    super( );
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
            else if(status.getSelectedItem().equals("Etudiant"))
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
         if(firstName.getText().length()==0 && firstName.setError("*ce champs est obligatoire").showError() ||
            !check( "^[a-zA-Z][a-zA-Z ]*$",firstName.getText()) && firstName.setError("*veuillez respecter le format").showError() ){error=true;}
         else firstName.hideError();

         if(lastName.getText().length()==0 && lastName.setError("*ce champs est obligatoire").showError() ||
                 !check( "^[a-zA-Z][a-zA-Z ]*$",lastName.getText()) && lastName.setError("*veuillez respecter le format").showError()){error=true;}
         else lastName.hideError();

         if(  email.getText().length()==0 && email.setError("*ce champs est obligatoire").showError()||
                 !check( "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$",email.getText()) && email.setError("*veuillez respecter le format").showError()){error=true;}
         else email.hideError();

         if(address.getText().length()==0 && address.setError("*ce champs est obligatoire").showError() ||
                 !check("^[0-9]{1,3}[a-zA-Z ]+,[0-9]{5}[a-zA-Z ]+$",address.getText()) && address.setError("veuillez respecter le format").showError()){error=true;}
         else address.hideError();

         if(mobile.getText().length()==0 && mobile.setError("*ce champs est obligatoire").showError() ||
                 !check("^0[0-9]{9}$",mobile.getText()) && mobile.setError("veuillez respecter le format").showError()){error=true;}
         else mobile.hideError();

         if(username.getText().length() ==0 && username.setError("*ce champs est obligatoire").showError()||
                 !check( "^[a-zA-Z0-9]{5,}$",username.getText()) && username.setError("veuillez respecter le format").showError() )  {error=true;}
         else username.hideError();
         if(!check("^.{6,}$",password.getPassword()) && password.setError("*au moins six caractéres").showError()){error=true;}
         else password.hideError();
          if(!error) {
              JSONObject response=adminControler.controlerAddUser(firstName.getText(),lastName.getText(),email.getText(), password.getPassword(),username.getText(),dateNaissance.getDate(),genre.getSelected(),address.getText(),mobile.getText(),status.getSelectedItem());
              switch ((int)response.get("code")){
                  case 201:
                      JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.INFORMATION_MESSAGE);
                      firstName.setText("");lastName.setText("");address.setText("");email.setText("");password.setText("");username.setText("");mobile.setText("");break;
                  case 409:
                      JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                  case 500:
                      JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
              }

          }

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
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        subPanel.setMinimumSize(new Dimension(width/3,height/10));
        subPanel.setSize(new Dimension(width/3,height/10));
        subPanel.setPreferredSize(new Dimension(width/3,height/10));
        subPanel.add(component);
        subPanel.setOpaque(false);
        panel.add(subPanel);
    }
    private static boolean check(String regex,String text){
        return Pattern.compile(regex).matcher(text).matches();
    }


}
