package fr.univ.tln.projet.planning.ihm.vue;

import fr.univ.tln.projet.planning.controler.AbstractControler;

import fr.univ.tln.projet.planning.controler.Changement;
import fr.univ.tln.projet.planning.ihm.vue.event.JFrameComponentListener;
import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;
import fr.univ.tln.projet.planning.modele.observer.Observer;

import java.awt.*;
import java.util.regex.Pattern;
import javax.swing.*;

public class LoginVue extends JFrame implements  Observer {
    JLabel l1, l2, l3;
    JTextField tf1;
    JButton btn1;
    JPasswordField p1;
    AbstractControler controler;
    public LoginVue(AbstractControler controler) {
        super();
        this.controler=controler;
        this.setTitle("Connexion");
        this.setSize(600,300);
        this.setMinimumSize(new Dimension(400,200));
        this.setPreferredSize(new Dimension(600,300));
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

            ) controler.controlerLogin(tf1.getText(),String.valueOf(p1.getPassword()));

            else {

                JDialog d = new JDialog( (JFrame) SwingUtilities.getWindowAncestor(this), "Erreur de saisie");
                d.add(new JLabel("Veuillez remplir correctement tous les champs!"));

                d.setSize(400, 200);
                d.setLocation(0,0);
                d.setVisible(true);
            }
        });
        this.add(l1,BorderLayout.NORTH);
        GridLayout gridLayout=new GridLayout(2,2);
        JPanel panel=new JPanel(gridLayout);
        panel.setOpaque(false);

        addComponentWithPanel(l2,panel);
        addComponentWithPanel(tf1,panel);
        addComponentWithPanel(l3,panel);
        addComponentWithPanel(p1,panel);
        this.add(panel,BorderLayout.CENTER);
        this.add(btn1,BorderLayout.SOUTH);



        this.setVisible(true);
    }


    private static boolean check(String regex,String text){
        return Pattern.compile(regex).matcher(text).matches();
    }

    @Override
    public void update(Object object, Changement changement) {
        if(changement.type== Changement.Type.LOGIN && changement.section == Changement.Section.ADMIN && object!=null){
        //Création de notre fenêtre avec le contrôleur en paramètre
        final JFrameComponentListener frame = new JFrameComponentListener("Hyper-Planning");

       // frame.setResizable(false);

        //header
        JHeaderPanel jHeaderPanel=new JHeaderPanel(new JLabel(java.time.LocalDate.now().toString()),new JLabel("GUIDDIR MEBROUK"));
        jHeaderPanel.setJHeaderVue(1000,50,new Color(7, 21, 23));
        //footer
        JFooter jFooter= new JFooter();
        jFooter.setJFooterVue(1000,50,new Color(7, 21, 23));
        //body
        JPanelAdapter p1=new AddUserVue(JPanelAdapter.TypePanel.SECTION,controler);
        JPanelAdapter p2=new JPanelAdapter(JPanelAdapter.TypePanel.SECTION);
        p2.add(new JLabel("2"));
        JPanelAdapter p3=new JPanelAdapter(JPanelAdapter.TypePanel.SECTION);
        p3.add(new JLabel("3"));
        JMenu jMenu= new JMenu().addItem("Ajouter compte",p1).addItem("Liste Compte",p2).addItem("Gérer les ressources",p3);
        jMenu.setJMenuVue(1000,400,new Color(10, 24, 65));
        frame.addObserver(jHeaderPanel);
        frame.addObserver(jFooter);
        frame.addObserver(jMenu);
        frame.addObserver(p1);
        frame.addObserver(p2);
        frame.addObserver(p3);
        frame.addObserver(jMenu.getPanelMenu());
        Template template=new Template(jHeaderPanel,jMenu, jFooter );
        template.setTemplateVue(new Color(45,45,45));

        frame.setContentPane(template);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        this.setVisible(false);
    }
    }
    private void addComponentWithPanel(Component component,JPanel panel){
        JPanel subPanel= new JPanel();
        subPanel.add(component);
        subPanel.setOpaque(false);
        panel.add(subPanel);
    }
}
