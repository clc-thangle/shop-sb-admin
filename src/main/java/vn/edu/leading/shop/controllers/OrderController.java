package vn.edu.leading.shop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.models.OrderModel;
import vn.edu.leading.shop.services.CustomerService;
import vn.edu.leading.shop.services.EmployeeService;
import vn.edu.leading.shop.services.OrderService;
import vn.edu.leading.shop.services.ShipperService;

import javax.validation.Valid;

@Controller
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final ShipperService shipperService;

    public OrderController(OrderService orderService, CustomerService customerService, EmployeeService employeeService, ShipperService shipperService) {
        this.orderService = orderService;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.shipperService = shipperService;
    }

//    @GetMapping("/orders")
//    public String list(Model model) {
//        model.addAttribute("orders", orderService.findAll());
//        return "orders/list";
//    }

    @GetMapping("/admin/orders")
    public String orders(Model model)
    {
        model.addAttribute("orders",orderService.findAll());
        model.addAttribute("customers",customerService.findAll());
        model.addAttribute("employees",employeeService.findAll());
        model.addAttribute("shippers",shipperService.findAll());
        return "admin/pages/orders";
    }

//    @GetMapping("/orders/add")
//    public String add(Model model) {
//        model.addAttribute("orderModel", new OrderModel());
//        return "orders/form";
//    }
//
//    @GetMapping("/orders/{id}/edit")
//    public String edit(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("orderModel", orderService.findById(id));
//        return "orders/form";
//    }
//
//    @GetMapping("/orders/{id}")
//    public String viewDetail(@PathVariable("id") Long id, Model model) {
//        model.addAttribute("orderDetails", orderService.findAllOrderDetailById(id));
//        return "orderDetails/list";
//    }

    @PostMapping("/admin/orders")
    public String save(@Valid OrderModel order, BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            return "admin/pages/orders";
        }
        orderService.save(order);
        redirect.addFlashAttribute("successMessage", "Saved order successfully!");
        model.addAttribute("orders",orderService.findAll());
        model.addAttribute("customers",customerService.findAll());
        model.addAttribute("employees",employeeService.findAll());
        model.addAttribute("shippers",shipperService.findAll());
        return "admin/pages/orders";
    }

    @GetMapping("/admin/orders/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect,Model model) {
        if (orderService.delete(id)) {
            redirect.addFlashAttribute("successMessage", "Deleted order successfully!");
            model.addAttribute("orders",orderService.findAll());
            model.addAttribute("customers",customerService.findAll());
            model.addAttribute("employees",employeeService.findAll());
            model.addAttribute("shippers",shipperService.findAll());
            return "admin/pages/orders";
        } else {
            redirect.addFlashAttribute("successMessage", "Not found!!!");
            model.addAttribute("orders",orderService.findAll());
            model.addAttribute("customers",customerService.findAll());
            model.addAttribute("employees",employeeService.findAll());
            model.addAttribute("shippers",shipperService.findAll());
            return "admin/pages/orders";
        }
    }
}
