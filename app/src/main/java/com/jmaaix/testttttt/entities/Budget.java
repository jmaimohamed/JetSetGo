package com.jmaaix.testttttt.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "budget",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "userId",
                onDelete = ForeignKey.CASCADE
        )
)
public class Budget {
    @PrimaryKey(autoGenerate = true)
    private long budgetId;
    private long userId;
    private double BFood;
    private double BAccomodation;
    private double BShopping;
    private  double BTransport;
    private  double BLoisir;
    private double totalBudget;


    // Constructors
    public Budget() {
        // Default constructor required by Room
    }

    public Budget(long userId, double totalBudget) {
        this.userId = userId;
        this.totalBudget = totalBudget;
    }

    // Getters and Setters

    public double getBFood(){return BFood;}
    public void setBFood(double BFood){this.BFood=BFood;}

    public double getBAccomodation(){return BAccomodation;}
    public void setBAccomodation(double BAccomodation){this.BAccomodation=BAccomodation;}
    public double getBShopping(){return BShopping;}
    public void setBShopping(double BShopping){this.BShopping=BShopping;}

    public double getBLoisir(){return BLoisir;}
    public void setBLoisir(double BLoisir){this.BLoisir=BLoisir;}


    public double getBTransport(){return BTransport;}
    public void setBTransport(double BTransport){this.BTransport=BTransport;}

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(double totalBudget) {
        this.totalBudget = totalBudget;
    }
}
