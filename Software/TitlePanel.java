import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class TitlePanel extends JPanel implements MouseListener,KeyListener{
	private static final int WIDTH=MainFrame.WIDTH;
	private static final int HEIGHT=MainFrame.HEIGHT;
	public Title title;
	public TitlePanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		title= new Title("ソフトウェア制作B-7班",this);
		addMouseListener(this);
		addKeyListener(this);
	}
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		title.draw(g);
	}
	public void mouseClicked(MouseEvent e){
		MainFrame.cl.show(MainFrame.cardPanel,"Login");
	}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		if(key==KeyEvent.VK_ENTER)MainFrame.cl.show(MainFrame.cardPanel, "Login");
	}
	public void keyTyped(KeyEvent e){}
}
