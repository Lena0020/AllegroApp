package pl.kurs.java.AllegroAppl.exception;

public class PaymentError extends Throwable{
    public PaymentError() {
        super("There has been a payment error");
    }
}
