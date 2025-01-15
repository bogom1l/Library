package bg.tu.varna.sit.service;

import bg.tu.varna.sit.dao.JAXBParser;
import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.data.BooksWrapper;
import jakarta.xml.bind.JAXBException;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookService {
    private final BooksWrapper booksWrapper;
    private File currentFile;

    public BookService() {
        this.booksWrapper = new BooksWrapper();
    }


    public boolean open(File xmlFile) {
        if (xmlFile == null || !xmlFile.exists()) {
            return false;
        }

        try {
            this.booksWrapper = JAXBParser.loadObjectFromXML(xmlFile);
            this.currentFile = xmlFile;
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }



    // Loads books from the XML file
    public List<Book> getAllBooks() {
        BooksWrapper booksWrapper = JAXBParser.loadObjectFromXML(BOOKS_FILE_PATH, BooksWrapper.class);
        return booksWrapper.getBooks();
    }

    // Find a book by ISBN
    public Optional<Book> getBookByIsbn(String isbn) {
        return getAllBooks().stream().filter(book -> book.getIsbn().equals(isbn)).findFirst();
    }

    // Find books based on search criteria (title, author, etc.)
    public List<Book> findBooks(String option, String criteria) {
        switch (option.toLowerCase()) {
            case "title":
                return getAllBooks().stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(criteria.toLowerCase()))
                        .collect(Collectors.toList());
            case "author":
                return getAllBooks().stream()
                        .filter(book -> book.getAuthor().toLowerCase().contains(criteria.toLowerCase()))
                        .collect(Collectors.toList());
            case "tag":
                return getAllBooks().stream()
                        .filter(book -> book.getKeywords().toLowerCase().contains(criteria.toLowerCase()))
                        .collect(Collectors.toList());
            default:
                return List.of();
        }
    }

    // Sort books by title, author, year, or rating
    public List<Book> sortBooks(String option, String order) {
        List<Book> books = getAllBooks();
        switch (option.toLowerCase()) {
            case "title":
                return books.stream()
                        .sorted((b1, b2) -> order.equalsIgnoreCase("asc") ? b1.getTitle().compareTo(b2.getTitle()) : b2.getTitle().compareTo(b1.getTitle()))
                        .collect(Collectors.toList());
            case "author":
                return books.stream()
                        .sorted((b1, b2) -> order.equalsIgnoreCase("asc") ? b1.getAuthor().compareTo(b2.getAuthor()) : b2.getAuthor().compareTo(b1.getAuthor()))
                        .collect(Collectors.toList());
            case "year":
                return books.stream()
                        .sorted((b1, b2) -> order.equalsIgnoreCase("asc") ? Integer.compare(b1.getYear(), b2.getYear()) : Integer.compare(b2.getYear(), b1.getYear()))
                        .collect(Collectors.toList());
            case "rating":
                return books.stream()
                        .sorted((b1, b2) -> order.equalsIgnoreCase("asc") ? Double.compare(b1.getRating(), b2.getRating()) : Double.compare(b2.getRating(), b1.getRating()))
                        .collect(Collectors.toList());
            default:
                return books;
        }
    }

    // Save books to the XML file
    public void saveBooks() {
        BooksWrapper booksWrapper = new BooksWrapper();
        List<Book> books = getAllBooks(); // Get current books
        booksWrapper.setBooks(books); // Set books in the wrapper
        String fileName = "books.xml"; // TODO
        JAXBParser.saveObjectToXML(fileName, booksWrapper); // Save the BooksWrapper object to XML
        System.out.println("Books saved to XML.");
    }


    // Load books (if needed) - already handled in getAllBooks()
    public void loadBooks() {
        List<Book> books = getAllBooks();
        System.out.println("Loaded " + books.size() + " books.");
    }
}