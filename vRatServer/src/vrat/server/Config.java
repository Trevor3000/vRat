package vrat.server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.application.Platform;

/*
 * @author vrmg
 */

public class Config extends Application{
    
	/*
     * Initialzes javafx gui
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
		Parent root = loader.load();
		Controller cont = (Controller) loader.getController();
		primaryStage.setTitle("Vrat - Remote Administration Tool");
        primaryStage.setScene(new Scene(root, 1280, 650));
		primaryStage.setOnCloseRequest(e -> System.exit(0));
        primaryStage.show();
		(new Thread (new ServerStarter(49))).start();
    }
	
	public static void main (String args[]){
        launch();
    }
}