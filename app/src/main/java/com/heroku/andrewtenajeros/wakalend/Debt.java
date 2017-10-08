package com.heroku.andrewtenajeros.wakalend;

/**
 * Created by Andrew on 9/7/2017.
 */

public class Debt {


    public String ID;
    public String Loan;
    public String Balance;
    public String days_to_pay;
    public String daily_collection;
    public String collector;

    public Debt(String loan, String days_to_pay) {
        Loan = loan;
        Balance = loan;
        this.days_to_pay = days_to_pay;
    }

    public String getID() {
        return ID;
    }

    public void setCollector(String collector) {
        this.collector = collector;
    }

    public String getLoan() {
        return Loan;
    }

    public String getBalance() {
        return Balance;
    }

    public String getDays_to_pay() {
        return days_to_pay;
    }

    public String getDaily_collection() {
        return daily_collection;
    }

    public String getCollector() {
        return collector;
    }
}
