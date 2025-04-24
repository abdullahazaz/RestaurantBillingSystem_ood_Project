import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MenuManager menuManager = new MenuManager();
        CustomerManager customerManager = new CustomerManager();
        OrderManager orderManager = new OrderManager();
        BillingManager billingManager = new BillingManager();

        // Welcome Art
        System.out.println("\n\u001B[36m╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   \u001B[1m🍽️  RESTAURANT BILLING SYSTEM  🍴  \u001B[0m\u001B[36m            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\u001B[0m");

        // Menu Display
        System.out.println("\n\u001B[33m══════════════════  TODAY'S MENU  ══════════════════\u001B[0m");
        menuManager.showMenuItems();

        // Customer Input
        System.out.println("\n\u001B[36m┌───────────────────────────────────────────────────────┐");
        System.out.println("│                \u001B[1m🥳  NEW CUSTOMER  🎉              \u001B[0m\u001B[36m│");
        System.out.println("└───────────────────────────────────────────────────────┘\u001B[0m");
        
        System.out.print("\n\u001B[34m▸ Enter customer name: \u001B[0m");
        String name = sc.nextLine();
        System.out.print("\u001B[34m▸ Phone: \u001B[0m");
        String phone = sc.nextLine();
        System.out.print("\u001B[34m▸ Email: \u001B[0m");
        String email = sc.nextLine();

        int customerId = customerManager.addCustomer(name, phone, email);
        System.out.printf("\n\u001B[32m✓ Customer #%d registered successfully!\u001B[0m\n", customerId);

        // Order Entry
        System.out.println("\n\u001B[35m══════════════════  ORDER ENTRY  ═══════════════════\u001B[0m");
        Map<Integer, Integer> orderItems = new HashMap<>();
        while (true) {
            System.out.print("\n\u001B[34m▸ Enter Item ID (\u001B[31m0 to finish\u001B[34m): \u001B[0m");
            int itemId = sc.nextInt();
            if (itemId == 0) break;
            System.out.print("\u001B[34m▸ Quantity: \u001B[0m");
            int quantity = sc.nextInt();
            orderItems.put(itemId, quantity);
            System.out.printf("\u001B[32m✓ Added %d units of item #%d\u001B[0m\n", quantity, itemId);
        }

        int orderId = orderManager.createOrder(customerId, orderItems);
        System.out.printf("\n\u001B[32m⚡ Order #%d created successfully!\u001B[0m\n", orderId);

        // Payment Processing
        System.out.println("\n\u001B[36m┌───────────────────────────────────────────────────────┐");
        System.out.println("│               \u001B[1m💳  PAYMENT DETAILS  💰             \u001B[0m\u001B[36m│");
        System.out.println("└───────────────────────────────────────────────────────┘\u001B[0m");
        
        System.out.print("\n\u001B[34m▸ Payment Method (Cash/Card): \u001B[0m");
        sc.nextLine(); // consume newline
        String paymentMethod = sc.nextLine();

        billingManager.generateBill(orderId, paymentMethod);
        
        // Closing Art
        System.out.println("\n\u001B[35m═══════════════════════════════════════════════════");
        System.out.println("   🎉  Thank you for dining with us!  �  ");
        System.out.println("       Visit again soon! \u001B[1m😊\u001B[0m");
        System.out.println("═══════════════════════════════════════════════════\u001B[0m\n");
    }
}