import java.util.*;
public class RailwayReserationsystem{
    static final int SEATS_PER_TRAIN = 100;
    static Map<String, User> users = new HashMap<>();
    static List<Train> trains = new ArrayList<>();
    static Map<String, String> userBookings = new HashMap<>(); 
    static final int Total=5;
    public static void main(String[] args) {
        initializeTrains();
        initializeUsers();
        String email,password,username;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Railway Reservation System");
        System.out.println("Enter email id:");
        email=scanner.next();
        System.out.println("Enter password:");
        password=scanner.next();
        System.out.println("Enter username:");
        username=scanner.next();
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Forget Password");
            System.out.println("3. Exit");                
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    handleLogin(scanner);
                    break;
                case 2:
                    handleForgetPassword(scanner);
                    break;
                case 3:
                    System.out.println("Thank you for using the Railway Reservation System.");
                    return;
                    default:
                    System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        private static void initializeTrains() {
            trains.add(new Train("PDY KCG Exp", "TMB", "KCG"));
            trains.add(new Train("MAS HYD SF EXP", "MAS", "HYD"));
            trains.add(new Train("Charminar Sf Exp", "TMB", "HYD"));
            trains.add(new Train("RMD SC special", "TMB", "Sc"));
            trains.add(new Train("Kacheguda Exp", "TMB", "KCG"));
        }
        private static void initializeUsers() {
            users.put("malleshwari", new User("malleshwari", "malli", "malli@email.com"));
            users.put("pravallika", new User("pravallika", "pravalli", "pravalli@email.com"));
            users.put("chandrika", new User("chandrika", "chandu", "chandu@email.com"));
        }
        private static void handleLogin(Scanner scanner) {
            System.out.print("Enter Username: ");
            String username = scanner.next();
            System.out.print("Enter Password: ");
            String password = scanner.next();
    
            if (users.containsKey(username) && users.get(username).getPassword().equals(password)) {
                System.out.println("Login Successful!");
                handleUserActions(scanner, username);
            } else {
                System.out.println("Invalid login credentials.");
            }
        }
        private static void handleForgetPassword(Scanner scanner) {
            System.out.print("Enter Username: ");
            String username = scanner.next();
            if (users.containsKey(username)) {
                System.out.print("Enter your registered email: ");
                String email = scanner.next();
                if (users.get(username).getEmail().equals(email)) {
                    System.out.print("Enter new password: ");
                    String newPassword = scanner.next();
                    users.get(username).setPassword(newPassword);
                    System.out.println("Password reset successfully!");
                } else {
                    System.out.println("Incorrect email.");
                }
            } else {
                System.out.println("Username not found.");
            }
        }
        private static void handleUserActions(Scanner scanner, String username) {
            while (true) {
                System.out.println("\n1. Book a Seat");
                System.out.println("2. Cancel Booking");
                System.out.println("3. View Booking");
                System.out.println("4. Logout");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        bookSeat(scanner, username);
                        break;
                    case 2:
                        cancelBooking(username);
                        break;
                    case 3:
                        viewBooking(username);
                        break;
                    case 4:
                        System.out.println("Logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        }
        private static void bookSeat(Scanner scanner, String username) {
            if (userBookings.containsKey(username)) {
                System.out.println("You have already booked a seat in " + userBookings.get(username));
                return;
            }
            System.out.println("\nAvailable Trains:");
            for (int i = 0; i < trains.size(); i++) {
                Train train = trains.get(i);
                System.out.println((i + 1) + ". " + train.getName() + " (" + train.getRouteFrom() + " to " + train.getRouteTo() + ")");
            }
            System.out.print("Enter Train Number: ");
            int trainNumber = scanner.nextInt();
            if (trainNumber < 1 || trainNumber > trains.size()) {
                System.out.println("Invalid train number.");
                return;
            }
            Train selectedTrain = trains.get(trainNumber - 1);
            if (selectedTrain.getBookedSeats() < SEATS_PER_TRAIN) {
                selectedTrain.bookSeat(username);
                userBookings.put(username, selectedTrain.getName());
                System.out.println("Booking confirmed for " + selectedTrain.getName() + ". Seat number: " + selectedTrain.getBookedSeats());
            } else {
                System.out.println("Seats are not available in " + selectedTrain.getName());
                System.out.println("Other trains with available seats:");
                for (Train train : trains) {
                    if (train.getBookedSeats() < SEATS_PER_TRAIN) {
                        System.out.println(train.getName());
                    }
                }
            }
        }
        private static void cancelBooking(String username) {
            if (!userBookings.containsKey(username)) {
                System.out.println("No booking found for your username.");
                return;
            }
            String trainName = userBookings.get(username);
            for (Train train : trains) {
                if (train.getName().equals(trainName)) {
                    train.cancelSeat(username);
                    break;
                }
            }
            userBookings.remove(username);
            System.out.println("Your booking for " + trainName + " has been cancelled.");
        }
        private static void viewBooking(String username) {
            if (userBookings.containsKey(username)) {
                System.out.println("Your booking: " + userBookings.get(username));
            } else {
                System.out.println("No booking found.");
            }
        }
    }
    class User {
        private String username;
        private String password;
        private String email;
        User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }
        public String getPassword() {
            return password;
        }
        public String getEmail() {
            return email;
        }
        public void setPassword(String newPassword) {
            this.password = newPassword;
        }
    }
    class Train {
        private String name;
        private String routeFrom;
        private String routeTo;
        private int bookedSeats = 0;
        private Set<String> passengers = new HashSet<>();
        public Train(String name, String routeFrom, String routeTo) {
            this.name = name;
            this.routeFrom = routeFrom;
            this.routeTo = routeTo;
        }
        public String getName() {
            return name;
        }
        public String getRouteFrom() {
            return routeFrom;
        }
        public String getRouteTo() {
            return routeTo;
        }
        public int getBookedSeats() {
            return bookedSeats;
        }
        public void bookSeat(String username) {
            passengers.add(username);
            bookedSeats++;
        }
        public void cancelSeat(String username) {
            if (passengers.remove(username)) {
                bookedSeats--;
            }
        }
    }

