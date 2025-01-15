package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.UserService;

public class UsersAddCommand implements Command {
    private UserService userService;
    private String username;
    private String password;

    public UsersAddCommand(UserService userService, String username, String password) {
        this.userService = userService;
        this.username = username;
        this.password = password;
    }

    @Override
    public void execute() {
        System.out.println("Adding user: " + username);
        userService.addUser(username, password);
    }
}
