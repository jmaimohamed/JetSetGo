package com.jmaaix.testttttt.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jmaaix.testttttt.DAO.FingerprintDataDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.entities.FingerprintData;
import com.jmaaix.testttttt.entities.User;

import java.util.List;
import java.util.Map;

@Database(entities = {User.class , FingerprintData.class},version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance ;
    public abstract UserDao userDao();
    public abstract FingerprintDataDao fingerprintDataDao();
    public static UserDatabase getInstance(Context context){
        if(instance== null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "JetSetGos")
                    .allowMainThreadQueries()
                    .build();

        }
        return instance;

    }


}