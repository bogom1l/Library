package bg.tu.varna.sit.service;

import bg.tu.varna.sit.dao.JAXBParser;
import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.data.BooksWrapper;
import bg.tu.varna.sit.data.User;
import bg.tu.varna.sit.data.UsersWrapper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BookService {
    private static final String directory = System.getProperty("user.dir");
    private static final String usersFilePath = System.getProperty("user.dir") + File.separator + "Library" + File.separator + "users.xml";

    private List<Book> books;
    private List<User> users;

    private String booksFilePath;
    private User loggedInUser;
    private boolean isBooksFileOpened = false;

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

    //todo: moga v saveAs da polzvam private save() methoda ama trqbva da go refactorna da priema String filePath
    public void save() {
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        saveBooks();
    }

    private void saveBooks() {
        BooksWrapper booksWrapper = new BooksWrapper();
        booksWrapper.setBooks(books);
        JAXBParser.saveObjectToXML(booksFilePath, booksWrapper);
        System.out.println("Books saved to XML.");
    }

    public void saveAs(String newFilePath) {
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        BooksWrapper booksWrapper = new BooksWrapper();
        booksWrapper.setBooks(books);
        String fullNewFilePath = directory + File.separator + "Library" + File.separator + newFilePath;
        JAXBParser.saveObjectToXML(fullNewFilePath, booksWrapper);
        System.out.println("Books saved to new path: " + fullNewFilePath);
    }

    public void displayAllBooks() {
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

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
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

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
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return false;
        }

        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return false;
        }

        if (!isUserAdmin()) {
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
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return false;
        }

        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return false;
        }

        if (!isUserAdmin()) {
            System.out.println("Access denied! You must be an admin perform this action.");
            return false;
        }

        Book bookToRemove = findBookByIsbn(isbn);
        if (bookToRemove == null) {
            System.out.println("Book not found with the provided ISBN.");
            return false;
        }

        books.remove(bookToRemove);
        return true;
    }

    private Book findBookByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public void findBooks(String option, String optionString) {
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

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
        if (!isBooksFileOpened) {
            System.out.println("No file is currently opened.");
            return;
        }

        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return;
        }

        switch (option.toLowerCase()) {
            case "title":
                mergeSort(books, "title");
                break;
            case "author":
                mergeSort(books, "author");
                break;
            case "year":
                mergeSort(books, "year");
                break;
            case "rating":
                mergeSort(books, "rating");
                break;
            default:
                System.out.println("Invalid sorting option.");
                return;
        }

        // Sort in asc/desc order
        if ("desc".equalsIgnoreCase(order)) {
            Collections.reverse(books);
        }

        // Print sorted books
        books.forEach(System.out::println);
    }

    private void mergeSort(List<Book> list, String option) {
        if (list.size() <= 1) {
            return;
        }

        // Split the list into two halves
        int mid = list.size() / 2;
        List<Book> left = new ArrayList<>(list.subList(0, mid));
        List<Book> right = new ArrayList<>(list.subList(mid, list.size()));

        // Recursively sort both halves
        mergeSort(left, option);
        mergeSort(right, option);

        // Merge the sorted halves
        merge(list, left, right, option);
    }

    private void merge(List<Book> list, List<Book> left, List<Book> right, String option) {
        int i = 0, j = 0, k = 0;

        // Merge the two lists based on the selected option
        while (i < left.size() && j < right.size()) {
            boolean conditionMet;
            switch (option.toLowerCase()) {
                case "title":
                    conditionMet = left.get(i).getTitle().compareTo(right.get(j).getTitle()) < 0;
                    break;
                case "author":
                    conditionMet = left.get(i).getAuthor().compareTo(right.get(j).getAuthor()) < 0;
                    break;
                case "year":
                    conditionMet = left.get(i).getYear() < right.get(j).getYear();
                    break;
                case "rating":
                    conditionMet = left.get(i).getRating() < right.get(j).getRating();
                    break;
                default:
                    System.out.println("Invalid option.");
                    return;
            }

            if (conditionMet) {
                list.set(k++, left.get(i++));
            } else {
                list.set(k++, right.get(j++));
            }
        }

        // Add the remaining elements of the left or right list
        while (i < left.size()) {
            list.set(k++, left.get(i++));
        }

        while (j < right.size()) {
            list.set(k++, right.get(j++));
        }
    }

    private void saveUsers() {
        UsersWrapper usersWrapper = new UsersWrapper();
        usersWrapper.setUsers(users);
        JAXBParser.saveObjectToXML(usersFilePath, usersWrapper);
        System.out.println("Users saved to XML.");
    }

    public boolean addUser(User newUser) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return false;
        }

        if (!isUserAdmin()) {
            System.out.println("Access denied! You must be an admin perform this action.");
            return false;
        }

        if (findUserByUsername(newUser.getUsername()) != null) {
            System.out.println("User already exists.");
            return false;
        }

        users.add(newUser);
        saveUsers();
        return true;
    }

    public boolean removeUser(String username) {
        if (!isUserLoggedIn()) {
            System.out.println("No user is currently logged in.");
            return false;
        }

        if (!isUserAdmin()) {
            System.out.println("Access denied! You must be an admin perform this action.");
            return false;
        }

        User userToRemove = findUserByUsername(username);
        if (userToRemove == null) {
            System.out.println("User not found.");
            return false;
        }

        users.remove(userToRemove);
        saveUsers();
        return true;
    }

    private User findUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

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

    public boolean isUserAdmin() {
        return loggedInUser.isAdmin();
    }

}