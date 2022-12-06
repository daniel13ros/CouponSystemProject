package exceptions;

public enum ErrMsg {

    COMPANY_NAME_EXIST("Cannot add company with exiting name"),
    COMPANY_EMAIL_EXIST("Cannot add company with exiting email"),
    CUSTOMER_NAME_EMAIL_EXIST("Cannot add  with exiting email"),
    CUSTOMER_NOT_EXIST("Cannot delete non exist customer"),
    COMPANY_NOT_EXIST("Cannot delete non exist company"),
    CUSTOMER_NAME_EXIST("Cannot update with exiting name"),
    COUPON_EXIST_BT_TITLE("Cannot add coupon with exiting title"),
    COUPON_NOT_EXIST("Cannot delete non exist coupon"),
    COUPON_IS_EXIST_IN_CUSTOMER("Customer has this coupon already"),
    COUPON_AMOUNT_IS_ZERO("Coupon sold out"),
    EMAIL_OR_PASS_WRONG("email or password are wrong"),
    NO_COMPANIES("you are not able to add coupon to non exist company"),
    NO_CUSTOMERS("you are not able to purchase coupon to non exist company"),
    COUPON_EXPIRED("Coupon expired");


    private String message;
    ErrMsg(String message) {
        this.message=message;
    }

    public String getMessage() {
        return message;
    }
}
