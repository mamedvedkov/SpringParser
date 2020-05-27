package parser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.sql.*;

@Component
public class DataBaseHandler {
    public Connection connection = null;
    @Value("${dataBase.Port}")
    private String dataBasePort;
    @Value("${dataBase.Name}")
    private String dataBaseName;
    @Value("${dataBase.Root}")
    private String dataBaseRoot;
    @Value("${dataBase.Pass}")
    private String dataBasePass;

    @PostConstruct
    private void init() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:"
                    + dataBasePort + "/"
                    + dataBaseName + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Europe/Moscow"
                    , dataBaseRoot
                    , dataBasePass);
            System.out.println("Connected!!!");
        } catch (Exception e) {
            e.printStackTrace();
    }
}

    public ResultSet runSql(String sql) throws SQLException {
        Statement sta = connection.createStatement();
        return sta.executeQuery(sql);
    }

    public boolean runSql2(String sql) throws SQLException {
        Statement sta = connection.createStatement();
        return sta.execute(sql);
    }

    @PreDestroy
    protected void close() throws Throwable {
        if (connection != null || !connection.isClosed()) {
            connection.close();
        }
    }

}
