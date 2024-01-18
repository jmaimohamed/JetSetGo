package com.jmaaix.testttttt.DAO;


import android.media.Image;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.jmaaix.testttttt.entities.Budget;
import com.jmaaix.testttttt.entities.Facture;

import java.util.List;

@Dao
public interface FactureDao {

    @Insert
    void insertFacture(Facture facture);

    @Query("SELECT id FROM users WHERE users.Email = :Email")
    long getUserIDByEmail (String Email);



    @Query("SELECT * FROM facture WHERE userId = :userId")
    List<Facture> getAllFacturesById(long userId);

    @Delete
    void deleteFacture(Facture facture);


}
