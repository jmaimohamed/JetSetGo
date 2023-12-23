package com.jmaaix.testttttt.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "Note",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Note {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo
    private String title;

    @ColumnInfo
    private String content;

    @ColumnInfo
    public long userId; // This is the foreign key that links to the User table

    // Add getters and setters for the user_id field
    public long getUser_id() {
        return userId;
    }

    public void setUser_id(long user_id) {
        this.userId = user_id;
    }


    public Note()
    {}

    public String getTitle(){

        return title;
    }

    public void setTitle(String title)
    {
        this.title =title;
    }

    public String getContent(){
        return content;
    }
    public void setContent (String content)
    {
        this.content = content;

    }

    public Note(String title, String content, long user_id){
        this.title=title;
        this.content =content;
        this.userId = user_id;

    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
