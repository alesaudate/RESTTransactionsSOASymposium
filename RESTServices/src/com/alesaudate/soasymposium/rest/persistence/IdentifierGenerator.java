package com.alesaudate.soasymposium.rest.persistence;

import java.util.UUID;

public class IdentifierGenerator {
	
	
	
	public static String generateId() {
		return UUID.randomUUID().toString();
	}

}
