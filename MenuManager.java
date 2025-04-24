import java.sql.*;

public class MenuManager {

    public void showMenuItems() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM menu_items WHERE availability = TRUE";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Header with cute styling
            System.out.println("\n\u001B[38;5;213m❀•≫─────── ⋆⋅☆⋅⋆ ───────≪•❀");
            System.out.println("   🍽️ \u001B[1;38;5;219mAVAILABLE MENU ITEMS\u001B[0m\u001B[38;5;213m 🍴");
            System.out.println("❀•≫─────── ⋆⋅☆⋅⋆ ───────≪•❀\u001B[0m");
            
            System.out.println("\u001B[38;5;147m┌────────┬──────────────────────────────┬────────────┬──────────────┐");
            System.out.printf("│ \u001B[38;5;229m%-6s \u001B[38;5;147m│ \u001B[38;5;229m%-28s \u001B[38;5;147m│ \u001B[38;5;229m%-10s \u001B[38;5;147m│ \u001B[38;5;229m%-12s \u001B[38;5;147m│%n",
                "ID", "DISH NAME", "PRICE", "CATEGORY");
            System.out.println("├────────┼──────────────────────────────┼────────────┼──────────────┤");

            // Table rows
            while (rs.next()) {
                String[] categoryData = getCategoryDecorations(rs.getString("category"));
                String dishName = rs.getString("name");
                
                System.out.printf("│ \u001B[38;5;231m%-6d \u001B[38;5;147m│ %-28s │ \u001B[38;5;120m$%-9.2f \u001B[38;5;147m│ %s%-11s \u001B[38;5;147m│%n",
                    rs.getInt("item_id"),
                    truncateString(dishName, 25),
                    rs.getDouble("price"),
                    categoryData[0],  // Emoji
                    categoryData[1]); // Category name
            }

            System.out.println("\u001B[38;5;147m└────────┴──────────────────────────────┴────────────┴──────────────┘\u001B[0m");
            System.out.println("\u001B[38;5;213m✦⋅⋅⋅ Enjoy our delicious offerings! ⋅⋅⋅✦\u001B[0m\n");

        } catch (SQLException e) {
            System.out.println("\n\u001B[38;5;196m⚠️ \u001B[1mError: \u001B[0m\u001B[38;5;196mFailed to retrieve menu: " + e.getMessage() + "\u001B[0m");
        }
    }

    private String[] getCategoryDecorations(String category) {
        String lowerCategory = category.toLowerCase();
        return switch (lowerCategory) {
            case "main dish" -> new String[]{"🍲 ", "Main Dish"};
            case "appetizer" -> new String[]{"🥑 ", "Appetizer"};
            case "dessert" -> new String[]{"🍨 ", "Dessert"};
            case "beverage" -> new String[]{"🥤 ", "Beverage"};
            case "soup" -> new String[]{"🍜 ", "Soup"};
            case "salad" -> new String[]{"🥗 ", "Salad"};
            default -> new String[]{"🍴 ", "Special"};
        };
    }

    private String truncateString(String text, int maxLength) {
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength-3) + "…"; // Use proper ellipsis character
    }
}