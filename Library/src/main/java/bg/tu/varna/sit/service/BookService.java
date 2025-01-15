package bg.tu.varna.sit.service;

import bg.tu.varna.sit.dao.JAXBParser;
import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.data.BooksWrapper;

import java.util.List;

public class BookService {
    private static final String BOOKS_FILE_PATH = "books.xml";

    private final BooksWrapper booksWrapper;

    public BookService(BooksWrapper booksWrapper) {
        this.booksWrapper = booksWrapper;
    }

    public List<Book> getAllBooks() {
        return booksWrapper.getBooks();
    }

    public void addBook(Book book) {
        booksWrapper.getBooks().add(book);
    }

    public List<Book> loadAllBooks() {
        return JAXBParser.loadObjectFromXML(BOOKS_FILE_PATH, List.class);
    }

    public void saveAllBooks(List<Book> books) {
        JAXBParser.saveObjectToXML(books, BOOKS_FILE_PATH);
    }


}