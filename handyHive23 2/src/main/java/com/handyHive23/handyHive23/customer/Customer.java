package com.handyHive23.handyHive23.customer;

import java.util.HashSet;
import java.util.Set;

import com.handyHive23.handyHive23.provider.Provider;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "customer")
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public String getPassword() { return password; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }
    public void setPassword(String password) { this.password = password; }
    public void setProfilePicture(String relativePath) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setProfilePicture'");
    }

    @ManyToMany (fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
@JoinTable(
    name = "saved_providers",
    joinColumns = @JoinColumn(name = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "provider_id")
)
private Set<Provider> savedProviders = new HashSet<>();

public Set<Provider> getSavedProviders() {
    return savedProviders;
}

public void setSavedProviders(Set<Provider> savedProviders) {
    this.savedProviders = savedProviders;
}


}
