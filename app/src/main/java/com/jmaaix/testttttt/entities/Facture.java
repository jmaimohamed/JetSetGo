package com.jmaaix.testttttt.entities;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "facture",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Facture {
    @PrimaryKey(autoGenerate = true)
    private long FactureId;
    private long userId;
    private byte[] Image;

    public Facture(){
    }

    public Facture(long userId, byte[] Image) {
        this.userId = userId;
        this.Image = Image;
    }
//getters and setters
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public long getFactureId(){ return FactureId;}
    public void setFactureId(long FactureId) {
        this.FactureId = FactureId;
    }


    public byte[] getImage(){return Image;}
    public void setImage(byte[] Image){this.Image=Image;}

}
