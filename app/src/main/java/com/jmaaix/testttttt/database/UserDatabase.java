package com.jmaaix.testttttt.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.entities.User;

import java.util.List;
import java.util.Map;

@Database(entities = {User.class},version = 2,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance ;
    public abstract UserDao userDao();
    public static UserDatabase getInstance(Context context){
        if(instance== null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "gestion_esprit")
                    .allowMainThreadQueries()
                    .build();

        }
        return instance;

    }


}