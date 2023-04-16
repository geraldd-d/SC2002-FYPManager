package boundaries;

import java.util.Scanner;

import controllers.LoginController;
import entities.Coordinator;
import entities.Faculty;
import entities.Student;
import entities.User;

public class PasswordMenu {
	private static PasswordMenu pm = null;
	private PasswordMenu() {};
	
	/** 
	 * @return PasswordMenu
	 */
	public static PasswordMenu getInstance() {
		if (pm == null) {
			pm = new PasswordMenu();
		}
		return pm;
	}
	
	/** 
	 * @param user
	 */
	public void display(User user) {
		LoginMenu lm = LoginMenu.getInstance();
		LoginController lc = LoginController.getInstance();
		Scanner sc = new Scanner(System.in);
		String input, oldPW;
		boolean authenticated = false, matched = false;
		do {
			System.out.println("Enter current password or leave blank to return: ");
			oldPW = sc.nextLine();
			if(oldPW.equals("")) {
				if (user instanceof Student) {
					user = (Student) user;
					StudentMenu sm = StudentMenu.getInstance();
		            sm.display((Student)user);
		        }
				else if (user instanceof Coordinator) {
					user = (Coordinator) user;
		            CoordinatorMenu cm = CoordinatorMenu.getInstance();
		            cm.display((Coordinator)user);
		        }
				else if (user instanceof Faculty) {
					user = (Faculty) user;
		            FacultyMenu fm = FacultyMenu.getInstance();
		            fm.display((Faculty)user);
		        }
				return;
			}
			if (lc.isLoggedIn(user, oldPW)) {
				authenticated = true;
			} else {
				System.out.println("Invalid Password.");
			}
		} while (!authenticated);
		do {
			sc.nextLine();
			System.out.println("Enter new password: ");
			String newPW = sc.nextLine();
			System.out.println("Confirm new password again: ");
			input = sc.nextLine();
			if (newPW.length() < 8 ) {
				System.out.println("Password is not long enough. 8 characters minimum.");
				continue;
			}
			if (user.checkPassword(newPW)) {
				System.out.println("You may not use the same password as your old one.");
				continue;
			}
			
			if (input.equals(newPW)) {
	        	lc.updatePassword(user,oldPW,newPW);
	        	matched = true;
	        	lm.display();
	        	return;
	        } else {
	        	System.out.println("Passwords did not match.");
	        }
		} while (!matched);
        
		
		return;
	}
}
