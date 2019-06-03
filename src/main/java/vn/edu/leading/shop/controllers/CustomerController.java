package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.models.CustomerModel;
import vn.edu.leading.shop.services.CustomerService;

import javax.validation.Valid;

@Controller
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public String list(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "customers/list";
    }

    @GetMapping("/admin/customers")
    public String customers(Model model) {
        model.addAttribute("customers", customerService.findAll());
        return "admin/pages/customers";
    }

//    @GetMapping("customers/search")
//    public String search(@RequestParam("term") String term, Model model) {
//        if (StringUtils.isEmpty(term)) {
//            return "redirect:/customers";
//        }
//        model.addAttribute("customers", customerService.search(term));
//        return "customers/list";
//    }
//
//    @GetMapping("/customers/add")
//    public String add(Model model) {
//        model.addAttribute("customerModel", new CustomerModel());
//        return "customers/form";
//    }
//
//    @GetMapping("/customers/{id}/edit")
//    public String edit(@PathVariable("id") Long id, Model model) {
//        model.addAttribute(customerService.findById(id));
//        return "admin/pages/customers";
//    }

    @PostMapping("/admin/customers")
    public String save(@Valid CustomerModel customer,Model model) {
        customerService.save(customer);
        model.addAttribute("customers", customerService.findAll());
        return "admin/pages/customers";
    }

    @GetMapping("/admin/customers/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect,Model model) {
        if (customerService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted customer successfully!");
            model.addAttribute("customers", customerService.findAll());
            return "admin/pages/customers";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            model.addAttribute("customers", customerService.findAll());
            return "admin/pages/customers";
        }
    }
}
