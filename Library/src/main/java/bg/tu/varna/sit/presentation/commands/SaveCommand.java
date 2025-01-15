package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class SaveCommand implements Command {
    private final BookService bookService;

    public SaveCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        bookService.save();
    }
}