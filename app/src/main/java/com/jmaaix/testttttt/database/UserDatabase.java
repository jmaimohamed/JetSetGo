package com.jmaaix.testttttt.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.jmaaix.testttttt.DAO.BudgetDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.entities.Budget;
import com.jmaaix.testttttt.entities.User;

@Database(entities = {User.class, Budget.class}, version = 3, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao userDao();
    public abstract BudgetDao budgetDao();

    public static UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "JETSETGO")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

