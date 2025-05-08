package com.handyHive23.handyHive23.service;

import com.handyHive23.handyHive23.provider.Provider;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "service")
public class ServicePost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double price;
    private String availability;

    @Column(name = "image_path")
    private String imagePath;

    // === Likes: usernames who liked the post ===
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "service_likes", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "username")
    private Set<String> likedByUsers = new HashSet<>();

    // === Comments: username + text + date ===
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "service_comments", joinColumns = @JoinColumn(name = "service_id"))
    private List<CommentEntry> recentComments = new ArrayList<>();

    // === Relationship with Provider ===
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "provider_id", nullable = false)
    private Provider provider;

    // ===== Constructors =====
    public ServicePost() {}

    public ServicePost(Long id, String title, String description, Double price, String availability, Provider provider) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.provider = provider;
    }

    // ===== Getters & Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) { this.description = description; }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) { this.price = price; }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) { this.availability = availability; }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) { this.provider = provider; }

    public Set<String> getLikedByUsers() {
        return likedByUsers;
    }

    public void setLikedByUsers(Set<String> likedByUsers) {
        this.likedByUsers = likedByUsers;
    }

    public List<CommentEntry> getRecentComments() {
        return recentComments;
    }

    public void setRecentComments(List<CommentEntry> recentComments) {
        this.recentComments = recentComments;
    }

    // Utility methods
    public int getLikes() {
        return likedByUsers.size();
    }

    public int getComments() {
        return recentComments.size();
    }

    public Object getCustomer() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCustomer'");
    }
}
