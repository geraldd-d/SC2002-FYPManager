package boundaries;
import entities.*;

import java.util.Scanner;
import controllers.*;

public class MainMenu {
	public static void main(String[] args){
		AccountsController ac = AccountsController.getInstance();
		Scanner sc = new Scanner(System.in);
		String input = "";
		String pass = "";
		boolean auth = false;
		User currentUser = null;
		do {
			System.out.println("Enter id: ");
			input = sc.nextLine();
			currentUser = ac.authStudent(input);
			if (currentUser instanceof Student) {
				currentUser = (Student) currentUser;
			}
		} while (currentUser == null);
		do {
			System.out.println("Enter password: ");
			pass = sc.nextLine();
			auth = currentUser.checkPassword(pass);
		} while (auth == false);
		System.out.println("Logged in!");
	}
}
