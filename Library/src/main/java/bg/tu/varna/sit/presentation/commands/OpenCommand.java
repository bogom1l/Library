package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class OpenCommand implements Command {
    private final BookService bookService;

    public OpenCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        bookService.open();
    }
}