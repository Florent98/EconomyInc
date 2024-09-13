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
        // Get the logger context and configuration
        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        PatternLayout layout = PatternLayout.newBuilder()
                .withPattern("[%d{dd:MMM:yyyy}] [%d{HH:mm:ss}] [%t/%level]: %msg%n")
                .build();

        FileAppender fileAppender = FileAppender.newBuilder()
                .setName("MoneyLogger")
                .withFileName("logs/economyinc-money.log")  // Output to a custom file
                .withLayout(layout)
                .withAppend(true)  // Append to the file instead of overwriting
                .build();

        fileAppender.start();

        // Create a separate logger configuration for your mod
        LoggerConfig modLoggerConfig = new LoggerConfig("MoneyLoggerConfig", org.apache.logging.log4j.Level.ALL, false);
        modLoggerConfig.addAppender(fileAppender, null, null);

        // Add the mod logger configuration to the Log4j context
        config.addLogger("MoneyLogger", modLoggerConfig);
        context.updateLoggers();  // Apply changes to the context

        // Get the logger instance for your mod
        modLogger = LogManager.getLogger("MoneyLogger");
    }

    public static Logger getLogger() {
        return modLogger;
    }

}
