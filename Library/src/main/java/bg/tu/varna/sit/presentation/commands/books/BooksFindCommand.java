package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksFindCommand implements Command {
    private final BookService bookService;
    private final String option;
    private final String optionString;

    public BooksFindCommand(BookService bookService, String option, String optionString) {
        this.bookService = bookService;
        this.option = option;
        this.optionString = optionString;
    }

    @Override
    public void execute() {
        bookService.findBooks(option, optionString);
    }
}