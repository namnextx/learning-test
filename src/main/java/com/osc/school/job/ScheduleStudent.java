package com.osc.school.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleStudent {
    private static final Logger logger = LoggerFactory.getLogger(ScheduleStudent.class);

    /*@Scheduled(initialDelayString = "10000", fixedRateString = "10000")
    public void scheduleCreateStudent() {
        logger.info("creating student");
        logger.info("created student");
    }*/

    /*@Scheduled(initialDelayString = "2000", fixedDelayString = "10000")
    public void fixedDelayStudent() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        logger.info("fixed delay");
    }*/


}
