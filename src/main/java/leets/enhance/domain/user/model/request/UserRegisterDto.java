package leets.enhance.domain.user.model.request;

import lombok.Getter;

@Getter
public class UserRegisterDto {  // 수정: DTO 모두 class -> record
    private String email;   // 수정: Validation 추가
    private String name;
    private String pwd;
    private String checkPwd;
    private Boolean isChecked;
}
