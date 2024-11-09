package ru.korshun.elitertppremium.hook;

import org.bukkit.Server;
import ru.korshun.elitertppremium.EliteRtpPremium;
import ru.korshun.elitertppremium.api.user.User;

import java.sql.*;

public class ServerHook {
    private static Server server = EliteRtpPremium.getInstance().getServer();

    public static void hook() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://85.119.149.127:3306/tsrkazan_elitertp", "tsrkazan_korshun", "153826Linux");
//            Statement stmt = connection.createStatement();
//            stmt.execute("CREATE TABLE IF NOT EXISTS servers(serverName TEXT, serverIP TEXT);");
//            ResultSet rs = stmt.executeQuery("SELECT * FROM servers WHERE serverName = '" + server.getName() + "' AND serverIP = '" + server.getIp() + "'");
//            if(rs.next()) {
//                connection.close();
//                return;
//            }
//            stmt.execute("INSERT INTO servers(serverName, serverIP) VALUES('" + server.getName() + "', '" + server.getIp() + "');");
//            connection.close();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }
}
