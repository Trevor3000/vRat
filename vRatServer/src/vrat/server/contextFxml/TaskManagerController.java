package vrat.server.contextFxml;

import com.jmr.wrapper.common.Connection;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * @author vrmg
 */

public class TaskManagerController implements Initializable{
    public static Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public static void setCon(Connection co){
        con = co;
    }
}
