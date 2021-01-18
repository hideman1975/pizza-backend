package pizza.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;

import pizza.models.Shop;

@Service
public class ShopService {

	static private ArrayList<Shop> shopList= new ArrayList<>();
		
	ShopService() {
		System.out.println("-----Shop Service ---------");
		ArrayList<LatLng> polygon = new ArrayList<>();
		ArrayList<LatLng> polygon1 = new ArrayList<>();
		ArrayList<LatLng> polygon2 = new ArrayList<>();
		ArrayList<LatLng> polygon3 = new ArrayList<>();
		ArrayList<LatLng> polygon4 = new ArrayList<>();
		
		polygon.add(new LatLng(56.3245594, 43.820239));
		polygon.add(new LatLng(56.3068925, 43.9495));
		polygon.add(new LatLng(56.265022, 43.954994));
		polygon.add(new LatLng(56.264736, 43.88659));
		polygon.add(new LatLng(56.279274, 43.832344));
		shopList.add(new Shop(new LatLng(56.287643, 43.927855), "Муравей"));
		shopList.get(0).setPolygon(polygon);
		
		polygon1.add(new LatLng(56.3209465, 44.06182524));
		polygon1.add(new LatLng(56.330089, 44.02846053));
		polygon1.add(new LatLng(56.326667, 43.980447));
		polygon1.add(new LatLng(56.273259, 43.972390));
		polygon1.add(new LatLng(56.2740199, 44.028938));
		shopList.add(new Shop(new LatLng(56.309084, 43.986268), "Небо"));
		shopList.get(1).setPolygon(polygon1);
		
		polygon2.add(new LatLng(56.2707879, 43.972633));
		polygon2.add(new LatLng(56.236785, 43.939103));
		polygon2.add(new LatLng(56.215512, 43.928690));
		polygon2.add(new LatLng(56.1985946, 43.990228));
		polygon2.add(new LatLng(56.2710606, 44.027363));
		shopList.add(new Shop(new LatLng(56.235055, 43.951469), "Гагаринский"));
		shopList.get(2).setPolygon(polygon2);
		
		polygon3.add(new LatLng(56.298892, 44.104364));
		polygon3.add(new LatLng(56.3197956, 44.0655125));
		polygon3.add(new LatLng(56.273547, 44.0333448));
		polygon3.add(new LatLng(56.271144, 44.090996));
		polygon3.add(new LatLng(56.2846025, 44.12010384));
		shopList.add(new Shop(new LatLng(56.290592, 44.073032), "Индиго"));
		shopList.get(3).setPolygon(polygon3);
		
		polygon4.add(new LatLng(56.334366, 43.973927));
		polygon4.add(new LatLng(56.308853, 43.950325));
		polygon4.add(new LatLng(56.326897, 43.818573));
		polygon4.add(new LatLng(56.345783, 43.81643));
		polygon4.add(new LatLng(56.363712, 43.8596));
		shopList.add(new Shop(new LatLng(56.325794, 43.958060), "Аврора"));
		shopList.get(4).setPolygon(polygon4);
	}
	
	public ArrayList<Shop> getShopList() {
		return this.shopList;
	}
}
