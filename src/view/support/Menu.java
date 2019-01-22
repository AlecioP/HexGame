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

import ai.strategies.RandomMove;
import controller.Game;

public class Menu {

	private JFrame mainframe;
	private JPanel panel;
	private ButtonHex play,sound;

	public Menu() {
		mainframe = new JFrame("Hex Game");
		mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainframe.getContentPane().setPreferredSize(new Dimension(700, 700));
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
				Game g = new Game(mainframe);
				g.play();
				g.configureStrategy(new RandomMove());
			}
		});
		mainframe.setSize(mainframe.getContentPane().getPreferredSize());
		mainframe.getContentPane().setVisible(true);
		mainframe.setVisible(true);
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
