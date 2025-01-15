package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksInfoCommand implements Command {
    private BookService bookService;
    private String isbn;

    public BooksInfoCommand(BookService bookService, String isbn) {
        this.bookService = bookService;
        this.isbn = isbn;
    }

    @Override
    public void execute() {
        System.out.println("Displaying information for ISBN: " + isbn);
        bookService.getBookByIsbn(isbn).ifPresent(System.out::println);
    }
}
