package pizza.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class SessionController {

	private static final Logger logger = LogManager.getLogger();
	
	@GetMapping("/")
	public String home(Model model, HttpSession session) {
		
		model.addAttribute("preferredLang", "ru");
		model.addAttribute("sessionId", session.getId());
		System.out.println("--------------------------SESSION-------------------------");
		logger.info("session", session.getId());
		System.out.println("--------------------------SESSION-------------------------");
		return "index.html";
	}
}
