package bg.tu.varna.sit.presentation.commands.users;


import bg.tu.varna.sit.data.User;
import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class UsersAddCommand implements Command {
    private final BookService bookService;
    private final String username;
    private final String password;

    public UsersAddCommand(BookService bookService, String username, String password) {
        this.bookService = bookService;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        User newUser = new User(username, password, false);

        if (bookService.addUser(newUser)) {
            System.out.println("User added successfully.");
        } else {
            System.out.println("Failed to add the user.");
        }
    }
}