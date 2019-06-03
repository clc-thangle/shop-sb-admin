package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.leading.shop.models.ConfigModel;
import vn.edu.leading.shop.repositories.ConfigRepository;
import vn.edu.leading.shop.services.CategoryService;
import vn.edu.leading.shop.services.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller

public class IndexController {

    private final ProductService productService;

    private final CategoryService categoryService;

    private final ConfigRepository configRepository;

    public IndexController(ProductService productService, CategoryService categoryService, ConfigRepository configRepository) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.configRepository = configRepository;
    }

    @GetMapping("/home")
    public String checkout() {
        return "sublime/checkout";
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",productService.findAll());
        return "sublime/index";
    }

    @GetMapping("/")
    public String coffee(Model model) {
        List<ConfigModel> configList = configRepository.findAll();
        Map<String, String> configs = new HashMap<>();
        for(ConfigModel configModel: configList){
            configs.put(configModel.getName(), configModel.getValue());
        }
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",productService.findAll());
        model.addAttribute("configs", configs);
        return "coffee";
    }
}
