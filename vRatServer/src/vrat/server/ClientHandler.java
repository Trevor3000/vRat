package vrat.server;

import javafx.beans.property.SimpleStringProperty;
import vrat.packets.PcSpecs;

/*
 * @author vrmg
 */

public class ClientHandler{
    
	/*
     * Saves all information from PcSpecs class to SimpleStringProperties
     */
    private SimpleStringProperty ip = new SimpleStringProperty();
    private SimpleStringProperty os = new SimpleStringProperty();
    private SimpleStringProperty macAddress = new SimpleStringProperty();
    private SimpleStringProperty pcName = new SimpleStringProperty();
    private SimpleStringProperty uniqueID = new SimpleStringProperty();
    private SimpleStringProperty country = new SimpleStringProperty();
    private SimpleStringProperty isp = new SimpleStringProperty();

    /*
     * Getters for javafx gui
     */
    public String getIp() {
        return this.ip.get();
    }
    public String getOs() {
        return this.os.get();
    }
    public String getMacAddress() {
        return this.macAddress.get();
    }
    public String getPcName() {
        return this.pcName.get();
    }
    public String getUniqueID() {
        return this.uniqueID.get();
    }
    public String getIsp() {
        return this.isp.get();
    }
    public String getCountry() {
        return this.country.get();
    }

	/*
	* Gives the getters their value
	*/
    public ClientHandler(PcSpecs specs) {
        ip.setValue(specs.ip);
        os.setValue(specs.os);
        macAddress.setValue(specs.macAddress);
        pcName.setValue(specs.pcName);
        uniqueID.setValue(specs.uniqueID);
        country.setValue(specs.country);
        isp.setValue(specs.isp);
    }
}


