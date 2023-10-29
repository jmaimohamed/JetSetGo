package com.jmaaix.testttttt.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fingerPrint")
public class FingerprintData  {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private long userId; // Foreign key to associate fingerprint data with a user
    private String fingerprintHash;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFingerprintHash() {
        return fingerprintHash;
    }

    public void setFingerprintHash(String fingerprintHash) {
        this.fingerprintHash = fingerprintHash;
    }
}
