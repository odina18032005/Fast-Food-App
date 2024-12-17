package uz.pdp.fast_food_app.model;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.fast_food_app.dto.UserDTO;

@Getter
public class TemporaryUser {
    private final UserDTO userDTO;
    private final MultipartFile file;
    private final String verificationCode;

    public TemporaryUser(UserDTO userDTO, MultipartFile file, String verificationCode) {
        this.userDTO = userDTO;
        this.file = file;
        this.verificationCode = verificationCode;
    }
}
