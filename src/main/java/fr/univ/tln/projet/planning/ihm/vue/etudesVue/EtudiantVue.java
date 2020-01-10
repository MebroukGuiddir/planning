package fr.univ.tln.projet.planning.ihm.vue.etudesVue;

import fr.univ.tln.projet.planning.controler.AbstractControler;
import fr.univ.tln.projet.planning.ihm.components.JdatePickerAdapter;
import fr.univ.tln.projet.planning.ihm.components.jTable.JTablePlanningEnseignant;
import fr.univ.tln.projet.planning.ihm.components.jTable.JTablePlanningEtudiant;
import fr.univ.tln.projet.planning.ihm.components.jTable.ModeleDynamiqueObject;
import fr.univ.tln.projet.planning.modele.utilisateurs.Etudiant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

    public class EtudiantVue extends JPanel {

        private AbstractControler controler;
        private ModeleDynamiqueObject seancesModel=new JTablePlanningEtudiant(new String[]{"Date","Heure","Salle","Cours","Status","Enseignant"});
        private JTable seances;
        public EtudiantVue(AbstractControler controler, Etudiant etudiant) {
            super();
            this.controler = controler;
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            seancesModel.updateModel(controler.selectSeancesEtudiant(etudiant.getIdUser(),new Date(),0));
            seances=new JTable(seancesModel);

            JPanel controlePanel=new JPanel();
            JButton beffor=new JButton(" << ");

            Calendar calendar = Calendar.getInstance();
            JdatePickerAdapter date =new JdatePickerAdapter("",Integer.valueOf(new SimpleDateFormat("yyyy").format(calendar.getTime())),
                    Integer.valueOf(new SimpleDateFormat("MM").format(calendar.getTime())),
                    Integer.valueOf(new SimpleDateFormat("dd").format(calendar.getTime())));
            JButton after=new JButton(" >> ");

            JComboBox periode =new JComboBox(new String[]{"Jour", "Semaine", "Mois"});
            periode.setFont(new Font("Arial", Font.BOLD, 13));
            periode.setSize(200, 30);
            periode.setPreferredSize(new Dimension(200,30));
            periode.setMinimumSize(new Dimension(200,30));
            periode.setOpaque(false);
            periode.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    seancesModel.updateModel(controler.selectSeancesEtudiant(etudiant.getIdUser(),date.getDate(),periode.getSelectedIndex()));
                }});
            beffor.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    date.setDate(-1);
                    seancesModel.updateModel(controler.selectSeancesEtudiant(etudiant.getIdUser(),date.getDate(),periode.getSelectedIndex()));
                }});
            after.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    date.setDate(1);
                    seancesModel.updateModel(controler.selectSeancesEtudiant(etudiant.getIdUser(),date.getDate(),periode.getSelectedIndex()));
                }});
            date.getJDatePicker().addActionListener(
                    new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            seancesModel.updateModel(controler.selectSeancesResponsable(etudiant.getIdUser(),date.getDate(),periode.getSelectedIndex()));

                        }});

            controlePanel.add(beffor);
            controlePanel.add(date);
            controlePanel.add(after);
            controlePanel.add(periode);
            this.add(controlePanel);
            this.add(new JScrollPane(seances));
        }
    }


