package entity;

import entity.constant.Gender;
import entity.embeddable.Address;
import entity.embeddable.Contact;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@MappedSuperclass
public abstract class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    protected int id;
    protected String name;
    protected LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    protected Gender gender;
    @Embedded
    protected Address address;
    @Embedded
    protected Contact contact;
}
