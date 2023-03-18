import java.util.*;
import java.util.Scanner;

public class userMenu {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int choice;

        do {
            System.out.println("FYP Management System");
            System.out.println("---------------------");
            System.out.println("1. Student Login");
            System.out.println("2. Faculty Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    // call student login method
                    break;
                case 2:
                    // call faculty login method
                    break;
                case 3:
                    System.out.println("Thank you for using FYP Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }

        } while (choice != 3);
    }
}

