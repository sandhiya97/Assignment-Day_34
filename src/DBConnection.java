
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class DBConnection {


    private Connection getConnection() throws SQLException {
        String connectionUrl = "jdbc:mysql://localhost:3306/payroll_services";
        String userName = "root";
        String password = "qwerty";
        Connection con;
        System.out.println("Connecting to database:" + connectionUrl);
        con = DriverManager.getConnection(connectionUrl, userName, password);
        System.out.println("Connection Successful!!" + con);
        return con;
    }

    public List<PayrollData> readData() throws PayrollExceptions {
        String sql = "select * from employee_payroll";
        List<PayrollData> employeePayrollList = new ArrayList<>();
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            while(result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                LocalDate startDate = result.getDate("start").toLocalDate();
                employeePayrollList.add(new PayrollData(id,name,salary,startDate));
            }
        } catch (SQLException e) {
            throw new PayrollExceptions(e.getMessage(),PayrollExceptions.ExceptionType.RETRIEVAL_PROBLEM);
        }
        return employeePayrollList;
    }
}