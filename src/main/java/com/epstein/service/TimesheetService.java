package com.epstein.service;

import com.epstein.entity.Timesheet;
import com.epstein.repository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.OrderBy;
import java.util.List;

@Service
public class TimesheetService {

    @Autowired private TimesheetRepository timesheetRepository;

    public Timesheet save(Timesheet timesheet) {
        return this.timesheetRepository.save(timesheet);
    }

    public List<Timesheet> getTimesheets() {
        return this.timesheetRepository.findAll( Sort.by(Sort.Direction.DESC, "year", "month") );
    }

    public Timesheet getTimesheetById(int id) {
        return this.timesheetRepository.findById(id).orElse(new Timesheet());
    }

    public Timesheet update(Timesheet timesheet) {
        Timesheet curr = this.getTimesheetById( timesheet.getId() );
        curr.setUser( timesheet.getUser());
        curr.setProject(timesheet.getProject());
        curr.setStage(timesheet.getStage());
        curr.setMonth(timesheet.getMonthValue());

        return this.save(curr);
    }

    public String deleteTimesheet(int id) {
        this.timesheetRepository.deleteById(id);
        return "Timesheet " + id + " został usunięty";
    }

    public List<Timesheet> getUserTimesheets(int id) {
        return this.timesheetRepository.findByUserId(id);
    }

    public boolean exist( Timesheet timesheet ) {
        Integer count = this.timesheetRepository.findAllWhere(
                timesheet.getUser().getId(),
                timesheet.getProject().getId(),
                timesheet.getStage(),
                timesheet.getMonthValue(),
                timesheet.getYear()
        );

        return count > 0;
    }
}
