package com.socialNetwork.users.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.util.Date;

@Table(name = "users", schema = "users_scheme")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "sex")
    private String sex;
    @Column(name = "country")
    private String country;
    @Column(name = "phone")
    private String phone;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birth_date")
    private Date birthDate;
    @Column(name = "profile_text")
    private String profileText;
    @Column(name = "email")
    private String email;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "img_url")
    private String imgUrl;
    @Column(name = "is_deleted")
    private boolean isDeleted = Boolean.FALSE;

    public User() {}

    public User(int id, String name, String surname, String sex, String country, String phone, Date birthDate,
                String profileText, String email, String passwordHash, String imgUrl, boolean isDeleted) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.country = country;
        this.phone = phone;
        this.birthDate = birthDate;
        this.profileText = profileText;
        this.email = email;
        this.passwordHash = passwordHash;
        this.imgUrl = imgUrl;
        this.isDeleted = isDeleted;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSex() {
        return sex;
    }

    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getProfileText() {
        return profileText;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", sex='" + sex + '\'' +
                ", country='" + country + '\'' +
                ", phone='" + phone + '\'' +
                ", birthDate=" + birthDate +
                ", profileText='" + profileText + '\'' +
                ", email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}