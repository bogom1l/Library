package bg.tu.varna.sit;

import bg.tu.varna.sit.presentation.CommandDispatcher;
import bg.tu.varna.sit.service.BookService;
import bg.tu.varna.sit.service.UserService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BookService bookService = new BookService();
        UserService userService = new UserService();
        CommandDispatcher commandDispatcher = new CommandDispatcher(bookService, userService);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();

            // Dispatch the input to the CommandDispatcher
            commandDispatcher.dispatch(input);
        }
    }
}