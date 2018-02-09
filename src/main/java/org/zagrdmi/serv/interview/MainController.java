package org.zagrdmi.serv.interview;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zagrdmi.serv.interview.entity.Service;


@Controller
public class MainController {

    @Autowired
    private ServicesRepository servicesRepository;

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("services", servicesRepository.findAll());
        return "main";
    }

    @GetMapping("/details/{id}")
    public String details(Model model, @PathVariable("id") Long serviceId) {
        Service service = servicesRepository.findOne(serviceId);
        model.addAttribute("service", service);
        return "details";
    }
}
