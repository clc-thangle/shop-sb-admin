package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.models.CategoryModel;
import vn.edu.leading.shop.services.CategoryService;

import javax.validation.Valid;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "admin/pages/categories";
    }

    @PostMapping("/admin/categories")
    public String save(@Valid CategoryModel category, BindingResult result, RedirectAttributes redirect,Model model) {
        if (result.hasErrors()) {
            return "admin/pages/categories";
        }
        categoryService.save(category);
        redirect.addFlashAttribute("successMessage", "Saved category successfully!");
        model.addAttribute("categories",categoryService.findAll());
        return "admin/pages/categories";
    }

    @GetMapping("/admin/categories/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect,Model model) {
        if (categoryService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted category successfully!");
            model.addAttribute("categories",categoryService.findAll());
            return "admin/pages/categories";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            model.addAttribute("categories",categoryService.findAll());
            return "admin/pages/categories";
        }
    }
}
