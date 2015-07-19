package vrat.server;

import com.jmr.wrapper.common.Connection;
import com.jmr.wrapper.common.listener.SocketListener;
import vrat.packets.PcSpecs;

/*
 * @author vrmg
 */

public class ServerListener implements SocketListener {
    
	/*
     * When a clients sends an object
     */
    @Override
    public void received(Connection con, Object object) {
        if (object instanceof PcSpecs){
            PcSpecs specs = (PcSpecs) object;
            ConnectionManager.getInstance().addToWrapper(con, specs);
			System.out.println(ConnectionManager.getInstance().Time() + "Information about client recived and added.");
        }
    }

    /*
     * When a client connects
     */
    @Override
    public void connected(Connection con) {
        System.out.println(ConnectionManager.getInstance().Time() + "Client with connection: \"" + con + "\" connected.");
    }

    /*
     * When a client disconnects
     */
    @Override
    public void disconnected(Connection con) {
        System.out.println(ConnectionManager.getInstance().Time() + "Client with connection: \"" + con + "\" disconnected.");
        ConnectionManager.getInstance().removeFromWrapper(con);
    }
}
