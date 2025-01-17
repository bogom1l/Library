package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class SaveAsCommand implements Command {
    private final BookService bookService;
    private final String newFilePath;

    public SaveAsCommand(BookService bookService, String newFilePath) {
        this.bookService = bookService;
        this.newFilePath = newFilePath;
    }

    @Override
    public void execute() {
        bookService.saveAs(newFilePath);
    }
}