package kg.core.mnr.controller;

import kg.core.mnr.models.breadcrumbs.Breadcrumb;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Controller
public class ReportController {

//    @GetMapping("report")
//    public String report(Model model) {
//        List<Breadcrumb> breadcrumbs = new ArrayList<>();
//        breadcrumbs.add(new Breadcrumb("/dashboard", "дешборд"));
//        breadcrumbs.add(new Breadcrumb("/report", "отчеты"));
//
//        model.addAttribute("breadcrumbs", breadcrumbs);
//        model.addAttribute("currentPage", "отчеты");
//
//        return "/otchet/reports";
//    }
}
