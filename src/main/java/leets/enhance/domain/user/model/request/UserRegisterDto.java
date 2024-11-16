package leets.enhance.domain.user.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRegisterDto {  // 수정: DTO 모두 class -> record

    @NotNull
    @Email
    private String email;

    @NotNull
    private String name;

    @NotNull
    private String pwd;

    @NotNull
    private String checkPwd;

    @NotNull
    private Boolean isChecked;
}
