package com.alesaudate.soasymposium.rest.entities.car;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class Cars {

	
	private List<Car> cars;
	
	
	@XmlElement(name="car")
	public List<Car> getCars() {
		return cars;
	}
	
	public void setCars(List<Car> cars) {
		this.cars = cars;
	}
	
}
