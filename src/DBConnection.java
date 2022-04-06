
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

public class DBConnection {

    public static void main(String[] args) {
        String connectionUrl = "jdbc:mysql://localhost:3306/payroll_services";
        String userName = "root";
        String password = "qwerty";
        Connection connection;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        }catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find the driver in the classpath");
        }

        listDrivers();
        try {
            System.out.println("Connecting to database:" + connectionUrl);
            connection = DriverManager.getConnection(connectionUrl,userName,password);
            System.out.println("Connection Success!!"+connection);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void listDrivers() {
        Enumeration<Driver> driverList = DriverManager.getDrivers();
        while(driverList.hasMoreElements()) {
            Driver driverClass = driverList.nextElement();
            System.out.println(driverClass.getClass().getName());
        }

    }

}
