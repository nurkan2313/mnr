package kg.core.mnr.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@AllArgsConstructor
@Controller
@RequestMapping("/report")
public class ReportController {

    @GetMapping
    public String report() {
        return "/otchet/reports";
    }

}
