package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksSortCommand implements Command {
    private BookService bookService;
    private String option;
    private String order;

    public BooksSortCommand(BookService bookService, String option, String order) {
        this.bookService = bookService;
        this.option = option;
        this.order = order;
    }

    @Override
    public void execute() {
        System.out.println("Sorting books by " + option + " in " + order + " order.");
        bookService.sortBooks(option, order).forEach(System.out::println);
    }
}