package kg.core.mnr.controller;

import kg.core.mnr.exception.EmailOrPhoneAlreadyExists;
import kg.core.mnr.models.dto.requests.LoginRequest;
import kg.core.mnr.models.dto.requests.RegisterRequest;
import kg.core.mnr.models.dto.response.UserResponseDto;
import kg.core.mnr.models.entity.Users;
import kg.core.mnr.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/auth")
    public String auth() {
        return "user/auth-login";
    }

    @GetMapping("/logout-success")
    public String logoutPage() {
        return "user/auth-login"; // Возвращает страницу после выхода
    }

    @PostMapping("/auth")
    public ModelAndView auth(@ModelAttribute LoginRequest loginRequest, Model model) {
        log.debug("Login request - Email or Phone: {}", loginRequest.getEmailOrPhone());
        Users users = userService.auth(loginRequest);

        if (users != null) {
            log.debug("Login success - Email or Phone: {}", loginRequest.getEmailOrPhone());
            return new ModelAndView("redirect:/");  // Перенаправляем на профиль после успешного входа
        } else {
            model.addAttribute("error", "Invalid username or password");
            return new ModelAndView("/error/error");  // Возвращаем страницу логина с сообщением об ошибке
        }
    }

    @GetMapping("/registration")
    public String registerPage() {
        return "user/auth-register";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute RegisterRequest request, Model model) {
        try {
            UserResponseDto register = userService.register(request);// Вызываем сервис для регистрации
            if (register == null) {
                model.addAttribute("error", "Invalid username or password");
                return "error";
            }
            return "redirect:/auth";  // Перенаправляем на страницу логина после успешной регистрации
        } catch (EmailOrPhoneAlreadyExists e) {
            model.addAttribute("error", "Email или телефон уже зарегистрированы.");
            return "/error/error";  // Возвращаем страницу регистрации с ошибкой
        }
    }

}
