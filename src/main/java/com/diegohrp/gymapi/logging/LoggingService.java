package com.diegohrp.gymapi.logging;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {
    private static final Logger logger = LoggerFactory.getLogger(LoggingService.class);

    public void info(String message, Object... args) {
        logger.info(appendContext(message), args);
    }

    public void warn(String message, Object... args) {
        logger.warn(appendContext(message), args);
    }

    public void error(String message, Object... args) {
        logger.error(appendContext(message), args);
    }

    public void debug(String message, Object... args) {
        logger.debug(appendContext(message), args);
    }

    private String appendContext(String message) {
        String transactionId = MDC.get("transactionId").toString();
        return transactionId != null
                ? String.format("TransactionID: %s, %s", transactionId, message)
                : message;
    }
}
