package com.handyHive23.handyHive23.review;

public class ReviewDTO {
    private int rating;
    private String comment;
    private String customerName;

    public ReviewDTO(String customerName2, int rating2, String comment2) {
        //TODO Auto-generated constructor stub
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
