package ServiceOrder.orders.exceptions;

public class QuantityLimitExceededException extends RuntimeException{
    public QuantityLimitExceededException(String message)
    {
        super(message);
    }
}
