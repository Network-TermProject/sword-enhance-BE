package leets.enhance.domain.user.model.request;

import lombok.Getter;

@Getter
public class UserRegisterDto {

    private String email;
    private String name;
    private String pwd;
    private String checkPwd;
}
