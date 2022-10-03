package com.techelevator.dao;

import com.techelevator.model.MasterSchedule;
import com.techelevator.model.StatusReport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcReportDao implements ReportDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReportDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<StatusReport> listStatusReport() {
        List<StatusReport> statusReports = new ArrayList<>();
        String sql = "SELECT " +
                "contract_name, " +
                "contract_id, " +
                "project_name, " +
                "project_id, " +
                "phase_item, " +
                "item_date, " +
                "item_tasks, " +
                "month_year, " +
                "phase, " +
                "item_description "+
                "FROM current_items_vw;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            statusReports.add(mapRowToStatusReport(results));
        }
        return statusReports;
    }

    @Override
    public List<MasterSchedule> listMaster() {
        List<MasterSchedule> masterSchedules = new ArrayList<>();
        String sql = "SELECT " +
                "contract_name, " +
                "contract_id, " +
                "project_name, " +
                "project_id, " +
                "phase, " +
                "start_dt, " +
                "end_dt, " +
                "duration_days " +
                "FROM schedule_vw;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next()) {
            masterSchedules.add(mapRowToMasterSchedule(results));
        }
        return masterSchedules;
    }

    private MasterSchedule mapRowToMasterSchedule(SqlRowSet rs) {
        MasterSchedule masterSchedule = new MasterSchedule();
        masterSchedule.setContractName(rs.getString("contract_name"));
        masterSchedule.setContractId(rs.getInt("contract_id"));
        masterSchedule.setProjectName(rs.getString("project_name"));
        masterSchedule.setProjectId(rs.getInt("project_id"));
        masterSchedule.setPhase(rs.getString("phase"));
        if (rs.getDate("start_dt") != null) {
            masterSchedule.setStart(rs.getDate("start_dt").toLocalDate());
        }
        if (rs.getDate("end_dt") != null) {
            masterSchedule.setEnd(rs.getDate("end_dt").toLocalDate());
        }
        masterSchedule.setDurationDays(rs.getInt("duration_days"));
        return masterSchedule;
    }

    private StatusReport mapRowToStatusReport(SqlRowSet rs){
        StatusReport statusReport = new StatusReport();
        statusReport.setContractName(rs.getString("contract_name"));
        statusReport.setContractId(rs.getInt("contract_id"));
        statusReport.setProjectName(rs.getString("project_name"));
        statusReport.setProjectId(rs.getInt("project_id"));
        statusReport.setPhaseItem(rs.getInt("phase_item"));
        if (rs.getDate("item_date") != null) {
            statusReport.setItemDate(rs.getDate("item_date").toLocalDate());
        }
        statusReport.setItemTasks(rs.getInt("item_tasks"));
        if (rs.getDate("month_year") != null) {
            statusReport.setMonthYear(rs.getDate("month_year").toLocalDate());
        }
        statusReport.setPhase(rs.getString("phase"));
        statusReport.setItemDescription(rs.getString("item_description"));
        return statusReport;
    }

}
