package bg.tu.varna.sit.presentation;

import bg.tu.varna.sit.presentation.commands.*;
import bg.tu.varna.sit.presentation.commands.books.*;
import bg.tu.varna.sit.presentation.commands.users.UsersAddCommand;
import bg.tu.varna.sit.presentation.commands.users.UsersRemoveCommand;
import bg.tu.varna.sit.service.BookService;

public class CommandDispatcher {
    private final BookService bookService;

    public CommandDispatcher(BookService bookService) {
        this.bookService = bookService;
    }

    public void dispatch(String commandLine) {
        String[] commandParts = commandLine.split(" ");
        String command = commandParts[0];

        try {
            switch (command.toLowerCase()) {
                case "open":
                    bookService.setFilePath(commandParts[1]); // todo: moje bi da premestq toq red w OpenCommand classa
                    new OpenCommand(bookService).execute();
                    break;
                case "close":
                    new CloseCommand(bookService).execute();
                    break;
                case "save":
                    new SaveCommand(bookService).execute();
                    break;
                case "saveas":
                    new SaveAsCommand(bookService, commandParts[1]).execute();
                    break;
                case "help":
                    new HelpCommand().execute();
                    break;
                case "exit":
                    System.exit(0);
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
            case "add":
                if (commandParts.length == 10) {
                    String author = commandParts[2];
                    String title = commandParts[3];
                    String genre = commandParts[4];
                    String description = commandParts[5];
                    int year = Integer.parseInt(commandParts[6]);
                    double rating = Double.parseDouble(commandParts[7]);
                    String keywords = commandParts[8];
                    String isbn = commandParts[9];
                    new BooksAddCommand(bookService, author, title, genre, description, year, rating, keywords, isbn).execute();
                } else {
                    System.out.println("Usage: books add <author> <title> <genre> <description> <year> <rating> <keywords> <isbn>");
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
                    new UsersAddCommand(bookService, commandParts[2], commandParts[3]).execute();
                } else {
                    System.out.println("Usage: users add <username> <password>");
                }
                break;
            case "remove":
                if (commandParts.length > 2) {
                    new UsersRemoveCommand(bookService, commandParts[2]).execute();
                } else {
                    System.out.println("Usage: users remove <username>");
                }
                break;
            default:
                System.out.println("Unknown users command.");
        }
    }
}
