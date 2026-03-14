package ServiceOrder.orders.exceptions;

public class MemberNotFoundException extends RuntimeException{
    public MemberNotFoundException(String message)
    {
        super(message);
    }
}
