package exceptions;

public class CouponSystemExceptions extends Exception{
    public CouponSystemExceptions(ErrMsg errMsg) {
        super(errMsg.getMessage());
    }
}
