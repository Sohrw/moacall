package deliverySystem.moacall.controller;


import deliverySystem.moacall.domain.Address;
import deliverySystem.moacall.domain.MemberStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter @Setter
public class MemberForm {

    @NotEmpty(message = "필수입니다.")
    private String userId;

    @NotEmpty(message = "필수입니다.")
    private String password;

    @NotEmpty(message = "필수입니다.")
    private String name;

    private String city;
    private String street;
    private String zipcode;
    private String detailAddress;

    private String memberStatus;



}
