import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserLoginApplication {

	public static void main(String[] args) throws IOException {
		BufferedReader fileReader = null;
		Scanner scanner = new Scanner(System.in);
		String[] usersParsed = null;
		UserService userService = new UserService();
		User[] users = new User[4];
		Boolean loggedIn = false;

		// Read in the text file
		try {
			fileReader = new BufferedReader(new FileReader("data.txt"));

			String line;
			{
				try {
					// Separate words with ","s and create a new user for each line with each
					// word being inputed as the variables for email, password and name respectively
					for (int i = 0; i < 4; i++) {
						line = fileReader.readLine();
						usersParsed = line.split(",");
						users[i] = userService.createUser(usersParsed[0], usersParsed[1], usersParsed[2]);

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 5 attempts at logging in
		try {
			for (int j = 0; j < 5; j++) {
				if (loggedIn == false) {
					System.out.println("Enter your email:");
					String email = scanner.nextLine();
					System.out.println("Enter your password:");
					String password = scanner.nextLine();

					// Iterate through all the users in the data.txt file and check if email(not case sensitive)
					// and password(case sensitive) match the users input for these fields
					for (int i = 0; i < 4; i++) {
						if (email.equalsIgnoreCase(users[i].getUsername()) && password.equals(users[i].getPassword())) {
							System.out.println("Welcome: " + users[i].getName());
							loggedIn = true;
						}

					}
					if (loggedIn == false && j != 4) {
						System.out.println("Invalid login, please try again!");
					}
				}
			}
		} finally {
			scanner.close();
			fileReader.close();
		}
		if (loggedIn == false) {
			System.out.println("Too many failed login attempts, you are now locked out.");
		}
	}
}
