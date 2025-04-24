import java.sql.*;
import java.util.*;

public class OrderManager {

    public int createOrder(int customerId, Map<Integer, Integer> items) {
        int orderId = -1;
        double totalAmount = 0.0;

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Order creation
            String insertOrder = "INSERT INTO orders (customer_id, total_amount, status) VALUES (?, ?, 'Pending')";
            PreparedStatement orderStmt = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, customerId);
            orderStmt.setDouble(2, 0);
            orderStmt.executeUpdate();

            ResultSet rs = orderStmt.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }

            // Process items
            String itemQuery = "SELECT price FROM menu_items WHERE item_id = ?";
            String insertDetails = "INSERT INTO order_details (order_id, item_id, quantity) VALUES (?, ?, ?)";
            PreparedStatement itemStmt = conn.prepareStatement(itemQuery);
            PreparedStatement detailStmt = conn.prepareStatement(insertDetails);

            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                int itemId = entry.getKey();
                int quantity = entry.getValue();

                itemStmt.setInt(1, itemId);
                ResultSet itemRs = itemStmt.executeQuery();
                if (itemRs.next()) {
                    double price = itemRs.getDouble("price");
                    totalAmount += price * quantity;

                    detailStmt.setInt(1, orderId);
                    detailStmt.setInt(2, itemId);
                    detailStmt.setInt(3, quantity);
                    detailStmt.executeUpdate();
                }
            }

            // Update total amount
            String updateOrder = "UPDATE orders SET total_amount = ? WHERE order_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateOrder);
            updateStmt.setDouble(1, totalAmount);
            updateStmt.setInt(2, orderId);
            updateStmt.executeUpdate();

            conn.commit();

            // Success message
            System.out.println("\n\u001B[38;5;48m╔══════════════════════════════════════════════════╗");
            System.out.println("\u001B[38;5;48m║ \u001B[1;92m📦 ORDER SUCCESSFULLY PLACED! 🎉\u001B[0m\u001B[38;5;48m             ║");
            System.out.println("\u001B[38;5;48m╠══════════════════════════════════════════════════╣");
            System.out.printf("\u001B[38;5;48m║ \u001B[97m🆔 Order ID: \u001B[96m%-36d \u001B[38;5;48m║%n", orderId);
            System.out.printf("\u001B[38;5;48m║ \u001B[97m👤 Customer ID: \u001B[95m%-34d \u001B[38;5;48m║%n", customerId);
            System.out.printf("\u001B[38;5;48m║ \u001B[97m💰 Total Amount: \u001B[92m$%-33.2f \u001B[38;5;48m║%n", totalAmount);
            System.out.println("\u001B[38;5;48m╚══════════════════════════════════════════════════╝\u001B[0m");
            System.out.println("\u001B[38;5;201m🍴 Your delicious order is on its way! 🚚\u001B[0m\n");

        } catch (SQLException e) {
            // Error message
            System.out.println("\n\u001B[38;5;196m┌────────────────────────────────────────────────────┐");
            System.out.println("\u001B[38;5;196m│  \u001B[1;91m⛔ ERROR PLACING ORDER! \u001B[0m\u001B[38;5;196m                         │");
            System.out.println("\u001B[38;5;196m├────────────────────────────────────────────────────┤");
            System.out.printf("\u001B[38;5;196m│  \u001B[97m%s%-44s \u001B[38;5;196m│%n", "Reason: ", e.getMessage());
            System.out.println("\u001B[38;5;196m└────────────────────────────────────────────────────┘\u001B[0m");
            System.out.println("\u001B[38;5;214m🔍 Please check the items and try again 🛒\u001B[0m\n");
        }

        return orderId;
    }
}