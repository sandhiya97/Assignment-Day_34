
import java.time.LocalDate;

public class PayrollData {
    private final int id;
    private final String name;
    private final double salary;
    private LocalDate start;

    public PayrollData(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public PayrollData(int id, String name, double salary, LocalDate start) {
        this(id,name,salary);
        this.start = start;
    }

    @Override
    public String toString() {
        return "EmployeePayrollData [ID=" + id + ", Name=" + name + ", Salary=" + salary + ", Start=" + start + "]";
    }
}