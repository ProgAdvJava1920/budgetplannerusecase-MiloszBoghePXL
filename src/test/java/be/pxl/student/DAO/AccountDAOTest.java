package be.pxl.student.DAO;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountDAOTest {
    @Test
    public void testAccountInsert(){
        Account account = new Account();
        account.setIBAN("");
        account.setPayments(new ArrayList<Payment>());
        account.setName("Satan");

        AccountDAO accountDAO = new AccountDAO("jdbc:mysql://localhost:3306/budgetplanner","root","admin");
        Account accountInserted = accountDAO.createAccount(account);
        assertEquals(account,accountInserted);

    }
}
