package com.alesaudate.soasymposium.rest.entities.car;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.alesaudate.soasymposium.rest.entities.Contact;


@XmlRootElement
@Entity
public class CarRental {

	@Id
	private String identifier;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Car car;
	
	
	@ManyToOne(cascade=CascadeType.PERSIST)
	private Contact contact;
	
	private Date periodFrom;
	
	private Date periodTo;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
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
