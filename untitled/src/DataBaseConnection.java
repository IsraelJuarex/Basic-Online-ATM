import java.sql.Connection;
import java.sql.DriverManager;

class DataBaseConnection {

    public static Connection getConnectionToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            return DriverManager.getConnection(
                    "jdbc:mysql://aws.connect.psdb.cloud/testonlinebd?sslMode=VERIFY_IDENTITY",
                    "u8s8xfpnenzw2yks86t9",
                    "pscale_pw_Tbj3WlpVZJuZisq9hRSK0P3t6jlYh9lPRKoUPYGisAT");

        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            return null;
        }
    }
}