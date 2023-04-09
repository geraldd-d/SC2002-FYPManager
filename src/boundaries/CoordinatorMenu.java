package boundaries;

import java.util.InputMismatchException;
import java.util.Scanner;

import entities.Coordinator;

public class CoordinatorMenu {
	private CoordinatorMenu() {};
	private static CoordinatorMenu cm = null;
	public static CoordinatorMenu getInstance() {
		if (cm == null) {
			cm = new CoordinatorMenu();
		}
		return cm;
	}
	public void display(Coordinator coordinator){
		Scanner sc = new Scanner(System.in);
        int choice = 0;
        do {
            System.out.println("FYP Management System");
            System.out.println("---------------------");
            System.out.println("1. View Projects");
            System.out.println("2. Filter Projects");
            System.out.println("3. View pending requests");
            System.out.println("4. View request history");
            System.out.println("5. Address pending requests");
            System.out.print("Enter your choice: ");
            try {
            	choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid choice. Please enter an integer from 1-3.");
                sc.nextLine();
                continue;
            }
            switch (choice) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid choice. Please enter an integer from 1-3.");
            	}
        	} while (choice != 3 && currentUser == null);
	}
}