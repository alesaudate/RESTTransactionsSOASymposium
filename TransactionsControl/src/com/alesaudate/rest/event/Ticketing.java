package com.alesaudate.rest.event;

import java.util.List;

public class Ticketing {
	
	
	private String ticketingType;
	private String identifier;
	
	private String customerName;
	private Double price;
	private String origin;
	private List<String> destinations;
	
	public String getTicketingType() {
		return ticketingType;
	}
	public void setTicketingType(String ticketingType) {
		this.ticketingType = ticketingType;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public List<String> getDestinations() {
		return destinations;
	}
	public void setDestinations(List<String> destinations) {
		this.destinations = destinations;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Ticketing [ticketingType=" + ticketingType + ", identifier="
				+ identifier + ", customerName=" + customerName + ", price="
				+ price + ", origin=" + origin + ", destinations="
				+ destinations + "]";
	}
	
	
	
	
	
	

}
