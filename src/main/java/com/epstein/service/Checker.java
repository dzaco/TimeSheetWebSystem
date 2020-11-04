package com.epstein.service;

import com.epstein.entity.Message;
import com.epstein.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class Checker {
    private MessageRepository messageRepository;
    private TimesheetService timesheetService;
    private WorkTimeService workTimeService;
    private UserService userService;

    public Checker(MessageRepository messageRepository, TimesheetService timesheetService, WorkTimeService workTimeService, UserService userService) {
        this.messageRepository = messageRepository;
        this.timesheetService = timesheetService;
        this.workTimeService = workTimeService;
        this.userService = userService;
    }

    private Optional<Message> addWorkTime() {
        if( !this.workTimeService.isTodayWorkTime() ) {
            Optional<Message> message = this.messageRepository.findByCode("start-work");
            return message;
        }
        else
            return Optional.empty();
    }

    private Optional<Message> addEndWorkTime() {
        if(LocalTime.now().isAfter(LocalTime.of(16,0))) {
            Optional<Message> message = this.messageRepository.findByCode("end-work");
            return message;
        }
        else
            return Optional.empty();
    }

    private Optional<Message> addTimesheet() {
        LocalDate now = LocalDate.now();
        LocalDate firstDay = now.minusDays( now.getDayOfMonth() );
        Period period = Period.between( firstDay, now );

        if(period.getDays() > 5 && this.timesheetService.getTimesheetByUserAndDate(this.userService.getLogged(), now.minusMonths(1)).isEmpty()){
            return this.messageRepository.findByCode("timesheet");
        }
        else
            return Optional.empty();

    }


    public List<Message> checkForMessage() {
        List<Message> messages = new ArrayList<>();
        this.addWorkTime().ifPresent(messages::add);
        this.addTimesheet().ifPresent(messages::add);
        this.addEndWorkTime().ifPresent(messages::add);
        return messages;
    }


}
