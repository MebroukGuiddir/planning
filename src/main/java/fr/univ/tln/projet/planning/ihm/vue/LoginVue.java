package fr.univ.tln.projet.planning.ihm.vue;
/**
 * @autor GUIDDIR MEBROUL
 * @since 1.0
 */
import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.Tabbed;
import fr.univ.tln.projet.planning.ihm.components.TabbedPaneAdapter;
import fr.univ.tln.projet.planning.ihm.panels.JFooter;
import fr.univ.tln.projet.planning.ihm.panels.JHeaderPanel;
import fr.univ.tln.projet.planning.ihm.panels.JMenu;
import fr.univ.tln.projet.planning.ihm.vue.etudesVue.DomaineVue;
import net.miginfocom.swing.MigLayout;
import org.json.simple.JSONObject;
import java.awt.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class LoginVue extends JFrame{
    JLabel l1, l2, l3,l4;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;
    JComboBox status;
    AbstractControler controler;

    public LoginVue(AbstractControler controler) {
        super();
        this.controler=controler;
        this.setTitle("Connexion");
        this.setResizable(false);
        this.setSize(600,400);
        this.setMinimumSize(new Dimension(600,400));
        this.setPreferredSize(new Dimension(600,400));
        this.getContentPane().setBackground(new Color(10, 24, 65));

        l1 = new JLabel("AUTHENTIFICATION");
        l1.setForeground(new Color(199, 201, 202));
        l1.setFont(new Font("Serif", Font.BOLD, 20));
        l1.setSize(100,40);
        l1.setMinimumSize(new Dimension(100,40));
        l1.setPreferredSize(new Dimension(100,40));
        l1.setHorizontalAlignment(JLabel.CENTER);
        l1.setVerticalAlignment(JLabel.CENTER);

        l2 = new JLabel("Username");
        l2.setForeground(new Color(199, 201, 202));
        l2.setSize(100,40);
        l2.setMinimumSize(new Dimension(100,40));
        l2.setPreferredSize(new Dimension(100,40));
        l2.setHorizontalAlignment(JLabel.CENTER);
        l2.setVerticalAlignment(JLabel.CENTER);

        l3 = new JLabel("Password");
        l3.setSize(100,40);
        l3.setForeground(new Color(199, 201, 202));
        l3.setMinimumSize(new Dimension(100,40));
        l3.setPreferredSize(new Dimension(100,40));
        l3.setHorizontalAlignment(JLabel.CENTER);
        l3.setVerticalAlignment(JLabel.CENTER);

        l4 = new JLabel("Status");
        l4.setSize(100,40);
        l4.setForeground(new Color(199, 201, 202));
        l4.setMinimumSize(new Dimension(100,40));
        l4.setPreferredSize(new Dimension(100,40));
        l4.setHorizontalAlignment(JLabel.CENTER);
        l4.setVerticalAlignment(JLabel.CENTER);

        tf1 = new JTextField();
        tf1.setSize(150,30);
        tf1.setMinimumSize(new Dimension(150,30));
        tf1.setPreferredSize(new Dimension(150,30));
        tf1.setHorizontalAlignment(JTextField.CENTER);

        p1 = new JPasswordField();
        p1.setSize(150,30);
        p1.setMinimumSize(new Dimension(150,30));
        p1.setPreferredSize(new Dimension(150,30));
        p1.setHorizontalAlignment(JTextField.CENTER);

        status = new JComboBox(new String[]{"Etudiant", "Enseignant","Responsable","Admin"});
        status.setSize(150,30);
        status.setMinimumSize(new Dimension(150,30));
        status.setPreferredSize(new Dimension(150,30));


        btn1 = new JButton("Login");
        btn1.setSize(100,40);
        btn1.setMinimumSize(new Dimension(100,40));
        btn1.setPreferredSize(new Dimension(100,40));
        btn1.setBackground(new Color(65, 14, 15));
        btn1.setForeground(new Color(199, 201, 202));

        btn1.addActionListener(actionEvent -> {
            if(true
                    //check( "^[a-zA-Z0-9]{5,}$",tf1.getText())&&
                          //  check("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",String.valueOf(p1.getPassword()))
            )
            {
                JSONObject response=controler.controlerLogin(tf1.getText(),String.valueOf(p1.getPassword()),status.getSelectedItem().toString());
                switch ((int)response.get("code")){
                    case 401:
                        JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                    case 500:
                        JOptionPane.showMessageDialog(new JFrame(),response.get("message"), (String) response.get("status"), JOptionPane.ERROR_MESSAGE);break;
                    case 200:
                        login((String) response.get("nom"),(String)response.get("prenom"),(String)response.get("user"));
                }
            }

            else {
                JOptionPane.showMessageDialog(new JFrame(),"Veuillez remplir correctement tous les champs!", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
            }
        });
        this.add(l1,BorderLayout.NORTH);
        GridLayout gridLayout=new GridLayout(6,1);
        JPanel panel=new JPanel(gridLayout);
        panel.setOpaque(false);

        addComponentWithPanel(l2,panel);
        addComponentWithPanel(tf1,panel);
        addComponentWithPanel(l3,panel);
        addComponentWithPanel(p1,panel);
        addComponentWithPanel(l4,panel);
        addComponentWithPanel(status,panel);
        this.add(panel,BorderLayout.CENTER);
        this.add(btn1,BorderLayout.SOUTH);



        this.setVisible(true);
    }

    private void login(String nom, String prenom, String user) {
        final JFrame frame = new JFrame ("Hyper-Planning");
        //fixe screen size
        frame.setResizable(false);
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        //header
        JHeaderPanel jHeaderPanel=new JHeaderPanel(new JLabel(java.time.LocalDate.now().toString()),new JLabel(nom+"  "+prenom));
        jHeaderPanel.setJHeaderVue(width-100,50,new Color(7, 21, 23));
        //footer
        JFooter jFooter= new JFooter();
        jFooter.setJFooterVue(width-100,50,new Color(7, 21, 23));
        //body
        JMenu jMenu = new JMenu();
        switch (user) {
            case "Etudiant":break;
            case "Admin":
                JPanel p1 = new ListUserVue(controler);
                JPanel p2 = new InfrastructuresVue(controler);
                JPanel p3 = new DomaineVue(controler);


                jMenu.addItem("Gérer les comptes", p1).addItem("Gérer les infrastructures", p2).addItem("Gérer les études", p3);
                break;

            case "Enseignant":break;
            case "Responsable":break;

        }
        jMenu.setUser("Espace "+user);
        jMenu.setJMenuVue(width - 100, height - 100, new Color(10, 24, 65));
        Template template=new Template(jHeaderPanel,jMenu, jFooter );
        template.setTemplateVue(new Color(45,45,45));

        frame.setContentPane(template);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.setVisible(false);
    }


    private static boolean check(String regex,String text){
        return Pattern.compile(regex).matcher(text).matches();
    }

    private void addComponentWithPanel(Component component,JPanel panel){
        JPanel subPanel= new JPanel();
        subPanel.add(component);
        subPanel.setOpaque(false);
        panel.add(subPanel);
    }
}
