package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import vn.edu.leading.shop.models.ConfigModel;
import vn.edu.leading.shop.repositories.ConfigRepository;

import java.util.List;

@Controller
public class ConfigController {

    private final ConfigRepository configRepository;

    public ConfigController(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @GetMapping("/admin/config")
    public String gerAllConfig(Model model) {
        List<ConfigModel> configs = configRepository.findAll();
        model.addAttribute("configs", configs);
        return "admin/pages/config";
    }

    @GetMapping("/admin/config/{name}/delete")
    public String delete(Model model, @PathVariable String name) {
        configRepository.delete(configRepository.getOne(name));
        List<ConfigModel> configs = configRepository.findAll();
        model.addAttribute("configs", configs);
        return "admin/pages/config";
    }

    @PostMapping("/admin/config")
    public String save(ConfigModel configModel, Model model){
        configRepository.save(configModel);
        List<ConfigModel> configs = configRepository.findAll();
        model.addAttribute("configs", configs);
        return "admin/pages/config";
    }
}
