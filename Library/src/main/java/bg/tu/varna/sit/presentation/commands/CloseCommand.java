package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class CloseCommand implements Command {
    private final BookService bookService;

    public CloseCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        System.out.println("Closing application...");
    }
}
