package gruppe3.adventurexp.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {

    private static final String SHARED_USERNAME = "admin";
    private static final String SHARED_PASSWORD = "1234";

    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, HttpSession session) {
        if (isValidLogin(username, password)) {
            session.setAttribute("loggedIn", true);
            return ResponseEntity.ok("Logged in");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Wrong username or password");
    }

    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Object loggedIn = session.getAttribute("loggedIn");
        if (loggedIn == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Not logged in");
        }

        return ResponseEntity.ok("Logged in");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    private boolean isValidLogin(String username, String password) {
        return SHARED_USERNAME.equals(username) && SHARED_PASSWORD.equals(password);
    }
}

