package pizza.model;

import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.maps.model.LatLng;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import pizza.services.SphericalUtil;

import com.squareup.okhttp.HttpUrl.Builder;


public class GoogleRoute {
public OkHttpClient client = new OkHttpClient();
	
	JSONObject jsonObject;
		
	// РџРѕР»СѓС‡РёС‚СЊ РјР°СЂС€СЂСѓС‚ РёР· СЃС‚РѕСЂРѕРЅРЅРµРіРѕ СЃРµСЂРІРёСЃР°
	
	public  JSONArray createRoute(String start, String finish) throws IOException {
	
	Builder httpUrl = new HttpUrl.Builder()
			.scheme("https")
			.host("router.project-osrm.org")
			.addPathSegment("route")
			.addPathSegment("v1")
			.addPathSegment("driving")
			.addPathSegment(start + ";" + finish)
			.addQueryParameter("overview", "false")
			.addQueryParameter("alternatives", "true")
			.addQueryParameter("steps", "false")
			.addQueryParameter("geometries", "geojson")
			.addQueryParameter("overview", "full")
			.addQueryParameter("hints", ";");
	
			
	Request request = new Request.Builder()
			.url(httpUrl.toString())
			.get()
			.build();
	Response response = client.newCall(request).execute();
	String body = response.body().string();
	System.out.println("-----Open Street Response ---------" + body);
	JSONParser jsonParser = new JSONParser();
	
	try {
		jsonObject = (JSONObject) jsonParser.parse(body);
	} catch (ParseException e) {
		e.printStackTrace();
	} 
	
	JSONArray routes = (JSONArray) jsonObject.get("routes");
	JSONObject route = (JSONObject) routes.get(0);
	JSONObject geometry = (JSONObject) route.get("geometry");
	JSONArray coordinates = (JSONArray) geometry.get("coordinates");
		
	return coordinates;
}
	
	// РџРѕР»СѓС‡РёС‚СЊ Р±Р»РёР¶Р°Р№С€СѓСЋ РїРёС†РёСЂРёСЋ
	
	public  JSONArray getNearestPizzeria(String start) throws IOException {
		Builder httpUrl = new HttpUrl.Builder()
				.scheme("https")
				.host("router.project-osrm.org")
				.addPathSegment("table")
				.addPathSegment("v1")
				.addPathSegment("driving")
				.addPathSegment(start + ";" + "43.927855,56.287643;43.986268,56.309084;43.951469,56.235055;44.073032,56.290592;43.958060,56.325794");
				
		Request request = new Request.Builder()
				.url(httpUrl.toString())
				.get()
				.build();
		Response response = client.newCall(request).execute();
		String body = response.body().string();
		System.out.println("-----Nearest Pizzeria Response ---------" + body);
		JSONParser jsonParser = new JSONParser();
		
		try {
			jsonObject = (JSONObject) jsonParser.parse(body);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
		JSONArray routes = (JSONArray) jsonObject.get("durations");
		JSONArray durations = (JSONArray) routes.get(0);
		//JSONObject geometry = (JSONObject) route.get("geometry");
		//JSONArray coordinates = (JSONArray) geometry.get("coordinates");
			
		return durations;
	}
	
	//Р�Р·РІР»РµРєР°РµС‚ РёР· Р·Р°С€РёС„СЂРѕРІР°РЅРЅРѕРіРѕ РјР°СЂС€СЂСѓС‚Р° РјР°СЃСЃРёРІ РµРіРѕ С‚РѕС‡РµРє
	
	public static ArrayList<LatLng> decodePoly(String encoded) {
	    ArrayList<LatLng> poly = new ArrayList<>();
	    int index = 0, len = encoded.length();
	    int lat = 0, lng = 0;

	    while (index < len) {
	        int b, shift = 0, result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lat += dlat;

	        shift = 0;
	        result = 0;
	        do {
	            b = encoded.charAt(index++) - 63;
	            result |= (b & 0x1f) << shift;
	            shift += 5;
	        } while (b >= 0x20);
	        int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
	        lng += dlng;

	        LatLng p = new LatLng((((double) lat / 1E5)),
	                (((double) lng / 1E5)));
	        poly.add(p);
	    }

	    return poly;
	}

	// Р”СЂРѕР±РёС‚ РѕС‚СЂРµР·РѕРє РЅР° СѓС‡Р°СЃС‚РєРё РїРѕ 5 РјРµС‚СЂРѕРІ
	
	public ArrayList<LatLng> getRoutePoints(LatLng start, LatLng end)  {
	    
		double distance = SphericalUtil.computeDistanceBetween(start, end);
		double div = Math.floor(distance / 10); //РЅР° СЃРєРѕР»СЊРєРѕ СѓС‡Р°СЃС‚РєРѕРІ РЅСѓР¶РЅРѕ СЂР°Р·Р±РёС‚СЊ РѕС‚СЂРµР·РѕРє
	    double dX =  end.lat - start.lat;
	    double dY =  end.lng - start.lng;
	    double stepX = dX /div;
	    double stepY = dY /div;
	    ArrayList<LatLng> arr = new ArrayList<>();
	         for (int i = 0; i < div; i++) {
	           	arr.add(new LatLng(start.lat+stepX*i, start.lng+stepY*i));
	        }
	        return arr;
	  } 
}
