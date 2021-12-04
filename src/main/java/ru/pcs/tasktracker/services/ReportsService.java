package ru.pcs.tasktracker.services;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 06.12.2021 in project task-tracker
 */
public interface ReportsService {

    Map<String, Object> getAllData(LocalDate from, LocalDate to);

    Map<String, Object> getAllData(String userEmail, LocalDate from, LocalDate to);
}
