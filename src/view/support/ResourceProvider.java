package view.support;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ResourceProvider {
	
//	private final static String IMG_PATH= "img";
	private final static String BUTTON1_ON="data/img/button1ON.png";
	private final static String BUTTON1_OFF="data/img/button1OFF.png";
	private final static String BUTTON2_ON="data/img/buttonON.png";
	private final static String BUTTON2_OFF="data/img/buttonOFF.png";
	private final static String BG = "data/img/bg.png";
	private Image hexcell,button1On,button1Off,button2On,button2Off,bg;
	
	private static ResourceProvider instance=null;
	
	private ResourceProvider() {
		try {
//			hexcell = ImageIO.read(new File(ResourceProvider.IMG_PATH));
			button1On = ImageIO.read(new File(ResourceProvider.BUTTON1_ON));
			button1Off = ImageIO.read(new File(ResourceProvider.BUTTON1_OFF));
			button2On = ImageIO.read(new File(ResourceProvider.BUTTON2_ON));
			button2Off = ImageIO.read(new File(ResourceProvider.BUTTON2_OFF));
			bg = ImageIO.read(new File(ResourceProvider.BG));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ResourceProvider getInstance() {
		if(instance==null)
			instance = new ResourceProvider();
		return instance;
	}
	
	public Image getHexcell() {
		return hexcell;
	}

	public Image getButton1On() {
		return button1On;
	}

	public Image getButton1Off() {
		return button1Off;
	}

	public Image getButton2On() {
		return button2On;
	}

	public Image getButton2Off() {
		return button2Off;
	}
	
	public Image getBg() {
		return bg;
	}
}
