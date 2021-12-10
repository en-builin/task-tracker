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
import ru.pcs.tasktracker.utils.WebUtils;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 06.12.2021 in project task-tracker
 */
@Controller
@RequestMapping(WebUtils.URL_REPORT)
@RequiredArgsConstructor()
public class ReportController {

    private final UsersService usersService;
    private final ReportsService reportsService;

    @GetMapping
    public String getReportsPage(Authentication authentication, Model model) {

        ReportParamsForm reportParams = ReportParamsForm.builder()
                .dateFrom(LocalDate.now().with(firstDayOfMonth()))
                .dateTo(LocalDate.now().with(lastDayOfMonth()))
                .build();

        model.addAttribute("reportParams", reportParams);
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));

        addReportDataToModel(authentication, model, reportParams);

        return WebUtils.VIEW_REPORT;
    }

    @PostMapping()
    public String showReport(Authentication authentication, ReportParamsForm reportParams, Model model) {

        model.addAttribute("reportParams", reportParams);
        model.addAttribute("currentUser", usersService.getUserByEmail(authentication.getName()));

        addReportDataToModel(authentication, model, reportParams);

        return WebUtils.VIEW_REPORT;
    }

    private void addReportDataToModel(Authentication authentication, Model model, ReportParamsForm reportParams) {

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
            model.addAttribute("reportData", reportsService.getAllData(
                    reportParams.getDateFrom(), reportParams.getDateTo()));
        } else {
            model.addAttribute("reportData", reportsService.getDataByUser(
                    authentication.getName(), reportParams.getDateFrom(), reportParams.getDateTo()));
        }
    }
}
