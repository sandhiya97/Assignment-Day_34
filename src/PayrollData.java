
import java.time.LocalDate;

public class PayrollData {
    private final int id;
    private final String name;
    private double salary;
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

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getName() {
        return this.name;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        long temp;
        temp = Double.doubleToLongBits(salary);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PayrollData other = (PayrollData) obj;
        if (id != other.id)
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (Double.doubleToLongBits(salary) != Double.doubleToLongBits(other.salary))
            return false;
        if (start == null) {
            return other.start == null;
        } else return start.equals(other.start);
    }
}