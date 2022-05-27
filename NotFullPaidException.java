package StateVendingMachine;

public class NotFullPaidException extends RuntimeException{
    private String message;

    public NotFullPaidException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
