package integracja;

import com.mysql.fabric.xmlrpc.Client;

import javax.xml.ws.Endpoint;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static ClientApp clientApp;
    public static App app;
    public static TestingApp testingApp;
    public static void main(String[] args) throws SQLException, AWTException {
        Endpoint.publish("http://localhost:8888/katalog", new Katalog());
        app = new App();
        clientApp = new ClientApp();
         testingApp = new TestingApp();

    }
}
