package pizza.handler;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.maps.model.LatLng;

import pizza.model.Shop;
import pizza.services.ShopService;

public class ShopWebSocketHandler extends TextWebSocketHandler{

	@Autowired
	private ShopService shopService;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Shop Connectetion Established with ID: "+ session.getId());
		ArrayList<Shop> shopList = shopService.getShopList();
		if(!shopList.isEmpty()) {
			JsonObject response = new JsonObject();
			JsonArray shops = new JsonArray();
			for(Shop item : shopList) {
				JsonObject shop = new JsonObject();
				JsonObject latLng = new JsonObject();
				latLng.addProperty("lat", item.latlng.lat);
				latLng.addProperty("lng", item.latlng.lng);
				shop.add("latLng", latLng);
				
				shop.addProperty("name", item.name);
				shop.addProperty("name", item.name);
				
				JsonArray polygon = new JsonArray();
				for(LatLng polyLatLng : item.polygon) {
					JsonObject polyCoord = new JsonObject();
					polyCoord.addProperty("lat", polyLatLng.lat);
					polyCoord.addProperty("lng", polyLatLng.lng);
					polygon.add(polyCoord);
				}
				shop.add("polygon", polygon);
								
				shops.add(shop);
			}
			response.add("shopList", shops);
			session.sendMessage(new TextMessage(response.toString()));
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Shop Session closed: " + status);
	}
}

