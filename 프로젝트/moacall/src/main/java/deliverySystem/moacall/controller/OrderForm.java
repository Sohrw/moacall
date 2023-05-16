package deliverySystem.moacall.controller;


import deliverySystem.moacall.domain.Address;
import deliverySystem.moacall.domain.OrderStatus;
import deliverySystem.moacall.domain.PaymentType;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderForm {

    private String clientCity;
    private String clientStreet;
    private String clientZipcode;
    private String clientDetailAddress;
    private int clientPrice;
    private int deliveryPrice;
    private String memo;
    private PaymentType paymentType;


}
