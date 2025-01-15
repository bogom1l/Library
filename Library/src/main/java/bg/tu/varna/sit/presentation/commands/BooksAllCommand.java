package bg.tu.varna.sit.presentation.commands;

import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.service.BookService;

import java.util.List;

public class BooksAllCommand implements Command{

    private final BookService bookService;

    public BooksAllCommand(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void execute() {
        List<Book> books = bookService.loadAllBooks();
        books.forEach(System.out::println);
    }
}
