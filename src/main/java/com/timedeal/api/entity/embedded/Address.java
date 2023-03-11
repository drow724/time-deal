package com.timedeal.api.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Address {

	private String city;

}
