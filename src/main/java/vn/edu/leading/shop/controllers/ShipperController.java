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
import vn.edu.leading.shop.models.ShipperModel;
import vn.edu.leading.shop.services.ShipperService;

import javax.validation.Valid;

@Controller
public class ShipperController {

    private final ShipperService shipperService;

    public ShipperController(ShipperService shipperService) {
        this.shipperService = shipperService;
    }

    @GetMapping("/shippers")
    public String list(Model model) {
        model.addAttribute("shippers", shipperService.findAll());
        return "shippers/list";
    }

    @GetMapping("/admin/shippers")
    public String shippers(Model model)
    {
        model.addAttribute("shippers",shipperService.findAll());
        return "admin/pages/shippers";
    }

//    @GetMapping("shippers/search")
//    public String search(@RequestParam("term") String term, Model model) {
//        if (StringUtils.isEmpty(term)) {
//            return "redirect:/shippers";
//        }
//        model.addAttribute("shippers", shipperService.search(term));
//        return "shippers/list";
//    }
//
//    @GetMapping("/shippers/add")
//    public String add(Model model) {
//        model.addAttribute("shipperModel", new ShipperModel());
//        return "shippers/form";
//    }

//    @GetMapping("/admin/shippers/{id}/edit")
//    public String edit(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("shipperModel", shipperService.findById(id));
//        return "admin/pages/shippers";
//    }

    @PostMapping("/admin/shippers")
    public String save(@Valid ShipperModel shipper, BindingResult result, RedirectAttributes redirect,Model model) {
        if (result.hasErrors()) {
            return "admin/pages/shippers";
        }
        shipperService.save(shipper);
        redirect.addFlashAttribute("successMessage", "Saved shipper successfully!");
        model.addAttribute("shippers", shipperService.findAll());
        return "admin/pages/shippers";
    }

    @GetMapping("/admin/shippers/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect,Model model) {
        if (shipperService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted shipper successfully!");
            model.addAttribute("shippers", shipperService.findAll());
            return "admin/pages/shippers";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            return "admin/pages/shippers";
        }
    }
}
