package bg.tu.varna.sit;

import bg.tu.varna.sit.presentation.CommandDispatcher;
import bg.tu.varna.sit.service.BookService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        BookService bookService = new BookService();
        CommandDispatcher commandDispatcher = new CommandDispatcher(bookService);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String input = scanner.nextLine().trim();
            commandDispatcher.dispatch(input);
        }
    }
}