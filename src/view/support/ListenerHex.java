package view.support;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.JOptionPane;

import ai.AbsMoveStrategy;
import ai.ArtificialIntelligence;
import ai.guiSupporter.GameOverComputer;
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
			
			if(handleWin())
				return;
			
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
			
			handleWin();
			
		}catch(InvalidMoveException ex) {
			System.out.println(ex.getMessage());
			JOptionPane.showMessageDialog(null,ex.getMessage());
		}catch(Exception ex) {
			ex.printStackTrace();
			try {
				ex.printStackTrace(new PrintStream(new File("data/log/log.txt")));
			} catch (FileNotFoundException e1) {
				System.err.println("ERROR IN ERROR : ");
				e1.printStackTrace();
			}
		}
	}

	public void setStr(AbsMoveStrategy str) {
		this.str = str;
	}
	
	private boolean handleWin() throws Exception {
		/*GAMEOVER*/
		int winner = GameOverComputer.getInstance().isGameOver(view.getGrid());
		if(winner==1) {
			JOptionPane.showMessageDialog(null,"RED WINS");
			return true;
		}else if(winner==0) {
			JOptionPane.showMessageDialog(null,"BLUE WINS");
			return true;
		}
		return false;
		/*GAMEOVER*/
	}

}
