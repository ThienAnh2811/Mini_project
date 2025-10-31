package com.example.demo.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SystemMonitorTask {

    // chạy mỗi 30 giây
    @Scheduled(fixedRate = 30000)
    public void logSystemStatus() {
        log.info("🕓 System running normally at {}", java.time.LocalTime.now());
    }
}