package com.jmaaix.testttttt.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.jmaaix.testttttt.database.UserDatabase;
import com.jmaaix.testttttt.entities.FingerprintData;
import com.jmaaix.testttttt.entities.User;


@Dao
public interface FingerprintDataDao  {





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFingerprintData(FingerprintData fingerprintData);

    @Query("SELECT * FROM fingerPrint WHERE userId = :userId")
    FingerprintData getFingerprintDataByUserId(long userId);
}
