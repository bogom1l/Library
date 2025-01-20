package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class LoginCommand implements Command {
    private final BookService bookService;
    private final String username;
    private final String password;

    public LoginCommand(BookService bookService, String username, String password) {
        this.bookService = bookService;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        bookService.login(username, password);
    }
}