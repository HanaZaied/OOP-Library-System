import java.util.InputMismatchException;
import java.util.Scanner;
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Library library = new Library();

    public static void main(String[] args) {
        seedSampleData();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    handleAddItem();
                    break;
                case 2:
                    handleAddMember();
                    break;
                case 3:
                    handleBorrow();
                    break;
                case 4:
                    handleReturn();
                    break;
                case 5:
                    library.listCatalog();
                    break;
                case 6:
                    library.printReport();
                    break;
                case 7:
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                case 8:
                    handleSearch();
                    break;
                case 9:
                    library.listAvailable();
                    break;
                default:
                    System.out.println("Please choose a valid option (1-9).");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== Library Lending System =====");
        System.out.println("1. Add Item");
        System.out.println("2. Add Member");
        System.out.println("3. Borrow Item");
        System.out.println("4. Return Item");
        System.out.println("5. List Catalog");
        System.out.println("6. Report");
        System.out.println("7. Exit");
        System.out.println("8. Search by Title");
        System.out.println("9. List Available Items");
    }

    private static void handleAddItem() {
        System.out.println("Kind of item? 1) Book  2) Magazine  3) DVD");
        int kind = readInt("Enter choice: ");

        try {
            switch (kind) {
                case 1: {
                    String title = readLine("Title: ");
                    String author = readLine("Author: ");
                    int pages = readInt("Pages: ");
                    library.addItem(new Book(title, author, pages));
                    System.out.println("Book added.");
                    break;
                }
                case 2: {
                    String title = readLine("Title: ");
                    int issue = readInt("Issue number: ");
                    library.addItem(new Magazine(title, issue));
                    System.out.println("Magazine added.");
                    break;
                }
                case 3: {
                    String title = readLine("Title: ");
                    int runtime = readInt("Runtime (minutes): ");
                    library.addItem(new DVD(title, runtime));
                    System.out.println("DVD added.");
                    break;
                }
                default:
                    System.out.println("Unknown kind of item.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add item: " + e.getMessage());
        }
    }

    private static void handleAddMember() {
        try {
            String id = readLine("Member id: ");
            String name = readLine("Name: ");
            int max = readInt("Max items allowed: ");
            library.addMember(new Member(id, name, max));
            System.out.println("Member added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Could not add member: " + e.getMessage());
        }
    }

    private static void handleBorrow() {
        String memberId = readLine("Member id: ");
        String itemId = readLine("Item id: ");
        try {
            library.borrowItem(memberId, itemId);
            System.out.println("Borrowed " + itemId + " to " + memberId + ".");
        } catch (LibraryException e) {
            System.out.println("Could not borrow: " + e.getMessage());
        }
    }

    private static void handleReturn() {
        String memberId = readLine("Member id: ");
        String itemId = readLine("Item id: ");
        try {
            library.returnItem(memberId, itemId);
            System.out.println("Returned " + itemId + " from " + memberId + ".");
        } catch (LibraryException e) {
            System.out.println("Could not return: " + e.getMessage());
        }
    }

    private static void handleSearch() {
        String query = readLine("Search title contains: ");
        library.searchByTitle(query);
    }


    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private static String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }


    private static void seedSampleData() {
        library.addItem(new Book("Clean Code", "Robert C. Martin", 464));
        library.addItem(new Magazine("National Geographic", 305));
        library.addItem(new DVD("Inception", 148));
    }
}
