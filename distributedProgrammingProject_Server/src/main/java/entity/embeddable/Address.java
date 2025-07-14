package entity.embeddable;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address implements Serializable {
    private String number;
    private String street;
    private String ward;
    private String district;
    private String city;
    private String country;
}
