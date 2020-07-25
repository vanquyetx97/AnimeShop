package application.controller.web;

import application.data.model.User;
import application.data.service.UserService;
import application.model.viewmodel.common.ProductVM;
import application.model.viewmodel.user.ChangePasswordVM;
import application.model.viewmodel.user.UserDetailVM;
import application.model.viewmodel.user.UserVM;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(path = "/user")
public class UserController extends BaseController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/detail")
    public String getUserDetail(@Valid @ModelAttribute("productname") ProductVM productName,
                                Model model,
                                HttpServletResponse response,
                                HttpServletRequest request,
                                final Principal principal) {


        /**
         * check cookie to create cart guid
         */
        this.checkCookie(response,request,principal);

        UserDetailVM vm = new UserDetailVM();

        UserVM userVM = new UserVM();

        String  username = SecurityContextHolder.getContext().getAuthentication().getName();

        User userEntity = userService.findUserByUsername(username);

        userVM.setAddress(userEntity.getAddress());
        userVM.setAvatar(userEntity.getAvatar());
        userVM.setEmail(userEntity.getEmail());
        userVM.setGender(userEntity.getGender());
        userVM.setName(userEntity.getName());
        userVM.setPhoneNumber(userEntity.getPhoneNumber());

        vm.setLayoutHeaderVM(this.getLayoutHeaderVM());

        model.addAttribute("user",userVM);
        model.addAttribute("vm",vm);

        return "/user-detail";
    }


    @PostMapping("/update")
    public String updateUser(@Valid @ModelAttribute("user") User user) {
        try {
            String username = SecurityContextHolder.getContext().getAuthentication().getName();

            User userEntity = userService.findUserByUsername(username);

            userEntity.setAddress(user.getAddress());
            userEntity.setAvatar(user.getAvatar());
            userEntity.setEmail(user.getEmail());
            userEntity.setGender(user.getGender());
            userEntity.setName(user.getName());
            userEntity.setPhoneNumber(user.getPhoneNumber());

            userService.updateUser(userEntity);

            return "redirect:/user/detail?updateSuccess";
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "redirect:/user/detail?updateFail";
    }

    @GetMapping("/change-password")
    public String changePassWord(@Valid @ModelAttribute("productname") ProductVM productName,
                                 Model model) {

        ChangePasswordVM changePasswordVM = new ChangePasswordVM();

        changePasswordVM.setLayoutHeaderVM(this.getLayoutHeaderVM());

        model.addAttribute("changePassword", changePasswordVM);
        return "/change-password";
    }

    @PostMapping("change-password")
    public String changePassWordPost(@Valid @ModelAttribute("changePassword") ChangePasswordVM password) {

        try {
            if(password.getCurrentPassword() != null && password.getNewPassword() != null && password.getConfirmPassword() != null) {
                String  username = SecurityContextHolder.getContext().getAuthentication().getName();

                User userEntity = userService.findUserByUsername(username);

                if(passwordEncoder.matches(password.getCurrentPassword(),userEntity.getPasswordHash()) == true) {
                    if(password.getNewPassword().equals(password.getConfirmPassword())) {
                        userEntity.setPasswordHash(passwordEncoder.encode(password.getNewPassword()));
                        userService.updateUser(userEntity);
                        return "redirect:/user/change-password?success";
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return "redirect:/user/change-password?fail";

    }


}
