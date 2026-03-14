package ServiceProduct.products.exceptions;

public class QuantityLimitExceededException extends RuntimeException{
    public QuantityLimitExceededException(String message)
    {
        super(message);
    }
}
