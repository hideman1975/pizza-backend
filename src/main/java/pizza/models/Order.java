package pizza.models;

import java.util.ArrayList;

import com.google.maps.model.LatLng;

public class Order {
	public String container;
	public LatLng latlng;
	public ArrayList<LatLng> path;
	
	public Order(LatLng latlng, String container, ArrayList<LatLng> path) {
		this.container = container;
		this.latlng = latlng;
		this.path = path;
	}
	
	public Order() {
		this.container = "Test Order";
		this.latlng = new LatLng(33.55, 12.65);
		//this.path = [this.latlng];
	}
	
	public String showMe() {
		return container;
	}
}
