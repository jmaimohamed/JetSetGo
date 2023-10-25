package com.jmaaix.testttttt.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String username;

    @ColumnInfo
    private String password;

    // Room will use this constructor for database operations
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // You can use @Ignore to tell Room not to use this constructor
    @Ignore
    public User() {
    }

    // Getter and setter methods
    // ...

    // You may want to add getter and setter methods for other fields if needed

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
