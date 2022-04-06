
import org.junit.BeforeClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class PayrollServiceTest {
    static PayrollService employeePayrollService;

    @BeforeClass
    public static void initializeConstructor()
    {
        employeePayrollService = new PayrollService();
    }

    @Test
    public void printWelcomeMessage() {

        employeePayrollService.printWelcomeMessage();
    }

    @Test
    public void given3EmployeesWhenWrittenToFileShouldMatchEmployeeEntries() {
        PayrollData[] arrayOfEmployees = {
                new PayrollData(1, "Stark", 500000.0),
                new PayrollData(2, "Parker", 200000.0),
                new PayrollData(3, "Hogan", 300000.0)
        };
        employeePayrollService = new PayrollService(Arrays.asList(arrayOfEmployees));
        employeePayrollService.writeEmployeePayrollData(PayrollService.IOService.FILE_IO);
        employeePayrollService.printData(PayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(PayrollService.IOService.FILE_IO);
        Assertions.assertEquals(3, entries);
    }

    @Test
    public void givenFileOnReadingFileShouldMatchEmployeeCount() {
        PayrollService employeePayrollService = new PayrollService();
        List<PayrollData> entries = employeePayrollService.readPayrollData(PayrollService.IOService.FILE_IO);
    }

}
