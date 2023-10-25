package com.jmaaix.testttttt.DAO;

import androidx.room.Dao;
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

        @Query("SELECT * FROM users WHERE  users.username= :username")
        User getUserByUsername(String username);
    @Query("select * from users")
    List<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void updateUser(User user);
}
