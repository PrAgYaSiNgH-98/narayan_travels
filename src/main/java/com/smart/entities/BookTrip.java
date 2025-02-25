package com.smart.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BookTrip {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")

	private long id ;
	private String Name;
	private long Mobile;
	private String fromLocation;
    private String toLocation;
    private LocalDate departureDate;
    private LocalDate returnDate; 
    private int numTravelers;
    private String typeVehical;
    private int noOfVehical;
	public int getNoOfVehical() {
		return noOfVehical;
	}
	public void setNoOfVehical(int noOfVehical) {
		this.noOfVehical = noOfVehical;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public long getMobile() {
		return Mobile;
	}
	public void setMobile(long mobile) {
		Mobile = mobile;
	}
	public LocalDate getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(LocalDate departureDate) {
		this.departureDate = departureDate;
	}
	public LocalDate getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(LocalDate returnDate) {
		this.returnDate = returnDate;
	}
	public int getNumTravelers() {
		return numTravelers;
	}
	public void setNumTravelers(int numTravelers) {
		this.numTravelers = numTravelers;
	}
	public String getTypeVehical() {
		return typeVehical;
	}
	public void setTypeVehical(String typeVehical) {
		this.typeVehical = typeVehical;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFromLocation() {
		return fromLocation;
	}
	public void setFromLocation(String fromLocation) {
		this.fromLocation = fromLocation;
	}
	public String getToLocation() {
		return toLocation;
	}
	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	
}
