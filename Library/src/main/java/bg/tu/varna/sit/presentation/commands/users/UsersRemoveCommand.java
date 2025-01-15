package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class UsersRemoveCommand implements Command {
    private BookService bookService;
    private String username;

    public UsersRemoveCommand(BookService bookService, String username) {
        this.bookService = bookService;
        this.username = username;
    }

    @Override
    public void execute() {
        System.out.println("Removing user: " + username);
        bookService.removeUser(username);
    }
}
