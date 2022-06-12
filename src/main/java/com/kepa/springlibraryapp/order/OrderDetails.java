package com.kepa.springlibraryapp.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@NoArgsConstructor
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String address;
    @NotEmpty
    @Pattern(regexp="^(?:\\+\\d{1,3}|0\\d{1,3}|00\\d{1,2})?(?:\\s?\\(\\d+\\))?(?:[-\\/\\s.]|\\d)+$")
    @Length(min = 9, max = 13)
    private String telephone;

    OrderDetails(final String address, final String telephone) {
        this.address = address;
        this.telephone = telephone;
    }
}
