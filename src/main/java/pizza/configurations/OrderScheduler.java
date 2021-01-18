package pizza.configurations;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.maps.model.LatLng;

import pizza.models.Order;
import pizza.services.OrderGeneratorService;
import pizza.services.ShopService;

import org.springframework.messaging.simp.SimpMessagingTemplate;

@EnableScheduling
@Configuration
public class OrderScheduler {
int counter;

@Autowired
SimpMessagingTemplate wsMessage;
@Autowired
private ShopService shopService;
@Autowired
private OrderGeneratorService orderService;



	@Scheduled(fixedDelay = 1000)
	public void sendOrder() {
		this.counter++;
		System.out.println("New Counter - "+ this.counter);
				
		Order lastOrder = orderService.getLastOrder();
		if (lastOrder != null) {
			System.out.println("New Order coming: "+ lastOrder.latlng.toString());
			wsMessage.convertAndSend("/topic/user", lastOrder);
		}
	}
	
	@Scheduled(fixedDelay = 5000)
	public void sendShops() {
		ArrayList shopList = shopService.getShopList();
		System.out.println("----Shop List---- - "+ shopList);
		if(!shopList.isEmpty()) {
			wsMessage.convertAndSend("/topic/shops", shopList);
		}
	}
}
