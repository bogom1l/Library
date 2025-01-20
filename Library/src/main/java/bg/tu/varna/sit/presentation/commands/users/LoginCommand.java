package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

import java.io.Console;
import java.util.Scanner;

public class LoginCommand implements Command {
    private final BookService bookService;
    private final Scanner scanner = new Scanner(System.in);

    public LoginCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        bookService.login(requestUsername(), requestPassword());
    }

    private String requestUsername() {
        System.out.print("Enter Username: ");
        return scanner.nextLine().trim();
    }

    private String requestPassword() {
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword("Enter Password: ");
            return new String(passwordArray);
        } else {
            // Fallback (without masking the password):
            System.out.print("Enter Password: ");
            return scanner.nextLine().trim();
        }
    }
}