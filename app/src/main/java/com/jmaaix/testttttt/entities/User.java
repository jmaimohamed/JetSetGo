package com.jmaaix.testttttt.entities;

import android.widget.Button;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jmaaix.testttttt.R;

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
    private String Role;

    @ColumnInfo
    private String Telephone;
    @ColumnInfo
    private String Pays;
    @ColumnInfo
    private String Spes;


    @ColumnInfo(name = "profileImage")
    private String profileImage;

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getSpes() {
        return Spes;
    }

    public void setSpes(String spes) {
        Spes = spes;
    }

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
    public User(String username, String password, String email, String telephone, String pays,String Role) {
        this.username = username;
        this.password = password;
        this.Email = email;
        this.Telephone = telephone;
        this.Pays = pays;
        this.Role= Role;
    }
    @ColumnInfo(name = "fingerprintData")
    private String fingerprintData;

    public String getFingerprintData() {
        return fingerprintData;
    }

    public void setFingerprintData(String fingerprintData) {
        this.fingerprintData = fingerprintData;
    }

    @Ignore
    public User() {
    }
    // Getter and setter for fingerprintDataId
    @ColumnInfo(name = "fingerprint_data_id")
    private long fingerprintDataId; // Store the reference to the fingerprint data

    public long getFingerprintDataId() {
        return fingerprintDataId;
    }

    public void setFingerprintDataId(long fingerprintDataId) {
        this.fingerprintDataId = fingerprintDataId;
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

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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
                ", pays='" + Role + '\'' +
                '}';
    }
}
