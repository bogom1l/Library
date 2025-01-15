package bg.tu.varna.sit.presentation;

import bg.tu.varna.sit.presentation.commands.CloseCommand;
import bg.tu.varna.sit.presentation.commands.OpenCommand;
import bg.tu.varna.sit.presentation.commands.SaveCommand;
import bg.tu.varna.sit.presentation.commands.books.BooksAllCommand;
import bg.tu.varna.sit.presentation.commands.books.BooksFindCommand;
import bg.tu.varna.sit.presentation.commands.books.BooksInfoCommand;
import bg.tu.varna.sit.presentation.commands.books.BooksSortCommand;
import bg.tu.varna.sit.presentation.commands.users.UsersAddCommand;
import bg.tu.varna.sit.presentation.commands.users.UsersRemoveCommand;
import bg.tu.varna.sit.service.BookService;
import bg.tu.varna.sit.service.UserService;

public class CommandDispatcher {
    private final BookService bookService;
    private final UserService userService;

    public CommandDispatcher(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }

    public void dispatch(String commandLine) {
        String[] commandParts = commandLine.split(" ");
        String command = commandParts[0];

        try {
            switch (command.toLowerCase()) {
                case "open":
                    String fileName = commandParts[1];
                    new OpenCommand(bookService, fileName).execute();
                    break;
                case "close":
                    new CloseCommand(bookService).execute();
                    break;
                case "save":
                    new SaveCommand(bookService).execute();
                    break;
                case "exit":
                    // TODO new ExitCommand().execute();
                    break;
//TODO                case "login":
//                    if (commandParts.length > 1) {
//                        new LoginCommand(userService, commandParts[1]).execute();
//                    } else {
//                        System.out.println("Usage: login <username>");
//                    }
//                    break;
//                case "logout":
//                    new LogoutCommand(userService).execute();
//                    break;
                case "books":
                    handleBooksCommand(commandParts);
                    break;
                case "users":
                    handleUsersCommand(commandParts);
                    break;
                default:
                    System.out.println("Unknown command. Type 'help' for a list of commands.");
            }
        } catch (Exception e) {
            System.out.println("Error executing command: " + e.getMessage());
        }
    }

    private void handleBooksCommand(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Usage: books <all|info|find|sort>");
            return;
        }

        String subCommand = commandParts[1];

        switch (subCommand) {
            case "all":
                new BooksAllCommand(bookService).execute();
                break;
            case "info":
                if (commandParts.length > 2) {
                    new BooksInfoCommand(bookService, commandParts[2]).execute();
                } else {
                    System.out.println("Usage: books info <isbn>");
                }
                break;
            case "find":
                if (commandParts.length > 3) {
                    new BooksFindCommand(bookService, commandParts[2], commandParts[3]).execute();
                } else {
                    System.out.println("Usage: books find <option> <option_string>");
                }
                break;
            case "sort":
                if (commandParts.length > 3) {
                    new BooksSortCommand(bookService, commandParts[2], commandParts[3]).execute();
                } else {
                    System.out.println("Usage: books sort <option> <asc/desc>");
                }
                break;
            default:
                System.out.println("Unknown books command.");
        }
    }

    private void handleUsersCommand(String[] commandParts) {
        if (commandParts.length < 2) {
            System.out.println("Usage: users <add|remove>");
            return;
        }

        String subCommand = commandParts[1];

        switch (subCommand) {
            case "add":
                if (commandParts.length > 3) {
                    new UsersAddCommand(userService, commandParts[2], commandParts[3]).execute();
                } else {
                    System.out.println("Usage: users add <username> <password>");
                }
                break;
            case "remove":
                if (commandParts.length > 2) {
                    new UsersRemoveCommand(userService, commandParts[2]).execute();
                } else {
                    System.out.println("Usage: users remove <username>");
                }
                break;
            default:
                System.out.println("Unknown users command.");
        }
    }
}
