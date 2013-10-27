package com.alesaudate.soasymposium.rest.entities.flight;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Flights {
	
	
	
	
	private List<Flight> flights;
	
	
	public Flights() {
		
	}
	
	
	public Flights(List<Flight> flights) {
		this.flights = flights;
	}
	
	@XmlElement(name="flight")
	public List<Flight> getFlights() {
		return flights;
	}
	
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}

}
