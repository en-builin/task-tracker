package ru.pcs.tasktracker.reports;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgeniy Builin (en.builin@gmail.com)
 * Created on 04.12.2021 in project task-tracker
 */
@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    public static final String SQL_SELECT_ALL_DATA = "select projects.title as project_title, users.name as user_name, o1.hours\n" +
            "from (select project_id, assignee_email, sum(hours) as hours\n" +
            "      from tasks\n" +
            "      where finished_at >= ?\n" +
            "        and finished_at < ?\n" +
            "      group by project_id, assignee_email) AS o1\n" +
            "         left join projects on o1.project_id = projects.id\n" +
            "         left join users on o1.assignee_email = users.email\n" +
            "order by title, name";

    public static final String SQL_SELECT_ALL_TOTAL = "select sum(hours) as hours\n" +
            "      from tasks\n" +
            "      where finished_at >= ?\n" +
            "        and finished_at < ?";

    public static final String SQL_SELECT_DATA_BY_USER = "select projects.title as project_title, users.name as user_name, o1.hours\n" +
            "from (select project_id, assignee_email, sum(hours) as hours\n" +
            "      from tasks\n" +
            "      where finished_at >= ?\n" +
            "        and finished_at < ?\n" +
            "        and assignee_email = ?\n" +
            "      group by project_id, assignee_email) AS o1\n" +
            "         left join projects on o1.project_id = projects.id\n" +
            "         left join users on o1.assignee_email = users.email\n" +
            "order by title, name";

    public static final String SQL_SELECT_TOTAL_BY_USER = "select sum(hours) as hours\n" +
            "      from tasks\n" +
            "      where finished_at >= ?\n" +
            "        and finished_at < ?\n" +
            "        and assignee_email = ?";

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, Object> getAllData(LocalDate from, LocalDate to) {

        Map<String, Object> result = new HashMap<>();

        result.put("rows", jdbcTemplate.queryForList(SQL_SELECT_ALL_DATA,
                from, to.plusDays(1)));
        result.put("total", jdbcTemplate.queryForObject(SQL_SELECT_ALL_TOTAL, BigDecimal.class,
                from, to.plusDays(1)));

        return result;
    }

    @Override
    public Map<String, Object> getAllData(String userEmail, LocalDate from, LocalDate to) {
        Map<String, Object> result = new HashMap<>();

        result.put("rows", jdbcTemplate.queryForList(SQL_SELECT_DATA_BY_USER,
                from, to.plusDays(1), userEmail));
        result.put("total", jdbcTemplate.queryForObject(SQL_SELECT_TOTAL_BY_USER, BigDecimal.class,
                from, to.plusDays(1), userEmail));

        return result;
    }

//    private LocalDateTime toUTC(LocalDate localDate) {
//        return localDate.atStartOfDay().atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
//    }
}
