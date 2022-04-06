
import java.util.List;
import java.util.Scanner;

public class PayrollService {
    private DBConnection employeePayrollDBService;
    public enum IOService {
        CONSOLE_IO, FILE_IO, DB_IO, REST_IO
    }

    public void printWelcomeMessage() {
        System.out.println("Welcome to the Employee PayRoll Service");
    }

    private static List<PayrollData> employeePayrollList;

    public PayrollService(List<PayrollData> employeePayrollList) {
        PayrollService.employeePayrollList = employeePayrollList;
    }

    public PayrollService() {
    }

    public static void main(String[] args) {
        PayrollService employeePayrollService = new PayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.CONSOLE_IO);
    }

    public void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.print("Enter Employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.print("Enter Employee Name ");
        String name = consoleInputReader.next();
        System.out.print("Enter Employee Salary ");
        double salary = consoleInputReader.nextDouble();
        employeePayrollList.add(new PayrollData(id, name, salary));
    }

    public void writeEmployeePayrollData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting Employee Payroll Roaster to Console\n" + employeePayrollList);
        else if (ioService.equals(IOService.FILE_IO)) {
            new PayrollFileIO().writeData(employeePayrollList);
        }
    }

    public void printData(IOService fileIo) {
        if (fileIo.equals(IOService.FILE_IO)) {
            new PayrollFileIO().printData();
        }

    }

    public long countEntries(IOService fileIo) {
        if (fileIo.equals(IOService.FILE_IO)) {
            return new PayrollFileIO().countEntries();
        }
        return 0;
    }

    public List<PayrollData> readPayrollData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            employeePayrollList = new PayrollFileIO().readData();
        return employeePayrollList;
    }

    public List<PayrollData> readEmployeePayrollData(IOService ioService) throws PayrollExceptions {
        if (ioService.equals(IOService.DB_IO))
            employeePayrollList = new DBConnection().readData();
        return employeePayrollList;
    }

    public void updateEmployeeSalary(String name, double salary) throws PayrollExceptions {
        int result = employeePayrollDBService.updateEmployeeData(name, salary);
        if (result == 0)
            return;
        PayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if (employeePayrollData != null)
            employeePayrollData.setSalary(salary);

    }

    private PayrollData getEmployeePayrollData(String name) {
        PayrollData employeePayrollData;
        employeePayrollData = employeePayrollList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.getName().equals(name))
                .findFirst()
                .orElse(null);
        return employeePayrollData;
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<PayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));

    }
}