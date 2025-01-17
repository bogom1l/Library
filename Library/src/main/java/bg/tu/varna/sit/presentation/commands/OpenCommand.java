package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class OpenCommand implements Command {
    private final BookService bookService;
    private final String fileName;

    public OpenCommand(BookService bookService, String fileName) {
        this.bookService = bookService;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        bookService.open(fileName);
        // Todo checks...
    }
}