package view.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;

public class ButtonHex extends JButton {
	private static final long serialVersionUID = -2236383360588994152L;
	/**
	 * 
	 */
	private enum State {ON,OFF}
	private State state;
	private Image on,off;
	public static final int TYPE_INFO=0,TYPE_WARNING=1;
	private String text;
	private Font font;
	
	public ButtonHex(int type,String text) {
		
		switch(type) {
		case TYPE_INFO :{
			on = ResourceProvider.getInstance().getButton1On();
			off = ResourceProvider.getInstance().getButton1Off();
			break;
		}
		case TYPE_WARNING :{
			on = ResourceProvider.getInstance().getButton2On();
			off = ResourceProvider.getInstance().getButton2Off();
			break;
		}
		default:
			throw new RuntimeException("Invalid type of button");
		}
		state = State.ON;
		this.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				graphicClick();
			}
		});
		this.text = text;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, new File("font/west.ttf"));
		} catch (FontFormatException | IOException e1) {} 
		
	}
	
	public void changeState() {
		if(state.equals(State.ON))
			state=State.OFF;
		else
			state=State.ON;
	}
	
	public void graphicClick() {
		Graphics g = this.getGraphics();
		if(g!=null) {
			state = State.OFF;
			paintComponent(g);
			try {
				Thread.sleep((long) 250);
			}catch(Exception e) {}
			state = State.ON;
			paintComponent(g);
		}
	}
	
	protected void paintComponent(Graphics g) {
		Color c = this.getParent().getBackground();
		g.setColor(c);
		g.fillRect( 0, 0, this.getWidth(), this.getHeight());
		Image img;
		if(state.equals(State.ON))
			img = on;
		else
			img = off;
		g.setColor(Color.BLACK);
		AffineTransform at = new AffineTransform();
		at.scale(50, 50);
		Font f = this.font.deriveFont(at);
		g.setFont(f);
		g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		double pressDelta;
		if(state.equals(State.ON))
			pressDelta = 0;
		else
			pressDelta = this.getHeight()/10;
		double deltaY =( this.getHeight()-g.getFontMetrics(f).getMaxAscent())/2;
		double deltaX = (this.getWidth()-g.getFontMetrics(f).stringWidth(text))/2;
		g.drawString(text, (int)deltaX, (int)deltaY+(int)pressDelta);
		
	}
}





// n-2 n-1 n n+1
// s1 s2 f
//
