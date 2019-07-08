package vn.edu.leading.shop.controllers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.edu.leading.shop.models.RoleModel;
import vn.edu.leading.shop.models.UserModel;
import vn.edu.leading.shop.repositories.BaseRepository;
import vn.edu.leading.shop.repositories.RoleRepository;
import vn.edu.leading.shop.services.BaseService;
import vn.edu.leading.shop.services.MailService;
import vn.edu.leading.shop.services.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    private final UserService userService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailService mailService;

    public UserController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder, MailService mailService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @GetMapping("/user/new")
    public String list(UserModel userModel) {
        UserModel newUser = new UserModel();
        newUser.setUsername(userModel.getUsername());
        newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
        RoleModel roleModel = roleRepository.findByName("ROLE_USER");
        Set<RoleModel> roleModels = new HashSet<>();
        roleModels.add(roleModel);
        newUser.setRoleModels(roleModels);
        userService.save(newUser);
        return "thanh cong" + userModel.getUsername() + " " + userModel.getPassword() + " sau khi ma hoa " + passwordEncoder.encode(userModel.getPassword());
    }

    @PostMapping("/register")
    public String register(@Valid UserModel userModel, BindingResult result) throws Exception {
//        if (result.hasErrors()) {
//            return "redirect:/register";
//        }

        userService.register(userModel);
        mailService.sendMail(userModel);
        return "redirect:/login";
    }

    @GetMapping("/admin/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("roles", roleRepository.findAll());

        return "admin/pages/users";
    }

    @GetMapping("/admin/users/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes,Model model)
    {
        if(userService.delete(id))
        {
            redirectAttributes.addFlashAttribute("successMessage", "Deleted product successfully!");
            model.addAttribute("users",userService.findAll());
            model.addAttribute("roles", roleRepository.findAll());
            return "admin/pages/users";
        }
        else
        {
            redirectAttributes.addFlashAttribute("successMessage", "Not found!!!");
            model.addAttribute(userService.findAll());
            return "admin/pages/users";
        }
    }

    @PostMapping("/admin/users")
    public String save(@Valid UserModel userModel, BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model )
    {
        if(bindingResult.hasErrors())
        {
            return "/admin/pages/users";
        }
        userService.save(userModel);
        redirectAttributes.addFlashAttribute("successMessage", "Saved product successfully");
        model.addAttribute("users",userService.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        return "/admin/pages/users";
    }

}
