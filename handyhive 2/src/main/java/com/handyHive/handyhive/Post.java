package com.handyHive.handyhive;

import jakarta.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ✅ Ensure auto-increment
    private Long id;

    private String caption;
    private String imagePath;

    // Constructors
    public Post() {}

    public Post(String caption, String imagePath) {
        this.caption = caption;
        this.imagePath = imagePath;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}
