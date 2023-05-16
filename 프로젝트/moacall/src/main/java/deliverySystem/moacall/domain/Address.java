package deliverySystem.moacall.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter @Setter
public class Address {
    private String city;
    private String street;
    private String zipcode;
    private String detailAddress;

    protected Address() {

    }

    public Address(String city, String street, String zipcode, String detailAddress) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
        this.detailAddress = detailAddress;
    }
}
