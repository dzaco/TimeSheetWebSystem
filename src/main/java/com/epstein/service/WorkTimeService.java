package com.epstein.service;

import com.epstein.entity.WorkTime;
import com.epstein.repository.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WorkTimeService {

    @Autowired private WorkTimeRepository workTimeRepository;
    @Autowired private UserService userService;

    // crud
    public WorkTime save(WorkTime time) {
        return this.workTimeRepository.save(time);
    }
    public List<WorkTime> getAll() {
        return this.workTimeRepository.findAll();
    }
    public WorkTime getById(int id) {
        return this.workTimeRepository.findById(id).orElse(new WorkTime());
    }
    public WorkTime update(WorkTime time) {
        WorkTime workTime = this.getById(time.getId());
        workTime.setDate( time.getDate().toLocalDate() );
        workTime.setTimeIn( time.getTimeIn().toLocalTime() );
        workTime.setTimeOut( time.getTimeOut().toLocalTime() );
        workTime.setUser(time.getUser());
        return this.save(workTime);
    }
    public void delete(WorkTime time) {
        this.workTimeRepository.delete(time);
    }


    public boolean isTodayWorkTime() {
        Optional<WorkTime> todayWorkTime = this.workTimeRepository.findByDateAndUserId( LocalDate.now() , userService.getLogged().getId() );
        return todayWorkTime.isPresent();
    }


}
