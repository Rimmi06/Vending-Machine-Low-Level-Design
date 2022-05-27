package StateVendingMachine;

public class NotSufficientChange extends  RuntimeException{
    private String message;

    public NotSufficientChange(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
