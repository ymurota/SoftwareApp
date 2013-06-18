import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Title {
	private String titleName;
	private float alpha =0.0f;
	private JPanel panel;
	public Title(String titleName,JPanel panel){
		this.panel = panel;
		this.titleName = titleName;
	}
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
		if(alpha<0.0f)alpha=0.0f;
		else if(alpha>1.0f)alpha=1.0f;
		Composite composite = g2d.getComposite();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,alpha));
		g.setColor(Color.WHITE);
		g.setFont(FontCreator.getFont(1).deriveFont(50.0f));
		g.drawString(titleName, MainFrame.WIDTH/2-250, MainFrame.HEIGHT/2);
		g2d.setComposite(composite);
	}
	public void FadeIn(){
		alpha = 0.0f;
		TimerTask task = new FadeInTask();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task,0,100);
	}
	private class FadeInTask extends TimerTask{
		public void run(){
			alpha += 0.05f;
			panel.repaint();
			if(alpha>0.9f)cancel();
		}
	}
}
