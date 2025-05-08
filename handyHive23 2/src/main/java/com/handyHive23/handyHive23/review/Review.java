package com.handyHive23.handyHive23.review;
 
 import com.handyHive23.handyHive23.customer.Customer;
 import com.handyHive23.handyHive23.service.ServicePost;
 import jakarta.persistence.*;
 import lombok.*;
 
 import java.sql.Date;
 
 import org.hibernate.annotations.OnDelete;
 import org.hibernate.annotations.OnDeleteAction;
 
 @Entity
 @Data
 @NoArgsConstructor
 @AllArgsConstructor
 public class Review {

    
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;
 
     private int rating;
     private String comment;
     private Date reviewDate;
 
     @ManyToOne
     @JoinColumn(name = "customer_id", nullable = false)
     @OnDelete(action = OnDeleteAction.CASCADE)
     private Customer customer;
 
     @ManyToOne
     @JoinColumn(name = "service_id", nullable = false)
     private ServicePost service;

     // ✅ Getters
    public Long getId() {
        return id;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ServicePost getService() {
        return service;
    }
 
     public void setCustomer(Customer customer) {
         this.customer = customer;
     }
 
     public void setService(ServicePost service) {
         this.service = service;
     }
 
     public void setReviewDate(Date reviewDate) {
         this.reviewDate = reviewDate;
     }

     public void setRating(int rating) {
        this.rating = rating;
    }    

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Transient
    public String getCustomerName() {
        return customer != null ? customer.getName() : "Anonymous";
    }

 }