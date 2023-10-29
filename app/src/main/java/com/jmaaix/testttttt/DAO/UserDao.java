package com.jmaaix.testttttt.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jmaaix.testttttt.entities.FingerprintData;
import com.jmaaix.testttttt.entities.User;

import java.util.List;

@Dao
public interface UserDao {



    @Insert
    void addUser(User u);

    @Query("SELECT * FROM users WHERE  users.username= :username")
        User getUserByUsername(String username);

    @Query("SELECT * FROM users WHERE  users.Email = :Email")
    User getUserByEmail(String Email);
    @Query("SELECT * FROM users WHERE  users.id = :ID")
    User getUserById(Long ID);
    @Query("SELECT * FROM users WHERE  users.Spes = :Spes")
    User getUserBySpes(String Spes);

    @Query("select * from users")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void updateUser(User user);
    @Query("UPDATE users SET username = :newUsername , Email=:Email , Telephone = :Telephonee , Pays = :Payss  WHERE id = :userId")
    void updateUsername(long userId, String newUsername,String Email,String Telephonee,String Payss);
    @Delete
    void deleteUser(User user);
    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUserById(long userId);




    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(long userId);

    @Query("SELECT * FROM fingerprint WHERE userId  = :userId") // Correct the table name
    FingerprintData getFingerprintDataByUserId(long userId);

    @Query("UPDATE users SET Spes=:spess  WHERE id = :userId")

    void updateMagic(long userId, String spess);
}
