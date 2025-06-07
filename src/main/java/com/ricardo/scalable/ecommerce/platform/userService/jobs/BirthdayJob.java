package com.ricardo.scalable.ecommerce.platform.userService.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ricardo.scalable.ecommerce.platform.userService.services.UserService;

@Component
public class BirthdayJob extends ScheduledJob {

    @Autowired
    private final UserService userService;

    public BirthdayJob(UserService userService) {
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 7 * * *")
    public void scheduled() {
        runSafely();
    }

    @Override
    protected void execute() {
        userService.notifyUserBirthdays();
    }

}
