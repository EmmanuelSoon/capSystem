package team2.capSystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin")
public class AdminController {
    

	@RequestMapping(value="/dashboard")
    public String displayDashboard() {
        return "/admin/adminDashboard";
    }
    
}
