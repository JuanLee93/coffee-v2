package com.example.coffee_v2.config;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.P6SpyOptions;
import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import jakarta.annotation.PostConstruct;
import org.hibernate.engine.jdbc.internal.FormatStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;

import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Configuration
public class P6SpyConfig implements MessageFormattingStrategy {

    @PostConstruct
    public void setLogMessageFormat() {
        P6SpyOptions.getActiveInstance().setLogMessageFormat(this.getClass().getName());
    }

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        return String.format("[%s] | %d ms | %s", category, elapsed, formatSql(category, sql));
    }

    private static String stackTrace() {
        StackTraceElement[] elements = new Throwable().getStackTrace();
        String className = ClassUtils.getUserClass(P6SpyConfig.class).getName();
        Optional<StackTraceElement> firstArray = Arrays.stream(elements)
                .filter(t -> t.toString().startsWith("com.example.coffee_v2") && !t.toString().contains(className))
                .findFirst();
        return firstArray.map(StackTraceElement::toString).orElse("");
    }

    private String formatSql(String category, String sql) {
        if (sql != null && !sql.trim().isEmpty() && Category.STATEMENT.getName().equals(category)) {
            String trimmedSQL = sql.trim().toLowerCase(Locale.ROOT);
            if (trimmedSQL.startsWith("create") || trimmedSQL.startsWith("alter") || trimmedSQL.startsWith("comment")) {
                sql = stackTrace() + FormatStyle.DDL.getFormatter().format(sql);
            } else {
                sql = stackTrace() + FormatStyle.BASIC.getFormatter().format(sql);
            }
        }
        return sql;
    }
}
