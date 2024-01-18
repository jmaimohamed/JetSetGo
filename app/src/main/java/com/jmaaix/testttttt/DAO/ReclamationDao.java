package com.jmaaix.testttttt.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.jmaaix.testttttt.entities.Reclamation;

import java.util.List;

@Dao
public interface ReclamationDao {

    @Insert
    void addReclamation(Reclamation reclamation);

    @Query("SELECT * FROM reclamation WHERE title = :title AND content = :content")
    List<Reclamation> getReclamationsByTitleAndContent(String title, String content);

    @Query("SELECT id FROM users WHERE users.Email = :Email")
    long getUserIdByEmail(String Email);
;

    @Query("SELECT * FROM reclamation WHERE userId = :userId")
    List<Reclamation> getReclamationsByUserId(long userId);




    @Update
    void updateReclamation(Reclamation reclamation);

    @Delete
    void deleteReclamation(Reclamation reclamation);

}
