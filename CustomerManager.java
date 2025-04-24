import java.sql.*;

public class CustomerManager {

    public int addCustomer(String name, String phone, String email) {
        int customerId = -1;
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if customer already exists
            String checkQuery = "SELECT customer_id FROM customers WHERE email = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, email);
            ResultSet rs = checkStmt.executeQuery();

            if(rs.next()) {
                customerId = rs.getInt("customer_id");
                System.out.println("\n\u001B[38;5;214m╔══════════════════════════════════════════════╗");
                System.out.println("\u001B[38;5;214m║ \u001B[1;93m👋 WELCOME BACK! \u001B[0m\u001B[38;5;214m                     ║");
                System.out.println("\u001B[38;5;214m╠══════════════════════════════════════════════╣");
                System.out.printf("\u001B[38;5;214m║ \u001B[97m📧 Email: \u001B[96m%-35s \u001B[38;5;214m║\n", email);
                System.out.printf("\u001B[38;5;214m║ \u001B[97m🆔 Your Existing ID: \u001B[92m%-25d \u001B[38;5;214m║\n", customerId);
                System.out.println("\u001B[38;5;214m╚══════════════════════════════════════════════╝\u001B[0m");
                System.out.println("\u001B[38;5;200m🎉 We're happy to see you again! 🥳\u001B[0m\n");
                return customerId;
            }

            // Insert new customer
            String insertQuery = "INSERT INTO customers (name, phone, email) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, name);
            insertStmt.setString(2, phone);
            insertStmt.setString(3, email);
            insertStmt.executeUpdate();

            rs = insertStmt.getGeneratedKeys();
            if (rs.next()) {
                customerId = rs.getInt(1);
                System.out.println("\n\u001B[38;5;51m╔══════════════════════════════════════════════╗");
                System.out.println("\u001B[38;5;51m║ \u001B[1;96m🎉 NEW CUSTOMER REGISTERED! 🌟\u001B[0m\u001B[38;5;51m           ║");
                System.out.println("\u001B[38;5;51m╠══════════════════════════════════════════════╣");
                System.out.printf("\u001B[38;5;51m║ \u001B[97m👤 Name:  \u001B[95m%-35s \u001B[38;5;51m║\n", name);
                System.out.printf("\u001B[38;5;51m║ \u001B[97m📞 Phone: \u001B[93m%-35s \u001B[38;5;51m║\n", phone);
                System.out.printf("\u001B[38;5;51m║ \u001B[97m🆔 New ID: \u001B[92m%-35d \u001B[38;5;51m║\n", customerId);
                System.out.println("\u001B[38;5;51m╚══════════════════════════════════════════════╝\u001B[0m");
                System.out.println("\u001B[38;5;200m✨ Welcome to our family! 🍰\u001B[0m\n");
            }

        } catch (SQLException e) {
            System.out.println("\n\u001B[38;5;196m┌──────────────────────────────────────────────┐");
            System.out.println("\u001B[38;5;196m│  \u001B[1;91m⚠️ REGISTRATION ERROR 🚫\u001B[0m\u001B[38;5;196m                  │");
            System.out.println("\u001B[38;5;196m├──────────────────────────────────────────────┤");
            System.out.printf("\u001B[38;5;196m│  \u001B[97m%s%-37s \u001B[38;5;196m│\n", "Reason: ", e.getMessage());
            System.out.println("\u001B[38;5;196m└──────────────────────────────────────────────┘\u001B[0m");
            System.out.println("\u001B[38;5;214m🔍 Please try different contact details 🧐\u001B[0m\n");
        }
        return customerId;
    }
}