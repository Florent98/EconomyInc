package fr.fifoube.main.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.FileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;

public class MoneyLogger {

    private static final Logger modLogger;

    static {
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("[%d{dd:MMM:yyyy}] [%d{HH:mm:ss}] [%t/%level]: %msg%n")
                .build();

        FileAppender fileAppender = FileAppender.newBuilder()
                .setName("MoneyLogger")
                .withFileName("logs/economyinc-money.log")
                .withLayout(layout)
                .withAppend(true)
                .build();

        fileAppender.start();

        LoggerConfig modLoggerConfig = new LoggerConfig("MoneyLoggerConfig", org.apache.logging.log4j.Level.ALL, false);
        modLoggerConfig.addAppender(fileAppender, null, null);

        config.addLogger("MoneyLogger", modLoggerConfig);
        context.updateLoggers();  // Apply changes to the context

        modLogger = LogManager.getLogger("MoneyLogger");
    }

    public static Logger getLogger() {
        return modLogger;
    }

}
