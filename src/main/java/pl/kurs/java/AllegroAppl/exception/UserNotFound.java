package pl.kurs.java.AllegroAppl.exception;

public class UserNotFound extends Throwable{
    public UserNotFound(){
        super("User not found");
    }
}
