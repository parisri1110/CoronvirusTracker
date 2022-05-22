package com.insanecoder.coronavirustracker.model;

public class LocationStats {

	
	private String state;
	private String country;
	private int latestTotalCases;
	private int differenceTotalCases;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public int getLatestTotalCases() {
		return latestTotalCases;
	}
	public void setLatestTotalCases(int latestTotalCases) {
		this.latestTotalCases = latestTotalCases;
	}
	
	public int getDifferenceTotalCases() {
		return differenceTotalCases;
	}
	public void setDifferenceTotalCases(int differenceTotalCases) {
		this.differenceTotalCases = differenceTotalCases;
	}
	@Override
	public String toString() {
		return "LocationStats [state=" + state + ", country=" + country + ", latestTotalCases=" + latestTotalCases
				+ "]";
	}
	
}
