package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksAllCommand implements Command {
    private BookService bookService;

    public BooksAllCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        bookService.showAllBooks();
    }
}