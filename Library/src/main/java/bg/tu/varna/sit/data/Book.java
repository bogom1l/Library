package bg.tu.varna.sit.data;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Book {
    private String author;
    private String title;
    private String genre;
    private String description;
    private int year;
    private String keywords;
    private double rating;
    private String isbn;

    public Book() {
    }

    public Book(String author, String title, String genre, String description, int year, String keywords, double rating, String isbn) {
        this.author = author;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.year = year;
        this.keywords = keywords;
        this.rating = rating;
        this.isbn = isbn;
    }

    @XmlElement
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @XmlElement
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @XmlElement
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @XmlElement
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @XmlElement
    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    @XmlElement
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return String.format("ISBN: %s\nTitle: %s\nAuthor: %s\nGenre: %s\nYear: %d\nRating: %.2f\nKeywords: %s\nDescription: %s\n",
                isbn, title, author, genre, year, rating, keywords, description);
    }
}
