package view.support;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalBorders;

import ai.strategies.BlockingMove;
import controller.Game;
import view.sound.SoundsProvider;

public class Menu {

	private JFrame mainframe;
	private JPanel panel;
	private ButtonHex play,sound;

	public Menu() {

		this.mainframe = new JFrame();


		this.mainframe.setTitle("Hex Game");
		this.mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.mainframe.getContentPane().setPreferredSize(new Dimension(700, 700));
		mainframe.getContentPane().setSize(mainframe.getContentPane().getPreferredSize());
		//		mainframe.setResizable(false);

		panel = new BgPanel(true);

		//		Border border = new BasicBorders.ButtonBorder(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);
		Border border = new MetalBorders.InternalFrameBorder();
		panel.setBorder(border);
		panel.setBackground(new Color(153, 102, 51));
		panel.setLayout(new GridLayout(5,3));
		fakePanel(4, panel);
		play = new ButtonHex(ButtonHex.TYPE_INFO,"PLAY");
		play.setBorder(null);
		panel.add(play);
		fakePanel(5, panel);
		sound = new ButtonHex(ButtonHex.TYPE_WARNING,"SOUND ON");
		sound.setBorder(null);
		panel.add(sound);
		fakePanel(4, panel);
		mainframe.getContentPane().add(panel);
		panel.setVisible(true);
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				play.graphicClick();
				//				try {
				//					Thread.sleep((long) 250);
				//				}catch(Exception ex) {ex.printStackTrace();}
				Game g = new Game(mainframe);
				g.play();
				g.configureStrategy(new BlockingMove());
			}
		});

		sound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(sound.getText().equals("SOUND ON")) {	
					sound.setText("SOUND OFF");
				}else {	
					sound.setText("SOUND ON");
				}
			}
		});

		sound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SoundsProvider.getInstance().soundButtonClicked();
			}
		});
		mainframe.setSize(mainframe.getContentPane().getPreferredSize());
		mainframe.getContentPane().setVisible(true);
		mainframe.setVisible(true);
		SoundsProvider.getInstance().startSound(SoundsProvider.index_menu, true);
	}

	private static void fakePanel(int n,JPanel pane) {
		for(int i=0;i<n;i++) {
			JPanel fake = new JPanel();
			fake.setVisible(false);
			fake.setOpaque(false);
			pane.add(fake);
		}
	}

	public void launch() {
		mainframe.setVisible(true);
	}
}
