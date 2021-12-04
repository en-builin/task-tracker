package ru.pcs.tasktracker.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pcs.tasktracker.dto.ReportParamsForm;
import ru.pcs.tasktracker.services.ReportsService;
import ru.pcs.tasktracker.services.UsersService;

import java.time.LocalDate;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 06.12.2021 in project task-tracker
 */
@Controller
@RequestMapping("/report")
@RequiredArgsConstructor()
public class ReportController {

    private final UsersService usersService;
    private final ReportsService reportsService;

    @GetMapping
    public String getReportsPage(Authentication authentication, Model model) {

        ReportParamsForm reportParams = ReportParamsForm.builder()
                .dateFrom(LocalDate.now().withDayOfMonth(1))
                .dateTo(LocalDate.now().withDayOfMonth(1).plusMonths(1).minusDays(1))
                .build();

        model.addAttribute("reportParams", reportParams);
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("reportData", reportsService.getAllData(
                    reportParams.getDateFrom(), reportParams.getDateTo()));
        } else {
            model.addAttribute("reportData", reportsService.getAllData(
                    authentication.getName(), reportParams.getDateFrom(), reportParams.getDateTo()));
        }
        return "report";
    }

    @PostMapping()
    public String showReport(Authentication authentication, ReportParamsForm reportParams, Model model) {

        model.addAttribute("reportParams", reportParams);
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("reportData", reportsService.getAllData(
                    reportParams.getDateFrom(), reportParams.getDateTo()));
        } else {
            model.addAttribute("reportData", reportsService.getAllData(
                    authentication.getName(), reportParams.getDateFrom(), reportParams.getDateTo()));
        }

        return "report";
    }
}
