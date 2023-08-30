package com.micutne.odik.common.scheduler;

import com.micutne.odik.domain.email.Email;
import com.micutne.odik.repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ExpiredEmailScheduler implements Job {
    private final EmailRepository emailRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LocalDateTime date = LocalDateTime.now().minusMinutes(3);
        List<Email> emailList = emailRepository.findByDateBefore(date);
        long count = emailList.size();
        if (!emailList.isEmpty()) {
            emailRepository.deleteAll(emailList);
            log.info("Delete " + count + " expired email");
        } else log.info("No expired email verification");
    }
}
