package br.com.OrderProject.OrderProject.api.controller;

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