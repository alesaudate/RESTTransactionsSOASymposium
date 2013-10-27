package com.alesaudate.soasymposium.rest.entities.flight;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@XmlRootElement
public class Flight {
	
	
	@Id
	private String identifier;
	
	private Date flightDate;
	
	
	private String fromDestination;
	
	private String toDestination;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getFlightDate() {
		return flightDate;
	}
	
	public void setFlightDate(Date flightDate) {
		this.flightDate = flightDate;
	}

	public String getFromDestination() {
		return fromDestination;
	}

	public void setFromDestination(String fromDestination) {
		this.fromDestination = fromDestination;
	}

	public String getToDestination() {
		return toDestination;
	}

	public void setToDestination(String toDestination) {
		this.toDestination = toDestination;
	}
	
	
	

}
