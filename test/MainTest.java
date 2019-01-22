import java.awt.Dimension;

import javax.swing.JFrame;

import core.Grid;
import view.GridView;
import view.support.ListenerHex;

public class MainTest {

	public static void main(String[] args) {
		JFrame frame = new JFrame("test");
		frame.getContentPane().setSize(new Dimension(800,800));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(frame.getContentPane().getSize());
		/*  
		 * 
		 * frame.setSize(new Dimension
		 * (frame.getContentPane().getPreferredSize().width,
		 * frame.getContentPane().getPreferredSize().height+
		 * frame.getSize().height));
		 * 
		 *  */
		Grid grid = new Grid(11);
		GridView gridview = new GridView(frame, grid);
		frame.add(gridview);
		
		gridview.setVisible(true);
		frame.setVisible(true);
		ListenerHex lis = new ListenerHex(gridview,null,null);
		gridview.addMouseListener(lis);
		System.out.println("DIMENSION : "+frame.getSize());
	}
}
