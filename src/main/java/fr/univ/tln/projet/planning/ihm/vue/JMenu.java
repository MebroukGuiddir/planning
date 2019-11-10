package fr.univ.tln.projet.planning.ihm.vue;
/**
 * @autor GUIDDIR MEBROUL
 * @since 1.0
 */
import fr.univ.tln.projet.planning.ihm.vue.event.JPanelAdapter;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;


public class JMenu extends JPanelAdapter {
    private JLabel user=new JLabel("Espace Admin");
    private  JList jList = new JList();
    private ArrayList <JPanelAdapter> panels=new ArrayList();
    private JPanelAdapter panelMenu=new JPanelAdapter(TypePanel.MENU);
    private    DefaultListModel modele = new DefaultListModel();

    public JPanelAdapter getPanelMenu() {
        return panelMenu;
    }
    public JMenu(){
     super(TypePanel.MENU);
     this.setLayout(new  FlowLayout(FlowLayout.LEFT,0, 0));

    }
    public JPanel setJMenuVue(int width, int height, Color color){
        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
        GridBagConstraints c = new GridBagConstraints();
        this.setMinimumSize(new Dimension(width,height));
        this.setPreferredSize(new Dimension(width,height));

        this.setAlignmentX(FlowLayout.CENTER);
        this.setAlignmentY(FlowLayout.CENTER);

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
        jList.setAlignmentX(0);
        jList.setPreferredSize(new Dimension(300,300));
        jList.setMinimumSize(new Dimension(300,300));
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged( ListSelectionEvent listSelectionEvent) {
                for(JPanelAdapter p :panels )p.setVisible(false);
                panels.get(jList.getSelectedIndex()).setVisible(true);

            }
        });
        JSeparator s = new JSeparator(SwingConstants.HORIZONTAL);
        s.setBackground(new Color(199, 201, 202));
        panelMenu.setLayout(new BoxLayout(panelMenu,BoxLayout.Y_AXIS));
        panelMenu.setMinimumSize(new Dimension(300,400));
        panelMenu.setPreferredSize(new Dimension(300,400));
        panelMenu.setOpaque(true);
        panelMenu.setBackground(color);
        panelMenu.add(user);
        panelMenu.add(s);
        panelMenu.add(jList);
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(0, 0, 0, 0);
        c.anchor = GridBagConstraints.WEST;
        this.add(panelMenu,c);
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.EAST;
        panels.get(0).setVisible(true);
        for(JPanel p:panels)this.add(p,c);
        this.setAlignmentX(0);
        this.setAlignmentY(1);
        return this;
    }

    public ArrayList<JPanelAdapter> getPanels() {
        return panels;
    }

    public JMenu addItem(String item, JPanelAdapter panel){
        panel.setMinimumSize(new Dimension(700,400));
        panel.setPreferredSize(new Dimension(700,400));
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
