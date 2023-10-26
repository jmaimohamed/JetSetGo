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
    @ColumnInfo
    private String Email;
    @ColumnInfo
    private String Telephone;
    @ColumnInfo
    private String Pays;


    public User(String Email, String password) {
        this.Email = Email;
        this.password = password;
    }

    public User(String username, String password, String email, String telephone, String pays) {
        this.username = username;
        this.password = password;
        this.Email = email;
        this.Telephone = telephone;
        this.Pays = pays;
    }

    @Ignore
    public User() {
    }


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
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getPays() {
        return Pays;
    }

    public void setPays(String pays) {
        Pays = pays;
    }

    @Override
    public String toString() {
        return "User{" +

                ", username='" + username + '\'' +
                ", Email='" + Email + '\'' +
                ", password='" + password + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", pays='" + Pays + '\'' +

                '}';
    }
}
