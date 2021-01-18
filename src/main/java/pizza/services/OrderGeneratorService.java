package pizza.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.maps.model.LatLng;

import pizza.models.GoogleRoute;
import pizza.models.Order;
import pizza.models.Shop;


@Service
public class OrderGeneratorService {
	
//	@Autowired
//	private static ShopService shopService;
	
	static ArrayList<LatLng> orderArray = new ArrayList<>();
	static ArrayList<LatLng> leafLetRoute = new ArrayList<>();
	//static ArrayList<String> pathArray= new ArrayList<>();
	static ArrayList<LatLng> LatLngCache = new ArrayList<>();
	static HashMap<Integer, ArrayList<LatLng>> RandCache = new HashMap<>();
	static ArrayList<ArrayList<LatLng>> PathCache = new ArrayList<>();
	static private Order lastOrder;
	static Timer timer = new Timer();
	public static GoogleRoute route = new GoogleRoute();
	static String start = "56.3262830,43.9579510";
	static String leafStart = "43.9579510,56.3262830";
	static String leafDestination = "43.927855,56.287643";
	//static ArrayList<Shop> shopList = shopService.getShopList();
	static ArrayList<LatLng> shopList = new ArrayList<>();
	
	static TimerTask generateOrder = new TimerTask() {
		
		@Override
		public void run() {
			timer.cancel();
			timer.purge();
			
			// Получаем случайный период времени
			int randomNum = ThreadLocalRandom.current().nextInt(1000, 3000 + 1);
			System.out.println("-----Rundom Period ---------" + randomNum);
			// Повторяем запрос нового таска
			repeatTask(randomNum);
		}
	};
	
	static {
		init();
		// Первый толчок маятника
		timer.schedule(generateOrder, 2000, 2000);
		}
	
	public static void repeatTask(int interval) {
		timer = new Timer();
		generateOrder = new TimerTask() {
	
			@Override
			public void run() {
				timer.cancel();
				timer.purge();
				
				int randomNum = ThreadLocalRandom.current().nextInt(1000, 3000 + 1);
				int randomOrder = ThreadLocalRandom.current().nextInt(0, 44);
								
				System.out.println("-----Rundom Period ---------" + randomNum);
				System.out.println("-----Rundom Order ---------" + orderArray.get(randomOrder).toString());

				if (RandCache.containsKey(randomOrder)) {
					System.out.println("----- Уже есть в кэше ---------");
					lastOrder = new Order (orderArray.get(randomOrder), "Заказ из кеша", RandCache.get(randomOrder));
				} else {
					// Вынести в отдельную функцию
					System.out.println("----- Ещё нет в кэше ---------");
				try {
					String longitude = Double.toString(orderArray.get(randomOrder).lng);
					String latitude = Double.toString(orderArray.get(randomOrder).lat);
					ArrayList<LatLng> pathPoint = new ArrayList<>();
					
					
					JSONArray nearestPizzeria = route.getNearestPizzeria(longitude + "," + latitude);
					nearestPizzeria.remove(0);
					Double nearest = 100000.0001;
					int nearestIndex = 0;
					for (int i = 0; i < nearestPizzeria.size(); i++) {
						Object item =  nearestPizzeria.get(i);
						Double lastItem = Double.parseDouble(item.toString());
						System.out.println("-----Item ---------" + lastItem);
						if (nearest > lastItem) {
							nearest = lastItem;
							nearestIndex = i;
					}
					}
					System.out.println("-----Nearest Table ---------" + nearestPizzeria);
					System.out.println("-----Nearest Shop Index ---------" + nearestIndex);
					
					//Выссчитать магазин отправления
					
					LatLng nearestLatLng = shopList.get(nearestIndex);
					String nLat = Double.toString(nearestLatLng.lat);
					String nLng = Double.toString(nearestLatLng.lng);
					
					JSONArray openStreetPath = route.createRoute(
							nLng + "," + nLat, longitude + "," + latitude);
					System.out.println("-----Nearest LangLong ---------" + nearestLatLng.toString());
//					JSONArray openStreetPath = route.createRoute(
//							leafStart, longitude + "," + latitude);
									
					openStreetPath.forEach(k ->	{
						Double lng = (Double) ((ArrayList) k).get(0);
						Double lat = (Double) ((ArrayList) k).get(1);;
						pathPoint.add(new LatLng(lat, lng));
						}
							);
				lastOrder = new Order (orderArray.get(randomOrder), "Заказ новый", pathPoint);
				RandCache.put(randomOrder, pathPoint);
				} catch (IOException e) {
					System.out.println(e);
					e.printStackTrace();
				}
			}	
			  // Вызывает сам себя через интервал
				repeatTask(randomNum);
			}
		};
		timer.schedule(generateOrder, interval, interval);
	}
	// Потом уедет в базу данных
	public static void init() {
		orderArray.add(new LatLng(56.329631, 43.925335));
		orderArray.add(new LatLng(56.334861, 43.938580));
		orderArray.add(new LatLng(56.336401, 43.915099));
		orderArray.add(new LatLng(56.316411, 43.947608));
		orderArray.add(new LatLng(56.310108, 43.945982));
		orderArray.add(new LatLng(56.301066, 43.940759));
		orderArray.add(new LatLng(56.301346, 43.928296));
		orderArray.add(new LatLng(56.288289, 43.931563));
		orderArray.add(new LatLng(56.289259, 43.949086));
		orderArray.add(new LatLng(56.284554, 43.933340));
		orderArray.add(new LatLng(56.317627, 43.923266));
		orderArray.add(new LatLng(56.313916, 43.891772));
		orderArray.add(new LatLng(56.309125, 43.987150));
		orderArray.add(new LatLng(56.304391, 44.020506));
		
		orderArray.add(new LatLng(56.304200, 44.096634));
		orderArray.add(new LatLng(56.293640, 44.093511));
		orderArray.add(new LatLng(56.309986, 44.057345));
		orderArray.add(new LatLng(56.302465, 44.069054));
		orderArray.add(new LatLng(56.286115, 44.041994));
		orderArray.add(new LatLng(56.283944, 43.986834));
		
		orderArray.add(new LatLng(56.230648, 43.953269));
		orderArray.add(new LatLng(56.217892, 43.950407));
		orderArray.add(new LatLng(56.233691, 43.980589));
		orderArray.add(new LatLng(56.258174, 43.928291));
		orderArray.add(new LatLng(56.269614, 43.875212));
		orderArray.add(new LatLng(56.287417, 43.891864));
		
		orderArray.add(new LatLng(56.281104, 44.049407));
		orderArray.add(new LatLng(56.282303, 44.012453));
		orderArray.add(new LatLng(56.275622, 43.992744));
		orderArray.add(new LatLng(56.267569, 43.983814));
		orderArray.add(new LatLng(56.282132, 44.012145));
		orderArray.add(new LatLng(56.269968, 43.941317));
		
		orderArray.add(new LatLng(56.221376, 43.967711));
		orderArray.add(new LatLng(56.217421, 43.948387));
		orderArray.add(new LatLng(56.211927, 43.956669));
		orderArray.add(new LatLng(56.207311, 43.990585));
		orderArray.add(new LatLng(56.224781, 43.958838));
		orderArray.add(new LatLng(56.275499, 44.073798));
		
		orderArray.add(new LatLng(56.285591, 44.100813));
		orderArray.add(new LatLng(56.276048, 44.071234));
		orderArray.add(new LatLng(56.285152, 44.090362));
		orderArray.add(new LatLng(56.285152, 44.090362));
		orderArray.add(new LatLng(56.285152, 44.090362));
		orderArray.add(new LatLng(56.292120, 44.044934));
		
		shopList.add(new LatLng(56.287643, 43.927855));
		shopList.add(new LatLng(56.309084, 43.986268));
		shopList.add(new LatLng(56.235055, 43.951469));
		shopList.add(new LatLng(56.290592, 44.073032));
		shopList.add(new LatLng(56.325794, 43.958060));
	}
	
	public Order getLastOrder() {
		return lastOrder;
	}
}
