package pizza.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import pizza.handler.ChatWebSocketHandler;
import pizza.handler.ShopWebSocketHandler;

@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

	private final static String CHAT_ENDPOINT = "/chat";
	private final static String SHOP_ENDPOINT = "/shop";
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(getChatWebSocketHandler(), CHAT_ENDPOINT).setAllowedOrigins("*");
		webSocketHandlerRegistry.addHandler(getShopWebSocketHandler(), SHOP_ENDPOINT).setAllowedOrigins("*");
	}
	
	@Bean
	public WebSocketHandler getChatWebSocketHandler() {
		System.out.println("-----------ChatWebSocketHandler Bean------------");
		return new ChatWebSocketHandler();
	}
	
	@Bean
	public WebSocketHandler getShopWebSocketHandler() {
		System.out.println("-----------ShopWebSocketHandler Bean------------");
		return new ShopWebSocketHandler();
	}

}
