package br.com.OrderProject.OrderProject.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.OrderProject.OrderProject.domain.model.Menu;
import br.com.OrderProject.OrderProject.domain.service.MenuService;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public ResponseEntity<Menu> getMenu() {
        return ResponseEntity.ok(menuService.getMenu());
    }
}