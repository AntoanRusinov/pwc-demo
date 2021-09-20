package com.pwc.project.controller;

import com.pwc.project.service.RoutingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/routing")
public class RoutingController {

    @Autowired
    private RoutingService routingService;

    @GetMapping("/{origin}/{destination}")
    private List<String> findRoute(@PathVariable("origin") String origin,
                                   @PathVariable("destination") String destination) {
        return routingService.findRoute(origin, destination);
    }

}