package com.alesaudate.soasymposium.rest.entities.flight;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.alesaudate.soasymposium.rest.entities.Contact;



@Entity
@XmlRootElement
public class Booking {
	
	@Id
	private String identifier;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Contact contact;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Flight flight;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
	public Flight getFlight() {
		return flight;
	}
	
	public void setFlight(Flight flight) {
		this.flight = flight;
	}


	public String toJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("{").append(getString("id")).append(":").append(getString(getIdentifier())).append(",").append(getString("type")).append(":").append(getString("RESERVATION")).append("}");
		return builder.toString();
	}
	
	public String toConfirmationJson() {
		StringBuilder builder = new StringBuilder();
		builder.append("{").append(getString("id")).append(":").append(getString(getIdentifier())).append(",").append(getString("type")).append(":").append(getString("CONFIRMATION")).append("}");
		return builder.toString();
	}
	
	
	public static void main(String[] args) {
		Booking booking = new Booking();
		booking.setIdentifier("123");
		
		System.out.println(booking.toJson());
		
	}
	
	private static String getString(String string) {
		return "\"" + string + "\"";
	}
	
}
