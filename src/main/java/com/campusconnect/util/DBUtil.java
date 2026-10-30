
package com.campusconnect.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {

    private static final String URL =
        "jdbc:oracle:thin:@(DESCRIPTION=" +
        "(ADDRESS=(PROTOCOL=TCPS)(HOST=db.freesql.com)(PORT=2484))" +
        "(CONNECT_DATA=(SERVICE_NAME=26ai_un3c1)))";

    // Enter your own Oracle FreeSQL credentials.
    private static final String USER = "P_A_Y_A_L_GILL161_SCHEMA_9B7HS";
    private static final String PASSWORD = "SWFV09WEP5YNG5N2I9FLG!ELVj6DDX";


public static Connection getConnection() throws SQLException {
    try {
        Class.forName("oracle.jdbc.OracleDriver");
    } catch (ClassNotFoundException e) {
        throw new SQLException(
            "Oracle JDBC driver is missing. Check Maven Dependencies.",
            e
        );
    }

    Properties properties = new Properties();
    properties.setProperty("user", USER);
    properties.setProperty("password", PASSWORD);
    properties.setProperty("oracle.net.ssl_server_dn_match", "true");

    return DriverManager.getConnection(URL, properties);
}
}
