package bg.tu.varna.sit;

import bg.tu.varna.sit.data.Book;
import bg.tu.varna.sit.data.User;

public class Main {
    public static void main(String[] args) {


        Book book = new Book("author1", "title1", "genre1", "description1", 2025, "keywords123", 5.5, "ISBN1234678");
        System.out.println(book);

        User user = new User("username1", "password1", true);
        System.out.println(user);

    }
}