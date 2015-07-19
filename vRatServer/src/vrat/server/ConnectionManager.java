package vrat.server;

import com.jmr.wrapper.common.Connection;
import java.text.SimpleDateFormat;
import javafx.collections.ObservableList;
import vrat.packets.PcSpecs;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

/*
 * @author vrmg
 */

public class ConnectionManager {
    
	/*
     * ObservableList containing only the clienthandler object
     */
    public ObservableList<ClientHandler> clients;

    /*
     * ArrayList containing connection and clienthandler
     */
    private ArrayList<Wrapper> conSpecs = new ArrayList<>();

    /*
     * There can only be one instance of connectionManager
     */
    private static ConnectionManager instance = new ConnectionManager();
    private ConnectionManager() {
    }
	
    public static ConnectionManager getInstance() {
        return instance;
    }

    /*
     * Adds connection and clienthandler to ArrayList and ObservableList
     */
    public void addToWrapper(Connection con, PcSpecs specs) {
        ClientHandler cl = new ClientHandler(specs);
        conSpecs.add(new Wrapper(con, cl));
        clients.add(cl);
    }

    /*
     * Finds the clienthandler belonging to the connection
     */
    public ClientHandler getHandlerFromCon(Connection con) {
        ClientHandler cl = null;
        for (int i = 0; i < conSpecs.size(); i++){
            if(conSpecs.get(i).getConnection().equals(con)){
                cl = conSpecs.get(i).getClientHandler();
            }
        }
        return cl;
    }

    /*
     * Finds the clienthandler belonging to the connection
     */
    public Connection getConFromHandler(ClientHandler cl) {
        Connection con = null;
        for (Wrapper wrap : conSpecs) {
            if (wrap.getClientHandler().equals(cl)) {
                con = conSpecs.get(conSpecs.indexOf(wrap)).getConnection();
                break;
            }
        }
        return con;
    }

    /*
     * Removes connection and clienthandler from ArrayList and clienthandler from ObservableList
     */
    public void removeFromWrapper(Connection con) {
        ClientHandler cl = getHandlerFromCon(con);
        Iterator<Wrapper> iter = conSpecs.iterator();
        while (iter.hasNext()) {
            Wrapper wrap = iter.next();
            if (wrap.getConnection().equals(con)) {
                iter.remove();
                break;
            }
        }
        clients.remove(cl);
    }

    /*
     * The wrapper class
     */
    private class Wrapper {
        private Connection con;
        private ClientHandler cl;

        private Wrapper(Connection con, ClientHandler cl) {
            this.con = con;
            this.cl = cl;
        }

        /*
         * Getters for wrapper
         */
        public Connection getConnection() {
            return con;
        }
        public ClientHandler getClientHandler(){
            return cl;
        }
    }
	
	/*
	* Returns the current time
	*/
	public String Time(){
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		return sdf.format(cal.getTime()) + " - ";
	}
}
