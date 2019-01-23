package view.support;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import core.HexCell;
import core.InvalidMoveException;
import view.GridView;
import view.sound.SoundsProvider;

public class ListenerHex  extends MouseAdapter{

	private GridView view;
	private ArtificialIntelligence ai;
	private AbsMoveStrategy str;

	public ListenerHex(GridView view,ArtificialIntelligence ai,AbsMoveStrategy str) {
		this.ai=ai;
		this.view = view;
		this.str = str;
	}

	public void mouseClicked(MouseEvent e) {
		try {
			System.out.println(""+e.getPoint());
			boolean br = false;
			for(int i=0;i<view.getGridView().length;i++) {
				for(int j=0;j<view.getGridView()[0].length;j++)
					if(view.getGridView()[i][j].contains(e.getPoint().x, e.getPoint().y)) {
						/*GRAPHIC EVENT */
						HexCell cell = view.getGrid().getGrid()[i][j];
						view.getGrid().occupy(cell.getCoordinates().getRow(), cell.getCoordinates().getCol());
						view.revalidate();
						view.repaint();
						br=true;
						/**/
						
						/*START AI */
						if(str!=null) {
							ai.addMove(i,j);
							ai.doPlay(str);
						}
						/**/
						
						/* TEST-SOUND */
						SoundsProvider.getInstance().startSound(SoundsProvider.index_ugh, false);
						try {
							Thread.sleep((long) 100);
						}catch(Exception ex) {}
						SoundsProvider.getInstance().startSound(SoundsProvider.index_wow, false);
						/* TEST-SOUND */
						
						break;
					}
				if(br)
					break;
			}
		}catch(InvalidMoveException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null,ex.getMessage());
		}
	}

	public void setStr(AbsMoveStrategy str) {
		this.str = str;
	}

}
