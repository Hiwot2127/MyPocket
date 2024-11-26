package myPackage;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpenseSorter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public static void sortByDate(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<Object[]> data = getDataFromModel(model);
        Collections.sort(data, (obj1, obj2) -> {
            LocalDate date1 = LocalDate.parse((String) obj1[1], DATE_FORMATTER);
            LocalDate date2 = LocalDate.parse((String) obj2[1], DATE_FORMATTER);
            return date1.compareTo(date2);
        });
        refreshTable(model, data);
    }

    public static void sortByAmount(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        List<Object[]> data = getDataFromModel(model);
        Collections.sort(data, (obj1, obj2) -> {
            double amount1 = Double.parseDouble((String) obj1[3]);
            double amount2 = Double.parseDouble((String) obj2[3]);
            return Double.compare(amount1, amount2);
        });
        refreshTable(model, data);
    }

    private static List<Object[]> getDataFromModel(DefaultTableModel model) {
        List<Object[]> data = new ArrayList<>();
        int rowCount = model.getRowCount();
        int columnCount = model.getColumnCount();
        for (int i = 0; i < rowCount; i++) {
            Object[] row = new Object[columnCount];
            for (int j = 0; j < columnCount; j++) {
                row[j] = model.getValueAt(i, j);
            }
            data.add(row);
        }
        return data;
    }

    private static void refreshTable(DefaultTableModel model, List<Object[]> sortedData) {
        model.setRowCount(0); // Clear existing rows
        for (Object[] row : sortedData) {
            model.addRow(row); // Add sorted rows
        }
    }
}
