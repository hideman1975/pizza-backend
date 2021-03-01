package pizza.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import pizza.model.ClientSession;

public class ChatWebSocketHandler extends TextWebSocketHandler{

	private final List<WebSocketSession> webSocketSessions = new ArrayList<WebSocketSession>();
	private final List<ClientSession> clientSessions = new ArrayList<ClientSession>();
	ClientSession removedClientSession;
//	public ChatWebSocketHandler() {
//	System.out.println("----------ChatWebSocketHandler--------------");	
//	}
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("Connectetion Established with ID: "+ session.getId());
		webSocketSessions.add(session);
		clientSessions.add(new ClientSession(session));
		System.out.println("webSocketSessions array length: " + webSocketSessions.size());
		System.out.println("clientSessions array length: " + clientSessions.size());
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("Message come: " + message);
		for(WebSocketSession webSocketSession : webSocketSessions) {
			webSocketSession.sendMessage(message);
		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("Session closed: " + session.getId());
		webSocketSessions.remove(session);
		clientSessions.remove(getClosedClientSession(session));
		removedClientSession = null;
	}

	public ClientSession getClosedClientSession(WebSocketSession wsSession) {
		
		clientSessions.forEach((k) -> {
			if (wsSession.getId() == k.getSessionId()) {
				System.out.println("Session Key = " + clientSessions.indexOf(k));
				removedClientSession = k;
			}
		});
		return removedClientSession;
	}
}
