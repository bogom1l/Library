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

public class BookService {
    private static final String directory = System.getProperty("user.dir");
    private static final String usersFilePath = System.getProperty("user.dir") + File.separator + "Library" + File.separator + "users.xml";
    private String booksFilePath;
    private List<Book> books;
    private List<User> users;
    private boolean isBooksFileOpened = false;
    private User loggedInUser;

    public BookService() {
        books = new ArrayList<>();
        users = new ArrayList<>();
        openUsersFile();
    }

    private void openUsersFile() {
        UsersWrapper usersWrapper = JAXBParser.loadObjectFromXML(usersFilePath, UsersWrapper.class);
        if (usersWrapper != null) {
            users = usersWrapper.getUsers();
            System.out.println("Users loaded: " + users.size());
        } else {
            System.out.println("No users found in the XML file.");
        }
    }

    public boolean isBooksFileOpened() {
        return this.isBooksFileOpened;
    }

    public boolean isFileAccessible() {
        File file = new File(booksFilePath);
        try (FileInputStream fis = new FileInputStream(file)) {
            return true; // File is accessible
        } catch (IOException e) {
            return false; // File is not accessible
        }
    }

    private boolean doesFileExist(String filePath) {
        File file = new File(filePath);
        return file.exists() && file.isFile();
    }

    private boolean isValidFileName(String fileName) {
        return fileName.endsWith(".xml");
    }

    public void open(String booksFileName) {
        booksFilePath = directory + File.separator + "Library" + File.separator + booksFileName;

        if (isBooksFileOpened) {
            System.out.println("The books file is already opened.");
            return;
        }

        if (!isValidFileName(booksFileName)) {
            System.out.println("Invalid file format. Please provide a valid .xml file.");
            return;
        }

        if (!doesFileExist(booksFilePath)) {
            System.out.println("The file doesn't exist.");
            return;
        }

        BooksWrapper booksWrapper = JAXBParser.loadObjectFromXML(booksFilePath, BooksWrapper.class);
        if (booksWrapper != null) {
            books = booksWrapper.getBooks();
            System.out.println("Books loaded: " + books.size());
        } else {
            System.out.println("No books found in the XML file.");
        }

        isBooksFileOpened = true;
    }

    public void close() {
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        books = new ArrayList<>();
        isBooksFileOpened = false;
        System.out.println("File closed.");
    }

    public void save() {
        saveBooks();
    }

    private void saveBooks() {
        BooksWrapper booksWrapper = new BooksWrapper();
        booksWrapper.setBooks(books);
        JAXBParser.saveObjectToXML(booksFilePath, booksWrapper);
        System.out.println("Books saved to XML.");
    }

    public void saveAs(String newFilePath) {
        BooksWrapper booksWrapper = new BooksWrapper();
        booksWrapper.setBooks(books);
        String fullNewFilePath = directory + File.separator + "Library" + File.separator + newFilePath;
        JAXBParser.saveObjectToXML(fullNewFilePath, booksWrapper);
        System.out.println("Books saved to new path: " + fullNewFilePath);
    }

    public void displayAllBooks() {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void displayBookInfo(String isbn) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        Book book = findBookByIsbn(isbn);
        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("Book with ISBN " + isbn + " not found.");
        }
    }

    public boolean addBook(Book newBook) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return false;
        }

        if(!isUserAdmin()){
            System.out.println("Access denied! You must be an admin perform this action.");
            return false;
        }

        if (findBookByIsbn(newBook.getIsbn()) != null) {
            return false;
        }

        books.add(newBook);
        return true;
    }

    public boolean removeBook(String isbn) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return false;
        }
        
        if(!isUserAdmin()){
            System.out.println("Access denied! You must be an admin perform this action.");
            return false;
        }

        Book bookToRemove = findBookByIsbn(isbn);

        if (bookToRemove != null) {
            books.remove(bookToRemove);
            return true;
        }
        return false;
    }

    private Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public void findBooks(String option, String optionString) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        List<Book> foundBooks = new ArrayList<>();
        switch (option.toLowerCase()) {
            case "title":
                foundBooks = books.stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(optionString.toLowerCase()))
                        .toList();
                break;
            case "author":
                foundBooks = books.stream()
                        .filter(book -> book.getAuthor().toLowerCase().contains(optionString.toLowerCase()))
                        .toList();
                break;
            case "tag":
                foundBooks = books.stream()
                        .filter(book -> book.getKeywords().toLowerCase().contains(optionString.toLowerCase()))
                        .toList();
                break;
            default:
                System.out.println("Invalid option.");
                return;
        }
        foundBooks.forEach(System.out::println);
    }

    public void sortBooks(String option, String order) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

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

    // -------------------- users --------------------------------

    private void saveUsers() {
        UsersWrapper usersWrapper = new UsersWrapper();
        usersWrapper.setUsers(users);
        JAXBParser.saveObjectToXML(usersFilePath, usersWrapper);
        System.out.println("Users saved to XML.");
    }

    public void addUser(String username, String password) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if(!isUserAdmin()){
            System.out.println("Access denied! You must be an admin perform this action.");
            return;
        }

        if (findUserByUsername(username) != null) {
            System.out.println("User already exists.");
            return;
        }

        User newUser = new User(username, password, false);
        users.add(newUser);
        saveUsers();
        System.out.println("User added.");
    }

    public void removeUser(String username) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        if(!isUserAdmin()){
            System.out.println("Access denied! You must be an admin perform this action.");
            return;
        }

        User userToRemove = findUserByUsername(username);
        if (userToRemove == null) {
            System.out.println("User not found.");
            return;
        }
        users.remove(userToRemove);
        saveUsers();
        System.out.println("User removed.");
    }

    private User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // -------- login/logout ------------

    public void login(String username, String password) {
        if (isUserLoggedIn()) {
            System.out.println("You are already logged in with username: " + loggedInUser.getUsername());
            return;
        }

        User user = users.stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst()
                .orElse(null);

        if (user == null) {
            System.out.println("Invalid username or password.");
        } else {
            loggedInUser = user;
            System.out.println("Login successful. Welcome, " + username + "!");
        }
    }

    public void logout() {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
        } else {
            System.out.println("Goodbye, " + loggedInUser.getUsername() + "!");
            loggedInUser = null;
        }
    }

    public boolean isUserLoggedIn() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public boolean isUserAdmin() {
        return loggedInUser.isAdmin();
    }

    // ----------------------

}
