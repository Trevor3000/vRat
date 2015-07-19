package vrat.packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.util.Scanner;

/*
 * @author vrmg
 */

public class PcSpecs implements Serializable{
    public String ip;
    public String macAddress;
    public String os;
    public String pcName;
    public String uniqueID;
    public String country;
    public String isp;

    public PcSpecs(String input){
        
		/*
		* Gets ip
		*/
		int startPlace = input.indexOf("ip") + 5;
        int endPlace = input.indexOf(",", startPlace);
        String iptemp = input.substring(startPlace, endPlace);
        iptemp = iptemp.substring(0, iptemp.length() - 1);

		/*
		* Gets isp
		*/
        int startPlace1 = input.indexOf("isp") + 6;
        int endPlace1 = input.indexOf(",", startPlace1);
        String isptemp = input.substring(startPlace1, endPlace1);
        isptemp = isptemp.substring(0, isptemp.length() - 1);

		/*
		* Gets country
		*/
        int findPlace = input.indexOf("country") + 1;
        int startPlace2 = input.indexOf("country", findPlace) + 10;
        int endPlace2 = input.indexOf(",", startPlace2);
        String countrytemp = input.substring(startPlace2, endPlace2);
        countrytemp = countrytemp.substring(0, countrytemp.length() - 1);
		
		/*
		* Saves it all to variables
		*/
        ip = iptemp;
        isp = isptemp;
        country = countrytemp;
        macAddress = GetMacAddress();
        os = GetOs();
        pcName = GetPcName();
        uniqueID = GetUniqueID();
    }

	/*
	* Gets os
	*/
    private String GetOs(){
        return System.getProperty("os.name");
    }

	/*
	* Gets pc name
	*/
    private String GetPcName(){
        String x = "";
        try {
            x = InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("Error getting pcName");
        }
        return x;
    }

	/*
	* Gets mac adress
	*/
    private String GetMacAddress(){
        InetAddress ip;
        StringBuilder macAddress = new StringBuilder();
        
		try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        } 
		catch (UnknownHostException | SocketException e){
            e.printStackTrace();
            System.out.println("Error getting macAddress");
        }
        
		return macAddress.toString().toUpperCase();
    }

	/*
    * Gets pc serial number
	* Windows
    */
    private String GetUniqueID(){
        String out = "";
        
		if (os.contains("Win")){
            try{
                Process process = Runtime.getRuntime().exec(new String[] { "wmic", "bios", "get", "serialNumber" });
                process.getOutputStream().close();
                Scanner sc = new Scanner(process.getInputStream());
                sc.next();
                String serial = sc.next();
                sc.close();
                out = serial;
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("Error getting windows serialNumber");
            }
        }
		
		/*
		* Mac
		*/
        if (os.contains("Mac")){
            ProcessBuilder pb = new ProcessBuilder("bash", "-c",
                    "ioreg -l | awk '/IOPlatformSerialNumber/ { print $4;}'");
            pb.redirectErrorStream(true);
            try {
                Process p = pb.start();
                String s;
                BufferedReader stdout = new BufferedReader(
                        new InputStreamReader(p.getInputStream()));
                while ((s = stdout.readLine()) != null) {
                    out = s.substring(1, s.length()-1);
                }
                p.getInputStream().close();
                p.getOutputStream().close();
                p.getErrorStream().close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Error getting mac serialNumber");
            }
        }
		
		/*
		* On some windows computers .exec will output: "To be filled by O.E.M
		* This ensures that there still will be an "unqiueID" and not an empty field
		*/
        else if (out.equals("to")){
			String a = System.getProperty("user.name").toLowerCase();
            out = a + "@" + GetPcName().toLowerCase(); // fx username@computerName
			out = "O.E.M - ";
		}
		
		/*
		* This ensures that there still will be an "unqiueID" and not an empty field
		*/
        else{
            String a = System.getProperty("user.name").toLowerCase();
            out = a + "@" + GetPcName().toLowerCase(); // fx username@computerName
        }
        return out;
    }
}

