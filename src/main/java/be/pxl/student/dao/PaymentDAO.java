package be.pxl.student.dao;

import be.pxl.student.entity.Payment;
import be.pxl.student.util.BudgetPlannerImporter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Logger log = LogManager.getLogger(BudgetPlannerImporter.class);
    private static final String SELECT_BY_ID = "SELECT * FROM Payment WHERE id = ?";
    private static final String SELECT_ALL = "SELECT * FROM Payment";
    private static final String UPDATE = "UPDATE Payment SET date = ?, amount = ?, currency = ?, detail = ?, accountId = ?, counterAccountId = ? WHERE id = ?";
    private static final String INSERT = "INSERT INTO Payment (date, amount,currency,detail,accountId,counterAccountId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String DELETE = "DELETE FROM Payment WHERE id = ?";
    private String url;
    private String user;
    private String password;

    public PaymentDAO(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }


    public Payment createPayment(Payment payment) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDate(1, payment.getDate());
            stmt.setFloat(2, payment.getAmount());
            stmt.setString(3, payment.getCurrency());
            stmt.setString(4, payment.getDetail());
            stmt.setInt(5, payment.getAccountId());
            stmt.setInt(6, payment.getCounterAccountId());
            stmt.setInt(7, payment.getId());
            if (stmt.executeUpdate() == 1) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        payment.setId(rs.getInt(1));
                        return payment;
                    }
                }
            }
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    public boolean updatePayment(Payment payment) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(UPDATE)) {
            stmt.setDate(1, payment.getDate());
            stmt.setFloat(2, payment.getAmount());
            stmt.setString(3, payment.getCurrency());
            stmt.setString(4, payment.getDetail());
            stmt.setInt(5, payment.getAccountId());
            stmt.setInt(6, payment.getCounterAccountId());
            stmt.setInt(7, payment.getId());
            return stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void deletePayment(int id) {
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(DELETE)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private Payment mapPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setDate(rs.getDate("date"));
        payment.setAmount(rs.getFloat("amount"));
        payment.setCurrency(rs.getString("currency"));
        payment.setDetail(rs.getString("detail"));
        payment.setAccountId(rs.getInt("accountId"));
        payment.setCounterAccountId(rs.getInt("counterAccountId"));
        return payment;
    }

    private List<Payment> mapPayments(ResultSet rs) throws SQLException {
        ArrayList<Payment> payments = new ArrayList<>();

        Payment payment = mapPayment(rs);
        payments.add(payment);

        while (rs.next()) {
            payment = mapPayment(rs);
            payments.add(payment);
        }

        return payments;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
