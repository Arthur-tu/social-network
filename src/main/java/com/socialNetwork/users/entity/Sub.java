package com.socialNetwork.users.entity;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "subs", schema = "users_scheme")
@Entity
public class Sub {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "target_id")
    private int targetId;
    @Column(name = "date")
    private Date date;

    public Sub(){}

    public Sub(int id, int userId, int targetId, Date date) {
        this.id = id;
        this.userId = userId;
        this.targetId = targetId;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getTargetId() {
        return targetId;
    }

    public Date getDate() {
        return date;
    }
}
