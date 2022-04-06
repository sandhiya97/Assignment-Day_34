import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    private PreparedStatement employeePayrollDataStatement;

    private static DBConnection employeePayrollDBService;

    DBConnection(){
    }

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

    public static DBConnection getInstance() {
        if(employeePayrollDBService == null)
            employeePayrollDBService = new DBConnection();
        return employeePayrollDBService;
    }

    public List<PayrollData> getEmployeePayrollData(String name) {
        List<PayrollData> employeePayrollList = null;
        if(this.employeePayrollDataStatement == null)
            this.prepareStatementForEmployeeData();
        try {
            employeePayrollDataStatement.setString(1,name);
            ResultSet resultSet = employeePayrollDataStatement.executeQuery();
            employeePayrollList = this.getEmployeePayrollData(resultSet);
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return employeePayrollList;
    }

    private List<PayrollData> getEmployeePayrollData(ResultSet result) {
        List<PayrollData> employeePayrollList = new ArrayList<>();
        try {
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("name");
                double salary = result.getDouble("salary");
                LocalDate startDate = result.getDate("start").toLocalDate();
                employeePayrollList.add(new PayrollData(id, name, salary, startDate));
                return employeePayrollList;
            }
        }catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void prepareStatementForEmployeeData() {
        try {
            Connection connection = this.getConnection();
            String sql = "select * from employee_payroll where name = ?";
            employeePayrollDataStatement = connection.prepareStatement(sql);
        }catch(SQLException e) {
            e.printStackTrace();
        }

    }

    public int updateEmployeeData(String name, double salary) throws PayrollExceptions {
        return this.updateEmployeeDataUsingStatement(name, salary);
    }

    private int updateEmployeeDataUsingStatement(String name, double salary) throws PayrollExceptions {
        String sql = String.format("update employee_payroll set salary = %.2f where name = '%s';", salary, name);
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new PayrollExceptions(e.getMessage(), PayrollExceptions.ExceptionType.UPDATE_PROBLEM);
        }
    }

}