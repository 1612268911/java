package com.cszt.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Date;

//日志输出源 MongoDB
@Component
public class MongoDBAppender extends UnsynchronizedAppenderBase<LoggingEvent> implements ApplicationContextAware {
    private static LogRepository logRepository;

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    protected void append(LoggingEvent e) {
        MyLog myLog = new MyLog();
        myLog.setLevel(e.getLevel().toString());
        myLog.setMsg(e.getFormattedMessage());
        myLog.setThread(e.getThreadName());
        myLog.setTs(new Date(e.getTimeStamp()));
        logRepository.save(myLog);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        if (applicationContext.getAutowireCapableBeanFactory().getBean(LogRepository.class) != null) {
            logRepository = (LogRepository) applicationContext.getAutowireCapableBeanFactory().getBean(LogRepository.class);
        }
    }
}
