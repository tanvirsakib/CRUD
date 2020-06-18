package com.example.crud;

public class Books {

    private String bookId;
    private String bookName;
    private float bookRating;

    public Books(){

    }

    public Books(String bookId, String bookName, float bookRating) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookRating = bookRating;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getBookRating() {
        return bookRating;
    }

    public void setBookRating(float bookRating) {
        this.bookRating = bookRating;
    }
}
