package fr.univ.tln.projet.planning.ihm.panels;
/**
 * @autor GUIDDIR MEBROUL
 * @since 1.0
 */
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;


public class JMenu extends JPanel {
    private JLabel user=new JLabel( );
    private  JList jList = new JList();
    private ArrayList <JPanel> panels=new ArrayList();
    private JPanel panelMenu=new JPanel();
    private    DefaultListModel modele = new DefaultListModel();


    public JMenu(){
     super();
     this.setLayout(new BorderLayout());

    }
    public JPanel setJMenuVue(int width, int height, Color color){
        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
        GridBagConstraints c = new GridBagConstraints();
        user.setForeground(new Color(250,250,250));
        user.setFont(new Font("Courier New", Font.BOLD, 25));
         user.setPreferredSize(new Dimension(300,100));
         user.setVerticalAlignment(JLabel.CENTER);
        user.setHorizontalAlignment(JLabel.LEFT);




        jList.setFixedCellHeight(60);
        jList.setFixedCellWidth(300);
        //jList.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,new Color(199, 201, 202)));
        jList.setCellRenderer(new TransparentListCellRenderer());
        jList.setOpaque(false);
        jList.setSelectionBackground(new Color(0x410E0F));
        jList.setSelectionForeground(new Color(199, 201, 202));
        jList.setForeground(new Color(199, 201, 202));
        jList.setAlignmentX(0);

        jList.setAlignmentY(0);
        jList.setPreferredSize(new Dimension(300,300));
        jList.setMinimumSize(new Dimension(300,300));
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent listSelectionEvent) {
                for(JPanel p :panels )p.setVisible(false);
                panels.get(jList.getSelectedIndex()).setVisible(true);

            }
        });
        JSeparator s = new JSeparator(SwingConstants.HORIZONTAL);
        s.setBackground(new Color(199, 201, 202));
        panelMenu.setLayout(new BoxLayout(panelMenu,BoxLayout.Y_AXIS));

        panelMenu.setOpaque(true);
        panelMenu.setBackground(color);
        panelMenu.add(user);
        panelMenu.add(s);
        panelMenu.add(jList);

        this.add(panelMenu,BorderLayout.WEST);

        panels.get(0).setVisible(true);
        JPanel panelCenter=new JPanel();
        for(JPanel p:panels)panelCenter.add(p);
            this.add(panelCenter,BorderLayout.CENTER);
        this.setAlignmentX(0);
        this.setAlignmentY(1);
        return this;
    }


    public JMenu addItem(String item, JPanel  panel){

        panel.setOpaque(true);
        panel.setBackground(new Color(199, 201, 202));
        modele.addElement(item);
        jList.setModel(modele);
        panel.setVisible(false);
        this.panels.add(panel);
        return this;
    }
    public JMenu setUser(String user){
        this.user.setText(user);
        return this;
    }


    public class TransparentListCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setForeground(Color.WHITE);
            setOpaque(isSelected);
            return this;
        }

    }
}
