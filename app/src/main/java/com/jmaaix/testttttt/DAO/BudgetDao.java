package com.jmaaix.testttttt.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jmaaix.testttttt.entities.Budget;
import com.jmaaix.testttttt.entities.User;

import org.w3c.dom.Element;

import java.util.List;

@Dao
public interface BudgetDao {
    @Insert
    void insertBudget(Budget budget);

    @Query("SELECT id FROM users WHERE users.Email = :Email")
    long getUserIDByEmail (String Email);

    @Query("SELECT totalBudget FROM budget WHERE userId = :userId")
    double getTotalBudget(long userId);

    @Query("SELECT BFood FROM budget WHERE userId = :userId")
    double getBFood(long userId);

    @Query("SELECT BAccomodation FROM budget WHERE userId = :userId")
    double getBAccomodation(long userId);


    @Query("SELECT BTransport FROM budget WHERE userId = :userId")
    double getBTransport(long userId);

    @Query("SELECT BShopping FROM budget WHERE userId = :userId")
    double getBShopping(long userId);

    @Query("SELECT BLoisir FROM budget WHERE userId = :userId")
    double getBLosir(long userId);


    @Query("UPDATE budget SET totalBudget = :totalbudget  WHERE userId = :userId")
    void updateTotalBudget(long userId, double totalbudget);



    @Query("UPDATE budget SET BFood = BFood + :newBFood WHERE userId = :userId")
    void updateBFood(long userId, double newBFood);

    @Query("UPDATE budget SET BAccomodation = BAccomodation + :newBAccomodation  WHERE userId = :userId")
    void updateBAccomodation(long userId, double newBAccomodation);

    @Query("UPDATE budget SET BShopping =BShopping+ :newBShopping  WHERE userId = :userId")
    void updateBShopping(long userId, double newBShopping);

    @Query("UPDATE budget SET BTransport = BTransport + :newBTransport  WHERE userId = :userId")
    void updateBTransport(long userId, double newBTransport);

    @Query("UPDATE budget SET BLoisir = BLoisir + :newBLoisir  WHERE userId = :userId")
    void updateBLoisir(long userId, double newBLoisir);

}

