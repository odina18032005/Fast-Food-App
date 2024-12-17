package uz.pdp.fast_food_app.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class EmailOrPasswordWrong extends RuntimeException {
    private HttpStatus status;

    public EmailOrPasswordWrong(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }
}
