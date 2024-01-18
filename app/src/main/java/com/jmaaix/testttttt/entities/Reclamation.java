package com.jmaaix.testttttt.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity(tableName = "Reclamation",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Reclamation {
    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String content;

    @ColumnInfo
    private Date creationDate;

    @ColumnInfo
    public long userId;
    public long getUserId() {
        return userId;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Reclamation(String title, String content, long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;

        this.creationDate = new Date();// Initialize lastModified with the current date
    }


    public Reclamation() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
