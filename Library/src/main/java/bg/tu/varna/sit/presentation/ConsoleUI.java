package bg.tu.varna.sit.presentation;

import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.service.BookService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {

    private final BookService bookService;
    private final Scanner scanner;

    public ConsoleUI(BookService bookService) {
        this.bookService = bookService;
        this.scanner = new Scanner(System.in);
    }

    // Main method for user interaction
    public void start() {
        String command;
        while (true) {
            System.out.print("Enter command: ");
            command = scanner.nextLine();
            if (command.equals("exit")) break;

            // Handle different commands here
            if (command.equals("books all")) {
                List<Book> books = bookService.getAllBooks();
                books.forEach(System.out::println);
            }
        }
    }
}
