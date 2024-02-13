package com.ilya.Misic.models;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="product")
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinColumn(name = "list_users")
    private List<User> list_users;

    public List<User> getList_users() {
        return list_users;
    }

    public void setList_users(List<User> list_users) {
        this.list_users = list_users;
    }

}
