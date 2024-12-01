import java.util.*;

public class CateringReservationSystem {

    static class Client {
        String name;
        String email;
        String phone;

        public Client(String name, String email, String phone) {
            this.name = name;
            this.email = email;
            this.phone = phone;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Email: " + email + ", Phone: " + phone;
        }
    }

    static class Event {
        String clientName;
        String date;
        String eventType;
        int guests;
        double totalCost;

        public Event(String clientName, String date, String eventType, int guests, double totalCost) {
            this.clientName = clientName;
            this.date = date;
            this.eventType = eventType;
            this.guests = guests;
            this.totalCost = totalCost;
        }

        @Override
        public String toString() {
            return "Client: " + clientName + ", Date: " + date + ", Type: " + eventType + 
                   ", Guests: " + guests + ", Total Cost: Rupees " + totalCost;
        }
    }

    static class Menu {
        String itemName;
        double price;

        public Menu(String itemName, double price) {
            this.itemName = itemName;
            this.price = price;
        }
    }

    static List<Client> clients = new ArrayList<>();
    static List<Event> events = new ArrayList<>();
    static List<Menu> menu = new ArrayList<>();

    public static void main(String[] args) {
        initializeMenu();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Catering Reservation System ===");
            System.out.println("1. Register Client");
            System.out.println("2. Schedule Event");
            System.out.println("3. View Menu");
            System.out.println("4. Customize Order");
            System.out.println("5. Check Real-time Availability");
            System.out.println("6. Payment Processing");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerClient(scanner);
                    break;
                case 2:
                    scheduleEvent(scanner);
                    break;
                case 3:
                    viewMenu();
                    break;
                case 4:
                    customizeOrder(scanner);
                    break;
                case 5:
                    checkAvailability(scanner);
                    break;
                case 6:
                    processPayment(scanner);
                    break;
                case 7:
                    System.out.println("Exiting system. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void initializeMenu() {
        menu.add(new Menu("Chicken Biryani", 260));
        menu.add(new Menu("Vegetable Biryani", 250));
        menu.add(new Menu("Grilled Fish", 279));
        menu.add(new Menu("Mutton Biryani", 280));
        menu.add(new Menu("Chocolate Cake", 300));
        menu.add(new Menu("Raita", 40));
    }

    private static void registerClient(Scanner scanner) {
        System.out.println("=== Client Registration ===");
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        clients.add(new Client(name, email, phone));
        System.out.println("Client registered successfully.");
    }

    private static void scheduleEvent(Scanner scanner) {
        System.out.println("=== Event Scheduling ===");
        System.out.print("Enter Client Name: ");
        String clientName = scanner.nextLine();
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = scanner.nextLine();
        System.out.print("Enter Event Type (Wedding, Birthday, etc.): ");
        String eventType = scanner.nextLine();
        System.out.print("Enter Number of Guests: ");
        int guests = scanner.nextInt();

        events.add(new Event(clientName, date, eventType, guests, 0));
        System.out.println("Event scheduled successfully.");
    }

    private static void viewMenu() {
        System.out.println("=== Menu ===");
        for (Menu item : menu) {
            System.out.println(item.itemName + " - Rupees " + item.price);
        }
    }

    private static void customizeOrder(Scanner scanner) {
        System.out.println("=== Order Customization ===");
        Map<String, Integer> order = new HashMap<>();
        double totalCost = 0;

        while (true) {
            System.out.println("\nAvailable Menu Items:");
            for (int i = 0; i < menu.size(); i++) {
                System.out.println((i + 1) + ". " + menu.get(i).itemName + " - Rupees " + menu.get(i).price);
            }
            System.out.print("Select an item number to add to the order (or 0 to finish): ");
            int itemChoice = scanner.nextInt();
            if (itemChoice == 0) break;

            if (itemChoice > 0 && itemChoice <= menu.size()) {
                System.out.print("Enter quantity: ");
                int quantity = scanner.nextInt();
                String itemName = menu.get(itemChoice - 1).itemName;
                double price = menu.get(itemChoice - 1).price;
                order.put(itemName, order.getOrDefault(itemName, 0) + quantity);
                totalCost += price * quantity;
                System.out.println("Added " + quantity + " x " + itemName + " to the order.");
            } else {
                System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("\nOrder Summary:");
        for (Map.Entry<String, Integer> entry : order.entrySet()) {
            System.out.println(entry.getKey() + " x " + entry.getValue());
        }
        System.out.println("Total Cost: Rupees " + totalCost);

        System.out.print("\nWould you like to save this order to an event? (yes/no): ");
        scanner.nextLine(); // Consume newline
        String response = scanner.nextLine();

        if (response.equalsIgnoreCase("yes")) {
            System.out.print("Enter Client Name for the Event: ");
            String clientName = scanner.nextLine();
            System.out.print("Enter Event Date (DD-MM-YYYY): ");
            String date = scanner.nextLine();
            System.out.print("Enter Event Type (e.g., Wedding, Birthday): ");
            String eventType = scanner.nextLine();
            System.out.print("Enter Number of Guests: ");
            int guests = scanner.nextInt();

            events.add(new Event(clientName, date, eventType, guests, totalCost));
            System.out.println("Order and event saved successfully for " + clientName + ".");
        } else {
            System.out.println("Order not associated with any event.");
        }
    }

    private static void checkAvailability(Scanner scanner) {
        System.out.println("=== Real-time Availability ===");
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = scanner.nextLine();
        boolean available = true;

        for (Event event : events) {
            if (event.date.equals(date)) {
                available = false;
                break;
            }
        }

        System.out.println(available ? "The date is available for booking." : "The date is already booked.");
    }

    private static void processPayment(Scanner scanner) {
        System.out.println("=== Payment Processing ===");
        System.out.print("Enter Client Name: ");
        String clientName = scanner.nextLine();

        Event clientEvent = null;
        for (Event event : events) {
            if (event.clientName.equalsIgnoreCase(clientName)) {
                clientEvent = event;
                break;
            }
        }

        if (clientEvent == null) {
            System.out.println("No event found for the client: " + clientName);
            return;
        }

        System.out.println("Event Details:");
        System.out.println(clientEvent);
        System.out.println("Total Cost: Rupees " + clientEvent.totalCost);

        System.out.print("Enter Amount to Pay: Rupees ");
        double amount = scanner.nextDouble();

        if (amount >= clientEvent.totalCost) {
            System.out.println("Processing payment of Rupees " + amount + "...");
            System.out.println("Payment successful! Thank you.");
        } else {
            System.out.println("Insufficient payment amount. Please try again.");
        }
    }
}
