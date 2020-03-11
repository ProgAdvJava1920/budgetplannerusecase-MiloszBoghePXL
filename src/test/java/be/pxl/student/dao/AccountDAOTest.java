package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class AccountDAOTest {
    private AccountDAO accountDAO;
    private static final String NUMERIC_STRING = "0123456789";

    @BeforeEach
    public void init() {
        accountDAO = new AccountDAO("jdbc:mysql://localhost:3306/budgetplanner", "root", "admin");
    }

    @Test
    public void test_AccountInsert_Success() {
        Account account = createTestAccount();
        Account accountInserted = accountDAO.createAccount(account);
        assertEquals(account, accountInserted);
    }

    @Test
    public void test_GetAllAccounts_Success() {
        Account account = createTestAccount();
        Account accountInserted = accountDAO.createAccount(account);
        assertEquals(account, accountInserted);
    }

    @Test
    public void test_AccountInsertAndDelete_Success() {
        Account account = accountDAO.createAccount(createTestAccount());
        int id = account.getId();
        assertNotNull(accountDAO.getAccountById(id));
        accountDAO.deleteAccount(id);
        assertNull(accountDAO.getAccountById(id));
    }

    @Test
    public void test_AccountInsert_Fails_IfAlreadyExists() {
        Account account = accountDAO.createAccount(createTestAccount());
        assertNull(accountDAO.createAccount(account));
        accountDAO.deleteAccount(account.getId());
    }

    private Account createTestAccount() {
        String iban = "BE" + randomNumeric();
        ArrayList<Payment> payments = new ArrayList<>();
        String name = "Satan";
        return new Account(iban, name, payments);
    }

    private String randomNumeric() {

        StringBuilder builder = new StringBuilder();
        for (int i = 14; i > 0; i--) {
            int character = (int) (Math.random() * NUMERIC_STRING.length());
            builder.append(NUMERIC_STRING.charAt(character));
        }

        return builder.toString();
    }

}
