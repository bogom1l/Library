package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksSortCommand implements Command {
    private final BookService bookService;
    private final String option;
    private final String order;

    public BooksSortCommand(BookService bookService, String option, String order) {
        this.bookService = bookService;
        this.option = option;
        this.order = order;
    }

    @Override
    public void execute() {
        bookService.sortBooks(option, order);
    }
}