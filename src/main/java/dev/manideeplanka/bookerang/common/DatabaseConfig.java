package dev.manideeplanka.bookerang.common;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import org.jdbi.v3.core.Jdbi;

public class DatabaseConfig {


    public static Jdbi getJdbi() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        HikariConfig config = new HikariConfig();
        config.setUsername(dotenv.get("DB_USERNAME"));
        config.setPassword(dotenv.get("DB_PASSWORD"));
        config.setJdbcUrl(dotenv.get("DB_URL"));

        HikariDataSource ds = new HikariDataSource(config);
        return Jdbi.create(ds);
    }
}
