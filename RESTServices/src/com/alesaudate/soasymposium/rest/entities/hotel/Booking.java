package com.alesaudate.soasymposium.rest.entities.hotel;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.alesaudate.soasymposium.rest.entities.Contact;


@XmlRootElement
@Entity(name="HotelBooking")
public class Booking {

	
	@Id
	private String identifier;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Contact contact;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Hotel hotel;
	
	private Date periodFrom;
	
	private Date periodTo;
	
	private Integer numberOfPeople;

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

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Date getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Date periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Date getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Date periodTo) {
		this.periodTo = periodTo;
	}

	public Integer getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(Integer numberOfPeople) {
		this.numberOfPeople = numberOfPeople;
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
	
	private static String getString(String string) {
		return "\"" + string + "\"";
	}
	
	
	
}
