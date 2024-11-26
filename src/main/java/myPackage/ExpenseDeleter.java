package myPackage;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExpenseDeleter {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "880011mysqlfeni";

    public void deleteExpense(JTable table, int userId) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int expenseId = (int) model.getValueAt(selectedRow, 0);
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this expense?", "Warning", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                deleteExpenseFromDatabase(userId, expenseId);
                model.removeRow(selectedRow);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select an expense to delete.");
        }
    }

    private void deleteExpenseFromDatabase(int userId, int expenseId) {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM expense_info WHERE user_id = ? AND expense_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, userId);
                statement.setInt(2, expenseId);
                int rowsDeleted = statement.executeUpdate();
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Expense deleted successfully from database.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to delete expense from database.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "An error occurred while deleting expense from database.");
        }
    }
}
