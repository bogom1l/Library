package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class LogoutCommand implements Command {
    private final BookService bookService;

    public LogoutCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        bookService.logout();
    }
}