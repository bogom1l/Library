package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksRemoveCommand implements Command {
    private final BookService bookService;
    private final String isbn;

    public BooksRemoveCommand(BookService bookService, String isbn) {
        this.bookService = bookService;
        this.isbn = isbn;
    }

    @Override
    public void execute() {
        boolean removed = bookService.removeBook(isbn);
        if (removed) {
            System.out.println("Book with ISBN " + isbn + " removed successfully.");
        } else {
            System.out.println("Failed to remove the book.");
        }
    }
}
