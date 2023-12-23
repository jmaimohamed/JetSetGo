package com.jmaaix.testttttt.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jmaaix.testttttt.DAO.NoteDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.entities.Note;
import com.jmaaix.testttttt.entities.User;

@Database(entities = {User.class, Note.class},version = 1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    private static UserDatabase instance ;
    public abstract UserDao userDao();
    public abstract NoteDao noteDao();

    public static UserDatabase getInstance(Context context){
        if(instance== null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "JetSet")
                    .allowMainThreadQueries()
                    .build();

        }
        return instance;

    }


}