import java.sql.*;

public class BillingManager {

    public void generateBill(int orderId, String paymentMethod) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String orderQuery = "SELECT total_amount FROM orders WHERE order_id = ?";
            PreparedStatement orderStmt = conn.prepareStatement(orderQuery);
            orderStmt.setInt(1, orderId);
            ResultSet rs = orderStmt.executeQuery();

            if (rs.next()) {
                double amount = rs.getDouble("total_amount");

                // Database operations remain unchanged
                String insertBill = "INSERT INTO bills (order_id, amount, payment_method) VALUES (?, ?, ?)";
                PreparedStatement billStmt = conn.prepareStatement(insertBill);
                billStmt.setInt(1, orderId);
                billStmt.setDouble(2, amount);
                billStmt.setString(3, paymentMethod);
                billStmt.executeUpdate();

                String updateStatus = "UPDATE orders SET status = 'Completed' WHERE order_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateStatus);
                updateStmt.setInt(1, orderId);
                updateStmt.executeUpdate();

                // Cute success message
                System.out.println("\n\u001B[38;5;46m╔══════════════════════════════════════════════╗");
                System.out.println("\u001B[38;5;46m║ \u001B[1;92m💸  BILL GENERATED SUCCESSFULLY!  🎉\u001B[0m\u001B[38;5;46m  ║");
                System.out.println("\u001B[38;5;46m╠══════════════════════════════════════════════╣");
                System.out.printf("\u001B[38;5;46m║ \u001B[97m📦 Order ID: \u001B[93m%-32d \u001B[38;5;46m║\n", orderId);
                System.out.printf("\u001B[38;5;46m║ \u001B[97m💰 Amount:   \u001B[92m$%-32.2f \u001B[38;5;46m║\n", amount);
                System.out.printf("\u001B[38;5;46m║ \u001B[97m💳 Method:   \u001B[96m%-32s \u001B[38;5;46m║\n", paymentMethod);
                System.out.println("\u001B[38;5;46m╚══════════════════════════════════════════════╝\u001B[0m");
                System.out.println("\u001B[38;5;201m✨ Thank you for your payment! Visit again! 🧁\u001B[0m\n");

            } else {
                // Error message formatting
                System.out.println("\n\u001B[38;5;196m┌──────────────────────────────────────────────┐");
                System.out.printf("\u001B[38;5;196m│ \u001B[1;91m🚫  Order ID \u001B[97m%-25d \u001B[91mNOT FOUND! │\n", orderId);
                System.out.println("\u001B[38;5;196m└──────────────────────────────────────────────┘\u001B[0m");
                System.out.println("\u001B[38;5;214m🔍 Please check the order number and try again 🕵️♀️\u001B[0m\n");
            }

        } catch (SQLException e) {
            // Error formatting
            System.out.println("\n\u001B[38;5;124m⛔━━━━━━━━━━━━━━━━━━━━━━━━━━━ ERROR ━━━━━━━━━━━━━━━━━━━━━━━⛔");
            System.out.printf("\u001B[38;5;124m   \u001B[1;97m🧾 Billing Error: \u001B[91m%s\n", e.getMessage());
            System.out.println("\u001B[38;5;124m⛔━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━⛔\u001B[0m\n");
        }
    }
}