package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class UsersRemoveCommand implements Command {
    private final BookService bookService;
    private final String username;

    public UsersRemoveCommand(BookService bookService, String username) {
        this.bookService = bookService;
        this.username = username;
    }

    @Override
    public void execute() {
        bookService.removeUser(username);
    }
}