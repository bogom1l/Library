package bg.tu.varna.sit.presentation.commands.books;

import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.presentation.Command;
import bg.tu.varna.sit.service.BookService;

public class BooksAddCommand implements Command {
    private final BookService bookService;
    private final String author;
    private final String title;
    private final String genre;
    private final String description;
    private final int year;
    private final String keywords;
    private final double rating;
    private final String isbn;

    public BooksAddCommand(BookService bookService, String author, String title, String genre, String description, int year, double rating, String keywords, String isbn) {
        this.bookService = bookService;
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.year = year;
        this.keywords = keywords;
        this.rating = rating;
        this.isbn = isbn;
    }

    @Override
    public void execute() {
        Book newBook = new Book(author, title, genre, description, year, keywords, rating, isbn);
        if (bookService.addBook(newBook)) {
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Failed to add the book.");
        }
    }
}
