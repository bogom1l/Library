package bg.tu.varna.sit.presentation.commands.users;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.UserService;

public class UsersRemoveCommand implements Command {
    private UserService userService;
    private String username;

    public UsersRemoveCommand(UserService userService, String username) {
        this.userService = userService;
        this.username = username;
    }

    @Override
    public void execute() {
        System.out.println("Removing user: " + username);
        userService.removeUser(username);
    }
}
