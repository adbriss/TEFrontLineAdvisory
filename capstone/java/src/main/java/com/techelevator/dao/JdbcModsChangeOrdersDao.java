package com.techelevator.dao;

import com.techelevator.model.ModChangeOrder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcModsChangeOrdersDao implements ModsChangeOrdersDao {


    private final JdbcTemplate jdbcTemplate;

    public JdbcModsChangeOrdersDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ModChangeOrder> listAllModsChangeOrders() {
        String sql = "SELECT id, " +
                "mod_co_name, " +
                "project_id, " +
                "type, " +
                "court_date, " +
                "schedule_impacted, " +
                "why_sched_impact, " +
                "how_sched_impact, " +
                "budget_impacted, " +
                "why_budgt_impact, " +
                "how_budgt_impact, " +
                "why_submit, " +
                "why_two, " +
                "why_three, " +
                "why_four, " +
                "why_five, " +
                "approved, " +
                "approv_datetm " +
                "FROM mods_changes;";
        SqlRowSet rs = this.jdbcTemplate.queryForRowSet(sql);
        List<ModChangeOrder> modsChangeOrderDaos = new ArrayList<>();
        while (rs.next()) {
            modsChangeOrderDaos.add(mapRowToModsCO(rs));
        }
        return modsChangeOrderDaos;
    }

    @Override
    public void addToModsCO(int projectId) {
        String sql = "INSERT INTO mods_changes " +
                "(mod_co_name, project_id, type, court_date, schedule_impacted, why_sched_impact, how_sched_impact, " +
                "budget_impacted, why_budgt_impact, how_budgt_impact, why_submit, why_two, why_three, why_four, why_five, " +
                "approved, approv_datetm) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        jdbcTemplate.update(sql, projectId);
    }

    @Override
    public void updateModsCO(ModChangeOrder modChangeOrder) {
        String sql = "UPDATE mods_changes " +
                "SET mod_co_name = ?," +
                " project_id = ?, " +
                "type = ?, " +
                "court_date = ?, " +
                "schedule_impacted = ?, " +
                "why_sched_impact = ?, " +
                "how_sched_impact = ?, " +
                "budget_impacted = ?, " +
                "why_budgt_impact = ?, " +
                "how_budgt_impact = ?, " +
                "why_submit = ?, " +
                "why_two = ?, " +
                "why_three = ?, " +
                "why_four = ?, " +
                "why_five = ?, " +
                "approved = ?, " +
                "approv_datetm = ?" +
                "WHERE id = ?;";
        jdbcTemplate.update(sql, modChangeOrder.getModCoName(), modChangeOrder.getProjectId(), modChangeOrder.getType(),
                modChangeOrder.getCourtDate(), modChangeOrder.isScheduleImpacted(), modChangeOrder.getWhySchedImpact(),
                modChangeOrder.getHowSchedImpact(), modChangeOrder.getWhySubmit(), modChangeOrder.getWhyTwo(),
                modChangeOrder.getWhyThree(), modChangeOrder.getWhyFour(), modChangeOrder.getWhyFive(),
                modChangeOrder.isApproved(), modChangeOrder.getApproveDateTm(), modChangeOrder.getId());
    }

    @Override
    public void deleteModsChangeCO(int id) {
        String sql = "DELETE FROM mods_changes WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    private ModChangeOrder mapRowToModsCO(SqlRowSet rs) {
        ModChangeOrder modChangeOrder = new ModChangeOrder();
        modChangeOrder.setId(rs.getInt("id"));
        modChangeOrder.setModCoName(rs.getString("mod_co_name"));
        modChangeOrder.setProjectId(rs.getInt("project_id"));
        modChangeOrder.setType(rs.getString("type"));
        modChangeOrder.setCourtDate(rs.getDate("court_date").toLocalDate());
        modChangeOrder.setScheduleImpacted(rs.getBoolean("schedule_impacted"));
        modChangeOrder.setWhySchedImpact(rs.getString("why_sched_impact"));
        modChangeOrder.setHowSchedImpact(rs.getString("how_sched_impact"));
        modChangeOrder.setBudgetImpacted(rs.getBoolean("budget_impacted"));
        modChangeOrder.setWhyBudgetImpact(rs.getString("why_budget_impact"));
        modChangeOrder.setHowBudgetImpact(rs.getString("why_submit"));
        modChangeOrder.setWhyThree(rs.getString("why_three"));
        modChangeOrder.setWhyFour(rs.getString("why_four"));
        modChangeOrder.setWhyFive(rs.getString("why_five"));
        modChangeOrder.setApproved(rs.getBoolean("approved"));
        if (rs.getTimestamp("aprrov_datetm") != null) {
            modChangeOrder.setApproveDateTm(rs.getTimestamp("last_modified").toLocalDateTime());
        }
        return modChangeOrder;
    }
}
