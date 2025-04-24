# 🍽️✨ Restaurant Billing System

A Java-based **CLI** application to manage restaurant 🍜 orders, customers 🧑‍💼, and billing 💰 — built with **MySQL** for database magic and sprinkled with a colorful, emoji-filled interface for a delightful developer experience! 🎉💻

---

## 🌟 Features
- 🧾 **Order Management**: Place orders, calculate totals, and track their status (🕒 Pending / ✅ Completed)
- 🧍 **Customer Registration**: Add new customers or find existing ones using their email 📧
- 🗃️ **Database Integration**: MySQL powers the backend for reliable data storage
- 🎨 **Colorful CLI Interface**: Enjoy a terminal experience with emojis + ANSI colors 🌈

---

## 🛠️ Requirements
- 🐬 **MySQL Server** (v8.0+)
- 🧰 **MySQL Workbench** (to set up your DB)
- ☕ **Java JDK** (v11+)
- 🔗 **MySQL Connector/J** (v9.2.0 - already included!)

---

## 🚀 Installation Guide

### 🔄 1. Clone the Repository
```bash
git clone https://github.com/[your-username]/RestaurantBillingSystem.git
cd RestaurantBillingSystem

🛢️ 2. Set Up the Database

Open MySQL Workbench and run the following SQL to create your database & tables:
CREATE DATABASE restaurant_db;
USE restaurant_db;

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE menu_items (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT,
    total_amount DECIMAL(10, 2),
    status VARCHAR(20),
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

CREATE TABLE order_details (
    order_id INT,
    item_id INT,
    quantity INT,
    FOREIGN KEY (order_id) REFERENCES orders(order_id),
    FOREIGN KEY (item_id) REFERENCES menu_items(item_id)
);

🧩 3. Configure Your Database Connection

Update DatabaseConnection.java with your own credentials:
private static final String URL = "jdbc:mysql://localhost:3306/restaurant_db";
private static final String USER = "root";
private static final String PASSWORD = "your_password";

📚 4. Add MySQL Connector in VS Code

Make sure settings.json includes the correct JAR path:
"java.project.referencedLibraries": [
    "lib/**/*.jar",
    "/path/to/mysql-connector-j-9.2.0.jar"
]

🖥️ Run the Application
	1.	Open the project in VS Code
	2.	Compile and run classes like DatabaseConnection.java, CustomerManager.java, and OrderManager.java
	3.	Use a Main class to execute your flow:

🚨 Troubleshooting
	•	❌ SQL Exception: Make sure MySQL server is running and your credentials are correct
	•	🔌 Connector Error: Check the path to the MySQL Connector/J
	•	🔐 JDBC URL Issues: If SSL is disabled, try:
jdbc:mysql://localhost:3306/restaurant_db?useSSL=false

⸻

👨‍💻 Author

Made with ❤️ by Md Abdullah Ebna Azaz 🌟


