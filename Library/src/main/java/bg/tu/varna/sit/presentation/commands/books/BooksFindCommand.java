package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksFindCommand implements Command {
    private BookService bookService;
    private String option;
    private String optionString;

    public BooksFindCommand(BookService bookService, String option, String optionString) {
        this.bookService = bookService;
        this.option = option;
        this.optionString = optionString;
    }

    @Override
    public void execute() {
        System.out.println("Finding books by " + option + ": " + optionString);
        bookService.findBooks(option, optionString).forEach(System.out::println);
    }
}
