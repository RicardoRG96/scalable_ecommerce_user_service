package com.ricardo.scalable.ecommerce.platform.userService.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ScheduledJob {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public final void runSafely() {
        logger.info("Executing scheduled job: {}", getClass().getSimpleName());
        try {
            execute();
            logger.info("Scheduled job completed successfully: {}", getClass().getSimpleName());
        } catch (Exception e) {
            logger.error("Error executing scheduled job: {}", getClass().getSimpleName(), e);
        }
    }

    protected abstract void execute();

}
