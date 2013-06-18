import javax.imageio.ImageIO;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.GradientPaint;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
public class User {
	public  String userName;
	public int HP;
	public int Ans=-1;
	public double Remain;
	public String Status="waiting...";
	private Rectangle hpRect;
	private BufferedImage statusImage,HPImage,icon;
	private GradientPaint grad;
	private Font font;
	private int TYPE;
	public final static int USER=0,ENEMY=-1;
	public User(String userName,int type){
		this.userName = userName;
		this.HP=100;
		this.Ans=-1;
		hpRect = new Rectangle(75,MainFrame.HEIGHT-41,HP*2,15);
		font = FontCreator.getFont(1);
		try{
			if(type == USER)icon = ImageIO.read(getClass().getResource("yaranaio.jpg"));
			else icon  = ImageIO.read(getClass().getResource("22024.png"));
			statusImage = ImageIO.read(getClass().getResource("2hu1.png"));
			HPImage = ImageIO.read(getClass().getResource("HP6.png"));	
		}catch(IOException e){
			e.printStackTrace();
		}
		this.TYPE=type;
	}
	public void HPUpdate(int hp){
		if(hp<0){
			this.HP=0;
		}else if(hp>100){
			this.HP=100;
		}else{
			this.HP=hp;
		}
	}
	public void Answer(int ans){
		this.Ans = ans;
	}
	public void statusUpdate(String status){
		Status = status;
	}
	public void draw(Graphics g){
		Graphics2D g2d = (Graphics2D)g;
			if(50<=HP){
				grad = new GradientPaint(30,10,Color.BLACK,
						210,10,Color.GREEN,false);
			}
			else if(20<=HP&&HP<50){
				grad = new GradientPaint(30,10,Color.BLACK,
						210,10,Color.YELLOW,false);
			}
			else if(HP<20){
				grad = new GradientPaint(30,10,Color.BLACK,
						210,10,Color.RED,false);
			}
		hpRect.setBounds(75,MainFrame.HEIGHT-41,HP*2,15);
		g2d.setPaint(grad);
		Composite nowComp = g2d.getComposite();
		AlphaComposite composite = AlphaComposite.getInstance(
                AlphaComposite.SRC_OVER, 0.4f);
		g2d.setComposite(composite);
		g2d.fill(hpRect);
		g2d.setComposite(nowComp);
		g2d.drawImage(HPImage, 7,MainFrame.HEIGHT-72, null);
		
		g2d.setColor(Color.WHITE);
		g2d.drawImage(icon,
				291/2-icon.getWidth()/2,
				MainFrame.HEIGHT-icon.getHeight()-100,null);
		g2d.setFont(font.deriveFont(18.0f));
		
		g2d.drawString(HP+"/100",180,MainFrame.HEIGHT-10);
		g2d.setFont(font.deriveFont(23.0f));
		g2d.drawString(userName, 23, MainFrame.HEIGHT-60);
		g2d.drawImage(statusImage,
				70,MainFrame.HEIGHT-icon.getHeight()-280,null);
		g2d.setColor(Color.BLACK);
		g2d.drawString(Status, 
				110, MainFrame.HEIGHT-icon.getHeight()-230);
	
	}
}
