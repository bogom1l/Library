package bg.tu.varna.sit;

import bg.tu.varna.sit.dao.JAXBParser;
import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.data.BooksWrapper;
import bg.tu.varna.sit.data.User;
import bg.tu.varna.sit.data.UsersWrapper;

import java.util.List;

public class Main {
    public static void main(String[] args) {


//        Book book = new Book("author1", "title1", "genre1", "description1", 2025, "keywords123", 5.5, "ISBN1234678");
//        System.out.println(book);
//
//        User user = new User("username1", "password1", true);
//        System.out.println(user);

        BooksWrapper booksWrapper = JAXBParser.loadObjectFromXML("books.xml", BooksWrapper.class);
        UsersWrapper usersWrapper = JAXBParser.loadObjectFromXML("users.xml", UsersWrapper.class);

        // Display books and users
        if (booksWrapper != null) {
            booksWrapper.getBooks().forEach(System.out::println);
        }
        if (usersWrapper != null) {
            usersWrapper.getUsers().forEach(System.out::println);
        }

        // Add a new book and user for testing
        Book newBook = new Book("New Author", "New Title", "New Genre", "New Description", 2025, "New Keywords", 4.7, "ISBN99999");
        booksWrapper.getBooks().add(newBook);

        User newUser = new User("newUser", "newPassword", false);
        usersWrapper.getUsers().add(newUser);

        // Save the updated objects back to XML
        JAXBParser.saveObjectToXML(booksWrapper, "books.xml");
        JAXBParser.saveObjectToXML(usersWrapper, "users.xml");


    }
}