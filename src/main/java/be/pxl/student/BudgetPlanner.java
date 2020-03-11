package be.pxl.student;

import be.pxl.student.dao.AccountDAO;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BudgetPlanner {
    public static void main(String[] args) {
        BudgetPlannerImporter importer = new BudgetPlannerImporter();
        Logger log = LogManager.getLogger(BudgetPlannerImporter.class);
        List<Account> accounts = importer.readFile("account_payments.csv");
        AccountDAO accountDAO = new AccountDAO("jdbc:mysql://localhost:3306/budgetplanner", "root", "admin");
        accountDAO.deleteAllAccounts();
        for (Account account : accounts) {
            accountDAO.createAccount(account);
            log.error( account.toString());
            for (Payment p : account.getPayments()) {
                log.error(p.toString());
            }
        }
    }

}
