package com.alesaudate.resttransactions.events;

import java.io.Serializable;

public class Confirmation implements Serializable {
	
	private String bookingIdentifier;

	public String getBookingIdentifier() {
		return bookingIdentifier;
	}

	public void setBookingIdentifier(String bookingIdentifier) {
		this.bookingIdentifier = bookingIdentifier;
	}

	
	
}
