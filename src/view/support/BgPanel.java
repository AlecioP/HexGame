package view.support;

import java.awt.Graphics;

import javax.swing.JPanel;

public class BgPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5888049594992278765L;
	
	public BgPanel(boolean b) {
		super(b);
	}

	@Override
	protected void paintComponent(Graphics arg0) {
		
		super.paintComponent(arg0);
		arg0.drawImage(ResourceProvider.getInstance().getBg(), 0, 0, this.getWidth(), this.getHeight(), null);
	}
}
