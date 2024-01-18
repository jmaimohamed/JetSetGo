package com.jmaaix.testttttt.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.jmaaix.testttttt.DAO.BudgetDao;
import com.jmaaix.testttttt.DAO.FactureDao;
import com.jmaaix.testttttt.DAO.NoteDao;
import com.jmaaix.testttttt.DAO.ReclamationDao;
import com.jmaaix.testttttt.DAO.UserDao;
import com.jmaaix.testttttt.entities.Budget;
import com.jmaaix.testttttt.entities.Converters;
import com.jmaaix.testttttt.entities.Facture;
import com.jmaaix.testttttt.entities.Note;
import com.jmaaix.testttttt.entities.Reclamation;
import com.jmaaix.testttttt.entities.User;


@Database(entities = {User.class, Budget.class, Facture.class, Note.class, Reclamation.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)

public abstract class UserDatabase extends RoomDatabase {

    private static UserDatabase instance;

    public abstract UserDao userDao();
    public abstract BudgetDao budgetDao();
    public abstract FactureDao factureDao();
    public abstract NoteDao noteDao();
    public abstract ReclamationDao reclamationDao();

    public static UserDatabase getInstance(Context context){
        if(instance== null){
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "beltempo")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

