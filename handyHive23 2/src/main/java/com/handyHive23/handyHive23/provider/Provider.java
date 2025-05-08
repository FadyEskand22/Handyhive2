package com.handyHive23.handyHive23.provider;

import java.util.HashSet;
import java.util.Set;

import com.handyHive23.handyHive23.customer.Customer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "provider")
@NoArgsConstructor
@AllArgsConstructor
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String businessName;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String expertise;
    private String availability;
    private String pricingDetails;
    private String password;
    private String profilePicture; 

    //business hours:
    private String sunStart;
    private String sunEnd;
    private String monStart;
    private String monEnd;
    private String tueStart;
    private String tueEnd;
    private String wedStart;
    private String wedEnd;
    private String thuStart;
    private String thuEnd;
    private String friStart;
    private String friEnd;
    private String satStart;
    private String satEnd;

    


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getPricingDetails() {
        return pricingDetails;
    }

    public void setPricingDetails(String pricingDetails) {
        this.pricingDetails = pricingDetails;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSunStart() {
        return sunStart;
    }

    public void setSunStart(String sunStart) {
        this.sunStart = sunStart;
    }

    public String getSunEnd() {
        return sunEnd;
    }

    public void setSunEnd(String sunEnd) {
        this.sunEnd = sunEnd;
    }

    public String getMonStart() {
        return monStart;
    }

    public void setMonStart(String monStart) {
        this.monStart = monStart;
    }

    public String getMonEnd() {
        return monEnd;
    }

    public void setMonEnd(String monEnd) {
        this.monEnd = monEnd;
    }

    public String getTueStart() {
        return tueStart;
    }

    public String getTueEnd() {
        return tueEnd;
    }

    public String getWedStart() {
        return wedStart;
    }

    public String getWedEnd() {
        return wedEnd;
    }

    public String getThuStart() {
        return thuStart;
    }

    public String getThuEnd() {
        return thuEnd;
    }

    public void setThuEnd(String thuEnd) {
        this.thuEnd = thuEnd;
    }

    public String getFriStart() {
        return friStart;
    }

    public String getFriEnd() {
        return friEnd;
    }

    public String getSatStart() {
        return satStart;
    }

    public String getSatEnd() {
        return satEnd;
    }

    public void setTueStart(String tueStart) {
        this.tueStart = tueStart;
    }
    
    public void setTueEnd(String tueEnd) {
        this.tueEnd = tueEnd;
    }
    
    public void setWedStart(String wedStart) {
        this.wedStart = wedStart;
    }
    
    public void setWedEnd(String wedEnd) {
        this.wedEnd = wedEnd;
    }
    
    public void setThuStart(String thuStart) {
        this.thuStart = thuStart;
    }
    
    public void setFriStart(String friStart) {
        this.friStart = friStart;
    }
    
    public void setFriEnd(String friEnd) {
        this.friEnd = friEnd;
    }
    
    public void setSatStart(String satStart) {
        this.satStart = satStart;
    }
    
    public void setSatEnd(String satEnd) {
        this.satEnd = satEnd;
    }

    @ManyToMany
    @JoinTable(
        name = "saved_providers",
        joinColumns = @JoinColumn(name = "provider_id"),
        inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> savedByUsers;

    public Set<Customer> getSavedByUsers() {
        return savedByUsers;
    }

    public void setSavedByUsers(Set<Customer> savedByUsers) {
        this.savedByUsers = savedByUsers;
    }

    @Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Provider provider = (Provider) o;
    return id != null && id.equals(provider.getId());
}

@Override
public int hashCode() {
    return getClass().hashCode();
}


    
    

}
