
public class PayrollExceptions extends Exception {
    enum ExceptionType{
        CONNECTION_PROBLEM, RETRIEVAL_PROBLEM;
    }

    ExceptionType type;
    PayrollExceptions(String message, ExceptionType type){
        super(message);
        this.type = type;
    }
}