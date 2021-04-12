package integracja;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.Set;

public class Checker implements TableCellRenderer {

    public static final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();
    Set<Integer> duplicateRowSet;
    Set<Integer> modifyRowSet;

    public Checker(Set<Integer> duplicateRowSet, Set<Integer> modifyRowSet) {
        this.duplicateRowSet = duplicateRowSet;
        this.modifyRowSet = modifyRowSet;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table,
                value, isSelected, hasFocus, row, column);


        if (modifyRowSet.contains(row)) {
            c.setBackground(Color.WHITE);
        } else if (duplicateRowSet.contains(row)) {
            c.setBackground(Color.red);
        } else {
            c.setBackground(Color.decode("#E8E8E8"));
        }


        if (value == null || value.equals("Nie Podano") || (column == 1 && !value.equals(" ") && !value.equals("") && (!isNumeric(value.toString().substring(0, value.toString().length() - 1)) || !value.toString().endsWith("\"")))) {
            c.setForeground(Color.red);
        } else {
            c.setForeground(Color.black);
        }


        return c;
    }

}


