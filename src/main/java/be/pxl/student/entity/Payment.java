package be.pxl.student.entity;

import java.time.LocalDateTime;

public class Payment {

    private int id;
    private int accountId;
    private int counterAccountId;
    private LocalDateTime date;
    private double amount;
    private String currency;
    private String detail;

    public Payment() {

    }

    public Payment(LocalDateTime date, double amount, String currency, String detail) {
        this.date = date;
        this.amount = amount;
        this.currency = currency;
        this.detail = detail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCounterAccountId() {
        return counterAccountId;
    }

    public void setCounterAccountId(int counterAccountId) {
        this.counterAccountId = counterAccountId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "Date: " + '\'' + date + '\'' +
                ", amount: " + '\'' + amount + '\'' +
                ", currency: '" + '\'' + currency + '\'' +
                ", detail: '" + '\'' + detail + '\'';
    }
}
