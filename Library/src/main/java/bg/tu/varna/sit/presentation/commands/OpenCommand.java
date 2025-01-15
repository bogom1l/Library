package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

import java.io.File;

public class OpenCommand implements Command {
    private final BookService bookService;
    private final String fileName;

    public OpenCommand(BookService bookService, String fileName) {
        this.bookService = bookService;
        this.fileName = fileName;
    }

    @Override
    public void execute() {
        System.out.println("Opening application...");

        File file = getFileFromCurrentDirectory(fileName);

        if (!bookService.open(file)) {
            System.out.println("Failed to open library. Make sure the file exists.");
            return;
        }

        System.out.println("Library opened successfully.");
    }

    private File getFileFromCurrentDirectory(String fileName) {
        String currentDirectory = System.getProperty("user.dir");
        return new File(currentDirectory, fileName);
    }

}