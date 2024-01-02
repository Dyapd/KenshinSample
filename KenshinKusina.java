import java.io.*;
import java.util.*;

public class KenshinKusina {
    private static final String admin = "admin";
    private static final String adminPassword = "12345";
    private static final Scanner scn = new Scanner(System.in);

    public static boolean login(String username, String password) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("users.txt"));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts[0].equals(username) && parts[1].equals(password)) {
                reader.close();
                return true;
            }
        }
        reader.close();
        return false;
    }

    public static void register(String username, String password) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt", true));
        writer.write(username + "," + password + "\n");
        writer.close();
    }

    public static void editCategory(ArrayList<String> category, ArrayList<Integer> categoryPrice, String categoryName, String fileName) throws IOException {
        System.out.println(categoryName + " Category");
        System.out.print("Do you want to add an item? Enter 'y' for Yes or 'n' for No: ");
        String input = scn.nextLine();
        int itemID = category.size() + 1; 

        while (input.equals("y")) {
            System.out.print("Enter an item to add: ");
            String itemName = scn.nextLine();
            System.out.print("Enter price: ");
            int itemPrice = scn.nextInt();
            scn.nextLine();

            category.add(itemName);
            categoryPrice.add(itemPrice);
            System.out.println(categoryName + " = " + category + " ID = " + itemID + "\n");
            System.out.println("Price = " + categoryPrice + " ID = " + itemID + "\n");

            BufferedWriter w = new BufferedWriter(new FileWriter(fileName, true));
            w.write(itemID + ", " + itemName + ", " + itemPrice + "\n");
            w.close();

            itemID++; 

            System.out.print("Do you want to add another item? Enter 'y' for Yes or 'n' for No: ");
            input = scn.nextLine();
        }

        System.out.print("Do you want to remove an item? Enter 'y' for Yes or 'n' for No: ");
        String rInput = scn.nextLine();

        while (rInput.equals("y")) {
            System.out.print("Enter the " + categoryName + " ID you want to remove: ");
            int ridInput = scn.nextInt();
            scn.nextLine();
            category.remove(ridInput - 1);
            categoryPrice.remove(ridInput - 1);

            System.out.print("Do you want to remove another " + categoryName + " item? Enter 'y' for Yes or 'n' for No: ");
            rInput = scn.nextLine();
        }
    }

    public static void main(String[] args) throws IOException {
        ArrayList<String> food = new ArrayList<String>();
        ArrayList<String> dessert = new ArrayList<String>();
        ArrayList<String> drinks = new ArrayList<String>();
        ArrayList<Integer> foodPrice = new ArrayList<Integer>();
        ArrayList<Integer> dessertPrice = new ArrayList<Integer>();
        ArrayList<Integer> drinksPrice = new ArrayList<Integer>();

        System.out.println("Welcome to Familia De Kusina!");
        System.out.println("------------------------------");
        while (true) {
            System.out.println("Enter 1 to register or 2 to login:  ");
            int choice = scn.nextInt();
            scn.nextLine();

            if (choice == 1) {
                System.out.print("Enter a username: ");
                String username = scn.nextLine();
                System.out.print("Enter a password: ");
                String password = scn.nextLine();
                register(username, password);
                System.out.println("Successfully registered");
            } else if (choice == 2) {
                boolean loggedIn = false;
                while (!loggedIn) {
                    System.out.print("Enter a username: ");
                    String username = scn.nextLine();
                    System.out.print("Enter password: ");
                    String password = scn.nextLine();

                    if (username.equals(admin) && password.equals(adminPassword)) {
                        System.out.println("Successfully logged in");
                        System.out.println("---------------------");
                        System.out.println("     Welcome Admin!     ");

                        boolean fEdit = true;
                        while (fEdit) {
                            System.out.print("Enter a category to edit (1 for Food, 2 for Dessert, 3 for Drinks, 4. Exit): ");
                            int inputCategory = scn.nextInt();
                            scn.nextLine();

                            if (inputCategory == 1) {
                                editCategory(food, foodPrice, "Food", "Foodmenu.txt");
                            } else if (inputCategory == 2) {
                                editCategory(dessert, dessertPrice, "Dessert", "Dessertmenu.txt");
                            } else if (inputCategory == 3) {
                                editCategory(drinks, drinksPrice, "Drinks", "Drinksmenu.txt");
                            } else if (inputCategory == 4) {
                                fEdit = false;
                            } else {
                                System.out.println("Error");
                                fEdit = false;
                            }
                        }
                        loggedIn = true;
                    } else if (login(username, password)) {
                        System.out.println("Successfully logged in");
                        System.out.println("---------------------");
                        System.out.println("     Welcome User!      ");
                        int totalCost = 0;
                        boolean categoryChoiceLoop = true;
                        while (categoryChoiceLoop) {
                            System.out.println("1. Food");
                            System.out.println("2. Dessert");
                            System.out.println("3. Drinks");
                            System.out.println("4. Checkout");
                            System.out.print("Enter a category to view items (or 0 to exit): ");
                            int categoryChoice = scn.nextInt();
                            scn.nextLine();

                            if (categoryChoice == 0) {
                                categoryChoiceLoop = false;
                            } else if (categoryChoice == 1) {
                                System.out.println("Food Category");
                                System.out.println("-------------");
                                for (int i = 0; i < food.size(); i++) {
                                    int itemID = i + 1;
                                    System.out.println ("ID: " + itemID + " - " + food.get(i) + " - Price: " + foodPrice.get(i));
                                }

                                boolean ordering = true;
                           

                                while (ordering) {
                                    System.out.print("Enter the ID of the item you want to order (0 to exit): ");
                                    int itemID = scn.nextInt();
                                    scn.nextLine();

                                    if (itemID == 0) {
                                        ordering = false;
                                    } else if (itemID > 0 && itemID <= food.size()) {
                                        System.out.print("Do you want to order this item? Enter 'Y' for Yes or 'N' for No: ");
                                        String orderChoice = scn.nextLine();

                                        if (orderChoice.equalsIgnoreCase("Y")) {
                                            totalCost += foodPrice.get(itemID - 1);
                                            System.out.println("Item added to order.");
                                        } else if (orderChoice.equalsIgnoreCase("N")) {
                                            System.out.println("Item not added to order.");
                                        } else {
                                            System.out.println("Invalid choice.");
                                        }
                                    } else {
                                        System.out.println("Invalid item ID.");
                                    }
                                }

                                System.out.println("Total Cost: P" + totalCost);
                            } else if (categoryChoice == 2) {
                                System.out.println("Dessert Category");
                                System.out.println("----------------");
                                for (int i = 0; i < dessert.size(); i++) {
                                    int itemID = i + 1;
                                    System.out.println("ID: " + itemID + " - " + dessert.get(i) + " - Price: " + dessertPrice.get(i));
                                }

                                boolean ordering = true;
                               
                                while (ordering) {
                                    System.out.print("Enter the ID of the item you want to order (0 to exit): ");
                                    int itemID = scn.nextInt();
                                    scn.nextLine();

                                    if (itemID == 0) {
                                        ordering = false;
                                    } else if (itemID > 0 && itemID <= dessert.size()) {
                                        System.out.print("Do you want to order this item? Enter 'Y' for Yes or 'N' for No: ");
                                        String orderChoice = scn.nextLine();

                                        if (orderChoice.equalsIgnoreCase("Y")) {
                                            totalCost += dessertPrice.get(itemID - 1);
                                            System.out.println("Item added to order.");
                                        } else if (orderChoice.equalsIgnoreCase("N")) {
                                            System.out.println("Item not added to order.");
                                        } else {
                                            System.out.println("Invalid choice.");
                                        }
                                    } else {
                                        System.out.println("Invalid item ID.");
                                    }
                                }

                                System.out.println("Total Cost: P" + totalCost);
                            } else if (categoryChoice == 3) {
                                System.out.println("Drinks Category");
                                System.out.println("---------------");
                                for (int i = 0; i < drinks.size(); i++) {
                                    int itemID = i + 1;
                                    System.out.println("ID: " + itemID + " - " + drinks.get(i) + " - Price: " + drinksPrice.get(i));
                                }

                                boolean ordering = true;
                                

                                while (ordering) {
                                    System.out.print("Enter the ID of the item you want to order (0 to exit): ");
                                    int itemID = scn.nextInt();
                                    scn.nextLine();

                                    if (itemID == 0) {
                                        ordering = false;
                                    } else if (itemID > 0 && itemID <= drinks.size()) {
                                        System.out.print("Do you want to order this item? Enter 'Y' for Yes or 'N' for No: ");
                                        String orderChoice = scn.nextLine();

                                        if (orderChoice.equalsIgnoreCase("Y")) {
                                            totalCost += drinksPrice.get(itemID - 1);
                                            System.out.println("Item added to order.");
                                        } else if (orderChoice.equalsIgnoreCase("N")) {
                                            System.out.println("Item not added to order.");
                                        } else {
                                            System.out.println("Invalid choice.");
                                        }
                                    } else {
                                        System.out.println("Invalid item ID.");
                                    }
                                }

                                System.out.println("Total Cost: P" + totalCost);
                            } else if (categoryChoice == 4) {
                                System.out.println("Checkout");
                                System.out.println("--------");
                                System.out.print("Enter your name: ");
                                String name = scn.nextLine();
                                System.out.print("Enter your address: ");
                                String address = scn.nextLine();
                                System.out.println("Your total amount is: " + totalCost);
                                System.out.print("Enter the payment amount: ");
                                int payment = scn.nextInt();
                                scn.nextLine();
                                if (payment >= totalCost) {

                                int change = payment - totalCost;

                                BufferedWriter receiptWriter = new BufferedWriter(new FileWriter("receipt.txt"));
                                receiptWriter.write("Name: " + name + "\n");
                                receiptWriter.write("Address: " + address + "\n");
                                receiptWriter.write("Total Cost: P" + totalCost + "\n");
                                receiptWriter.close();

                                System.out.println("Payment: P" + payment);
                                System.out.println("Total Cost: P" + totalCost);
                                System.out.println("Change: P" + change);
                                System.out.println("Your order will be delivered at " + address);
                                System.out.println("Thank you for your ordering at Familia De Kusina!!");
                                System.exit(0);
                                } else {
                                    System.out.println("Order is cancelled");
                                    System.exit(0);
                                }

                            } else {
                                System.out.println("Invalid choice.");
                            }
                        }
                    } else {
                        System.out.println("Invalid username or password");
                    }
                }
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}