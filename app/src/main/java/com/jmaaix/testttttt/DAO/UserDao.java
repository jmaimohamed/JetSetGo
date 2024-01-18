package com.jmaaix.testttttt.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jmaaix.testttttt.entities.User;

import java.util.List;

@Dao
public interface UserDao {



    @Insert
    void addUser(User u);
    @Query("SELECT username FROM users")
    List<String> getAllUsernames();
    @Query("SELECT * FROM users WHERE  users.username= :username")
        User getUserByUsername(String username);
    @Query("SELECT * FROM users WHERE Email = :Email AND password = :password")
    User Login(String Email, String password);
    @Query("SELECT * FROM users WHERE  users.Email = :Email")
    User getUserByEmail(String Email);
    @Query("SELECT * FROM users WHERE  users.id = :ID")
    User getUserById(Long ID);
    @Query("SELECT * FROM users WHERE  users.Spes = :Spes")
    User getUserBySpes(String Spes);

    @Query("select * from users")
    List<User> getAll();

    @Query("SELECT profileImage FROM users WHERE id = :userId")
    String getProfileImageById(long userId);

    @Query("UPDATE users SET profileImage = :imagePath WHERE id = :userId")
    void updateProfileImage(long userId, String imagePath);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void updateUser(User user);
    @Query("UPDATE users SET username = :newUsername , Email=:Email , Telephone = :Telephonee , Pays = :Payss  WHERE id = :userId")
    void updateUsername(long userId, String newUsername,String Email,String Telephonee,String Payss);

    @Query("UPDATE users SET password = :Password  WHERE id = :userId")
    void UpdatePassword(long userId, String Password);
    @Delete
    void deleteUser(User user);
    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUserById(long userId);




    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(long userId);


    @Query("UPDATE users SET Spes=:spess  WHERE id = :userId")

    void updateMagic(long userId, String spess);
}
