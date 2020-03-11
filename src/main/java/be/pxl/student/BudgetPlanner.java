package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        BudgetPlannerImporter importer = new BudgetPlannerImporter();
        Logger log = LogManager.getLogger(BudgetPlannerImporter.class);
        List<Account> accounts = importer.readFile("account_payments.csv");
        for (Account a : accounts) {
            log.error( a.toString());
            for (Payment p : a.getPayments()) {
                log.error(p.toString());
            }
        }
    }

}
