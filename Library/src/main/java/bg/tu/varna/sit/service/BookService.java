package bg.tu.varna.sit.service;

import bg.tu.varna.sit.dao.JAXBParser;
import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.data.BooksWrapper;
import bg.tu.varna.sit.data.User;
import bg.tu.varna.sit.data.UsersWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private static final String usersFilePath = System.getProperty("user.dir") + File.separator + "Library" + File.separator + "users.xml";
    private String booksFilePath;
    private List<Book> books;
    private List<User> users;

    public BookService() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void setFilePath(String booksFileName) {
        String directory = System.getProperty("user.dir");

        this.booksFilePath = directory + File.separator + "Library" + File.separator + booksFileName;

        System.out.println("Books file path set to: " + this.booksFilePath);
        System.out.println("Users file path set to: " + usersFilePath);
    }

    public boolean isFileAccessible() {
        File file = new File(booksFilePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            return true; // File is accessible
        } catch (IOException e) {
            return false; // File is not accessible
        }
    }

    // Open books and users data from XML
    public void open() {
        if (!isFileAccessible()) {
            System.out.println("The books file is currently in use or not accessible.");
            return;
        }

        BooksWrapper booksWrapper = JAXBParser.loadObjectFromXML(booksFilePath, BooksWrapper.class);
        if (booksWrapper != null) {
            books = booksWrapper.getBooks();
            System.out.println("Books loaded: " + books.size());
        } else {
            System.out.println("No books found in the XML file.");
        }

        UsersWrapper usersWrapper = JAXBParser.loadObjectFromXML(usersFilePath, UsersWrapper.class);
        if (usersWrapper != null) {
            users = usersWrapper.getUsers();
            System.out.println("Users loaded: " + users.size());
        } else {
            System.out.println("No users found in the XML file.");
        }
    }

    // Close the application, optionally saving the data
    public void close() {
        System.out.println("File closed.");
    }

    // Save books to the XML file
    public void save() {
        saveBooks();
        saveUsers();
    }

    private void saveBooks() {
        BooksWrapper booksWrapper = new BooksWrapper();
        booksWrapper.setBooks(books);
        JAXBParser.saveObjectToXML(booksFilePath, booksWrapper);
        System.out.println("Books saved to XML.");
    }

    private void saveUsers() {
        UsersWrapper usersWrapper = new UsersWrapper();
        usersWrapper.setUsers(users);
        JAXBParser.saveObjectToXML(usersFilePath, usersWrapper);
        System.out.println("Users saved to XML.");
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void displayBookInfo(String isbn) {
        Book book = findBookByIsbn(isbn);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book with ISBN " + isbn + " not found.");
        }
    }

    // Find books by title, author, or tag
    public void findBooks(String option, String optionString) {
        List<Book> foundBooks = new ArrayList<>();
        switch (option.toLowerCase()) {
            case "title":
                foundBooks = books.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(optionString.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "author":
                foundBooks = books.stream()
                        .filter(book -> book.getAuthor().toLowerCase().contains(optionString.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            case "tag":
                foundBooks = books.stream()
                        .filter(book -> book.getKeywords().toLowerCase().contains(optionString.toLowerCase()))
                        .collect(Collectors.toList());
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }
        foundBooks.forEach(System.out::println);
    }

    // Sort books by title, author, year, or rating
    public void sortBooks(String option, String order) {
        Comparator<Book> comparator = null;

        switch (option.toLowerCase()) {
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
                break;
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "year":
                comparator = Comparator.comparingInt(Book::getYear);
                break;
            case "rating":
                comparator = Comparator.comparingDouble(Book::getRating);
                break;
            default:
                System.out.println("Invalid option for sorting.");
                return;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        books.sort(comparator);
        books.forEach(System.out::println);
    }

    // Add a new user
    public void addUser(String username, String password) {
        if (findUserByUsername(username) != null) {
            System.out.println("User already exists.");
            return;
        }

        User newUser = new User(username, password, true);
        users.add(newUser);
        saveUsers();  // Save the updated users list
        System.out.println("User added.");
    }

    // Remove a user
    public void removeUser(String username) {
        User userToRemove = findUserByUsername(username);
        if (userToRemove == null) {
            System.out.println("User not found.");
            return;
        }
        users.remove(userToRemove);
        saveUsers();  // Save the updated users list
        System.out.println("User removed.");
    }

    private Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    private User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
