package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import vn.edu.leading.shop.models.ConfigModel;
import vn.edu.leading.shop.repositories.ConfigRepository;
import vn.edu.leading.shop.services.CategoryService;
import vn.edu.leading.shop.services.ProductService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/")
    public String coffee(Model model) {
        Map<String, String> configs = configRepository.findAll().stream().collect(Collectors.toMap(ConfigModel::getName, ConfigModel::getValue));
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("products",productService.findAll());
        model.addAttribute("configs", configs);
        return "coffee";
    }

    @GetMapping("/coffee-detail/{id}")
    public String coffeeDetails(@PathVariable("id") Long id, Model model) {
        Map<String, String> configs = configRepository.findAll().stream().collect(Collectors.toMap(ConfigModel::getName, ConfigModel::getValue));
        model.addAttribute("productModel",productService.findById(id));
        model.addAttribute("configs", configs);
        return "coffee-detail";
    }

    @GetMapping("/cart")
    public String cart( Model model) {
        Map<String, String> configs = configRepository.findAll().stream().collect(Collectors.toMap(ConfigModel::getName, ConfigModel::getValue));
        model.addAttribute("productModel",productService.findAll());
        model.addAttribute("configs", configs);
        return "cart";
    }
}
