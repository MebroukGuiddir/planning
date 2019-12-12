package fr.univ.tln.projet.planning.ihm.components.jTable;

import fr.univ.tln.projet.planning.modele.utilisateurs.Utilisateur;
import lombok.Getter;
import lombok.Setter;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Getter
@Setter
public abstract class ModeleDynamiqueObject <T> extends AbstractTableModel {
    protected final List<T> rows = new ArrayList<>();
    protected final String[] entetes;
    public ModeleDynamiqueObject(String [] entetes) {
        super();
        this.entetes=entetes;
    }

    public int getRowCount() {
        return rows.size();
    }
    public int getColumnCount() {
        return entetes.length;
    }
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }
    public abstract Object getValueAt(int rowIndex, int columnIndex );
    public Object getRow(int rowIndex){
        return rows.get(rowIndex);
    }
    public void updateRows(T row) {
        rows.add(row);

        fireTableRowsInserted(rows.size() -1, rows.size() -1);
    }

    public void removeRow(int rowIndex) {
        rows.remove(rowIndex);

        fireTableRowsDeleted(rowIndex, rowIndex);
    }
    public void updateModel(Collection<T> rows){
        this.rows.clear();
        this.rows.addAll(rows);
        System.out.println(this.rows);
        fireTableDataChanged();
    }
}