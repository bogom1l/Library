package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class UsersAddCommand implements Command {
    private BookService bookService;
    private String username;
    private String password;

    public UsersAddCommand(BookService bookService, String username, String password) {
        this.bookService = bookService;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        System.out.println("Adding user: " + username);
        bookService.addUser(username, password);
    }
}
