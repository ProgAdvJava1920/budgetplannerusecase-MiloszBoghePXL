package be.pxl.student.entity;

import java.util.ArrayList;
import java.util.List;

public class Account {

    private int id;
    private String IBAN;
    private String name;
    private List<Payment> payments;

    public Account() {

    }

    public Account(String iban) {
        setName("Unknown");
        setIBAN(iban);
        this.payments = new ArrayList<>();
    }

    public Account(String IBAN, String name, List<Payment> payments) {
        this.IBAN = IBAN;
        this.name = name;
        this.payments = payments;
    }

    public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String IBAN) {
        this.IBAN = IBAN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "Account: " +
                "IBAN: '" + IBAN + '\'' +
                ", name: '" + name + '\'';
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
