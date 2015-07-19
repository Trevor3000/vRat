package vrat.server;

import com.jmr.wrapper.common.exceptions.NNCantStartServer;
import com.jmr.wrapper.server.Server;

/*
 * @author vrmg
 */

public class ServerStarter implements Runnable {
    private int port;

    public ServerStarter(int port) {
        this.port = port;
    }

    /*
     * Starts the server with the given port
     */
    @Override
    public void run() {
        try {
            Server server = new Server(port, port);

            /*
             * Sets server listeners
             */
            server.setListener(new ServerListener());
            
			if (server.isConnected()) {
                Config c = new Config();
				System.out.println(ConnectionManager.getInstance().Time() + "Server successfully started on port: \"" + port + "\".");
            }
        } 
		catch (NNCantStartServer e) {
            e.printStackTrace();
            System.out.println(ConnectionManager.getInstance().Time() + "Error starting server - port might already be in use!");
        }

    }
}






