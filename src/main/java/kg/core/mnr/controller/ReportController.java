package kg.core.mnr.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@AllArgsConstructor
@Controller
public class ReportController {

    @GetMapping("report")
    public String report() {
        return "/otchet/reports";
    }
}
