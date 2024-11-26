package myPackage;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ExpenseEditor {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/expensemanagement";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "880011mysqlfeni";

    public static void editExpense(JTable tblExpenseList) {
        int selectedRow = tblExpenseList.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a row to edit.");
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tblExpenseList.getModel();
      
        int expenseId = (int) model.getValueAt(selectedRow, 0);
        String currentDate = (String) model.getValueAt(selectedRow, 1);
        String currentReason = (String) model.getValueAt(selectedRow, 2);
        String currentAmount = (String) model.getValueAt(selectedRow, 3);


        String date = null;
        String reasonInput = null;
        String amount = null;

        while (true) {
            date = JOptionPane.showInputDialog("Enter Date (MM/dd/yyyy):", currentDate);
            if (date.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Date field is required.");
            } else {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    dateFormat.setLenient(false);
                    dateFormat.parse(date);
                    break; 
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in MM/dd/yyyy format.");
                }
            }
        }

        while (true) {
            reasonInput = JOptionPane.showInputDialog("Enter Reason (transport, entertainment, food, other):", currentReason.toLowerCase());
            if (reasonInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Reason field is required.");
            } else {
                String[] validReasons = {"transport", "entertainment", "food", "other"};
                boolean isValidReason = false;
                for (String validReason : validReasons) {
                    if (reasonInput.equals(validReason)) {
                        isValidReason = true;
                        break;
                    }
                }
                if (!isValidReason) {
                    JOptionPane.showMessageDialog(null, "Invalid reason. Please enter one of the following options: transport, entertainment, food, other.");
                } else {
                    break; 
                }
            }
        }

        while (true) {
            amount = JOptionPane.showInputDialog("Enter Amount:", currentAmount);
            if (amount.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Amount field is required.");
            } else {
                try {
                    double formattedAmount = Double.parseDouble(amount);
                    if (formattedAmount <= 0) {
                        JOptionPane.showMessageDialog(null, "Amount must be a positive number.");
                    } else {
                        break; 
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.");
                }
            }
        }

        model.setValueAt(date, selectedRow, 1);
        model.setValueAt(reasonInput, selectedRow, 2);
        model.setValueAt(amount, selectedRow, 3);

        updateExpenseInDatabase(expenseId, date, reasonInput, amount);
    }

    private static void updateExpenseInDatabase(int expenseId, String date, String reason, String amount) {
             int userId = UserManager.getCurrentUserId();

        String formattedDate;
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = outputFormat.format(inputFormat.parse(date));
        } catch (ParseException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error formatting the date.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String sql = "UPDATE expense_info SET date = ?, reason = ?, amount = ? WHERE user_id = ? AND expense_id = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, formattedDate);
                statement.setString(2, reason);
                statement.setString(3, amount);
                statement.setInt(4, userId);
                statement.setInt(5, expenseId);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Expense updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error updating the expense.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
        }
    }
}
