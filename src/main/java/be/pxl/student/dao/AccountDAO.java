package be.pxl.student.dao;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Logger log = LogManager.getLogger(BudgetPlannerImporter.class);
    private static final String SELECT_BY_ID = "SELECT * FROM Account WHERE id = ?";
    private static final String SELECT_BY_IBAN = "SELECT * FROM Account WHERE IBAN = ?";
    private static final String SELECT_ALL = "SELECT * FROM Account order by id desc";
    private static final String UPDATE = "UPDATE Account SET name=?, IBAN=? WHERE id = ?";
    private static final String INSERT = "INSERT INTO Account (name, IBAN) VALUES (?, ?)";
    private static final String DELETE = "DELETE FROM Account WHERE id = ?";
    private String url;
    private String user;
    private String password;

    public AccountDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    public Account createAccount(Account account) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getIBAN());
            if (getAccountByIBAN(account.getIBAN()) != null) {
                throw new SQLException("Bestaat al");
            } else {
                if (stmt.executeUpdate() == 1) {
                    try (ResultSet rs = stmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            account.setId(rs.getInt(1));
                            return account;
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public boolean updateAccount(Account account) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setString(1, account.getName());
            stmt.setString(2, account.getIBAN());
            stmt.setInt(3, account.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteAccount(int id) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<Account> getAllAccounts() {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAccounts(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public Account getAccountById(int id) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_BY_ID)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Account getAccountByIBAN(String iban) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_BY_IBAN)) {
            stmt.setString(1, iban);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAccount(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean deleteAllAccounts() {
        List<Account> accounts = getAllAccounts();
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            for (Account acc : accounts) {
                stmt.setInt(1, acc.getId());
                stmt.execute();
            }
        } catch (SQLException | NullPointerException ex) {
            log.error(ex.getMessage());
        }
        return false;
    }

    public int getMaxId() {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(SELECT_ALL)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapAccount(rs).getId();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    private Account mapAccount(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setName(rs.getString("name"));
        account.setIBAN(rs.getString("iban"));
        account.setId(rs.getInt("id"));
        return account;
    }

    private List<Account> mapAccounts(ResultSet rs) throws SQLException {
        ArrayList<Account> accounts = new ArrayList<>();

        Account account = mapAccount(rs);
        accounts.add(account);

        while (rs.next()) {
            account = mapAccount(rs);
            accounts.add(account);
        }

        return accounts;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
