package pizza.models;

import java.util.ArrayList;

import com.google.maps.model.LatLng;

public class Shop {
	public String name;
	public LatLng latlng;
	public  ArrayList<LatLng> polygon = new ArrayList<>();
	
	public Shop(LatLng latlng, String name) {
		this.name = name;
		this.latlng = latlng;
	}
	
	public void setPolygon(ArrayList<LatLng> polygon) {
		this.polygon = polygon;
	}
}
