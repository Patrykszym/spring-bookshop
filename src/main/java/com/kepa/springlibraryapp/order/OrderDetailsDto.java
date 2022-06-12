package com.kepa.springlibraryapp.order;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
class OrderDetailsDto {
    @NotEmpty
    private final String address;
    @NotEmpty
    @Pattern(regexp = "^(?:\\+\\d{1,3}|0\\d{1,3}|00\\d{1,2})?(?:\\s?\\(\\d+\\))?(?:[-\\/\\s.]|\\d)+$")
    @Length(min = 9, max = 13)
    private final String telephone;
}
