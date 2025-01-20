package bg.tu.varna.sit.presentation;

import bg.tu.varna.sit.presentation.commands.*;
import bg.tu.varna.sit.presentation.commands.books.*;
import bg.tu.varna.sit.presentation.commands.users.LoginCommand;
import bg.tu.varna.sit.presentation.commands.users.LogoutCommand;
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
                    String fileName = commandParts[1];
                    new OpenCommand(bookService, fileName).execute();
                    break;
                case "close":
                    new CloseCommand(bookService).execute();
                    break;
                case "save":
                    new SaveCommand(bookService).execute();
                    break;
                case "saveas":
                    String newFileName = commandParts[1];
                    new SaveAsCommand(bookService, newFileName).execute();
                    break;
                case "help":
                    new HelpCommand().execute();
                    break;
                case "exit":
                    System.exit(0);
                case "login":
                    if (commandParts.length == 3) {
                        String username = commandParts[1];
                        String password = commandParts[2];
                        new LoginCommand(bookService, username, password).execute();
                    } else {
                        System.out.println("Usage: login <username> <password>");
                    }
                    break;
                case "logout":
                    new LogoutCommand(bookService).execute();
                    break;
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
            case "remove":
                if (commandParts.length == 3) {
                    String isbn = commandParts[2];
                    new BooksRemoveCommand(bookService, isbn).execute();
                } else {
                    System.out.println("Usage: books remove <isbn>");
                }
                break;
            case "info":
                if (commandParts.length == 3) {
                    String isbn = commandParts[2];
                    new BooksInfoCommand(bookService, isbn).execute();
                } else {
                    System.out.println("Usage: books info <isbn>");
                }
                break;
            case "find":
                if (commandParts.length == 4) {
                    String option = commandParts[2];
                    String optionString = commandParts[3];
                    new BooksFindCommand(bookService, option, optionString).execute();
                } else {
                    System.out.println("Usage: books find <option> <option_string>" +
                            "\t|\t<option> is one of: 'title', 'author' , 'tag'");
                }
                break;
            case "sort":
                if (commandParts.length == 4) {
                    String option = commandParts[2];
                    String order = commandParts[3];
                    new BooksSortCommand(bookService, option, order).execute();
                } else {
                    System.out.println("Usage: books sort <option> <asc/desc>" +
                            "\t|\t<option> is one of: 'title', 'author' , 'year', 'rating''");
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
                if (commandParts.length == 4) {
                    String username = commandParts[2];
                    String password = commandParts[3];
                    new UsersAddCommand(bookService, username, password).execute();
                } else {
                    System.out.println("Usage: users add <username> <password>");
                }
                break;
            case "remove":
                if (commandParts.length == 3) {
                    String username = commandParts[2];
                    new UsersRemoveCommand(bookService, username).execute();
                } else {
                    System.out.println("Usage: users remove <username>");
                }
                break;
            default:
                System.out.println("Unknown users command.");
        }
    }
}
