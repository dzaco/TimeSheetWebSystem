package com.epstein.service;

import com.epstein.entity.Timesheet;
import com.epstein.entity.User;
import com.epstein.repository.TimesheetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.OrderBy;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        ).size();

        return count > 0;
    }

    public Optional<Timesheet> getTimesheet(Timesheet timesheet) {
        List<Timesheet> list = this.timesheetRepository.findAllWhere(
                timesheet.getUser().getId(),
                timesheet.getProject().getId(),
                timesheet.getStage(),
                timesheet.getMonthValue(),
                timesheet.getYear()
        );

        if( list.size() == 1)
            return Optional.of(list.get(0));
        else
            return Optional.empty();

    }

    public List<Timesheet> getTimesheetsInProject(int id) {
        return this.timesheetRepository.findByProjectId(id);
    }

    public Optional<Timesheet> getTimesheetByUserAndDate(User user, LocalDate date) {
        return this.timesheetRepository.findByUserIdAndDate(user.getId(), date.getYear(), date.getMonth().getValue() );
    }
}
