package com.alesaudate.soasymposium.rest.entities.hotel;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Hotels {
	
	private List<Hotel> hotels;

	
	@XmlElement(name="hotel")
	public List<Hotel> getHotels() {
		return hotels;
	}

	public void setHotels(List<Hotel> hotels) {
		this.hotels = hotels;
	}
	
	

}
