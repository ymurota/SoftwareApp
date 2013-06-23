
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.Image;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import javax.swing.ImageIcon;
public class GamePanel extends JPanel implements Runnable{
	private static final int WIDTH=291;
	private static final int HEIGHT=MainFrame.HEIGHT;
	
	private static final int FPS = 50;//FPS設定
	private static final long PERIOD = (long)(1.0/FPS*1000000000);
	private static long INTERVAL = 1000000000L;
    private long calcInterval = 0L;
    private long prevCalcTime;
    private long frameCount = 0;
    private double actualFPS = 0.0;
    private int POSITION;
	private Graphics dbg;
	private Image dBuffer = null;
	private DecimalFormat df = new DecimalFormat("0.0");
	private int STATE=0;
	private int STOP = -1;
    public static int LEFT=0,RIGHT=1;
    public int newHP;
	public Thread gameLoop;
	public GamePanel(int POSITION){
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		gameLoop=new Thread(this);
		this.POSITION = POSITION;
	}
	public void run(){
		long beforeTime,afterTime,timeDiff,sleepTime;
		long overSleepTime=0L;
		int noDelay=0;
		beforeTime = System.nanoTime();
		prevCalcTime = beforeTime;
		int tmpHP;
		while(true){
			if(STATE==STOP)break;
			if(POSITION==LEFT)tmpHP=MainFrame.user.HP;
			else tmpHP=MainFrame.opponent.HP;
			if(Math.max(tmpHP, newHP+1)==tmpHP){
				tmpHP--;
				update(tmpHP);//newHPになるまで1ずつ減らす
			}else{
				update(newHP);//tmpHP<newHPになったらnewHPで固定
			}
			render();//バッファリング
			print();//描画
			
			afterTime = System.nanoTime();
			timeDiff = afterTime-beforeTime;
			sleepTime = (PERIOD-timeDiff)-overSleepTime;
			if(sleepTime>0){
				try{
					Thread.sleep(sleepTime/1000000L);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				overSleepTime = System.nanoTime()-afterTime-sleepTime;
			}else{
				overSleepTime=0L;
				if(++noDelay>=1){
					Thread.yield();
					noDelay=0;
				}
			}
			beforeTime = System.nanoTime();
			calcFPS();
		}
		
	}
	private void update(int i){
		switch(STATE){
		case 0: if(POSITION==LEFT){//最初
			newHP = MainFrame.user.HP;
			}
			else{
				newHP = MainFrame.opponent.HP;
			}
				STATE=1;break;
		case 1:
			if(POSITION==LEFT){
				MainFrame.user.HPUpdate(i);
			}
			else{
				MainFrame.opponent.HPUpdate(i);
			}
		break;
		}
	}
	private void render(){
		if(dBuffer==null){
			dBuffer = createImage(WIDTH,HEIGHT);
			 if (dBuffer == null) {
	                System.out.println("dBuffer1 is null");
	                return;
	            } else {
	                dbg = dBuffer.getGraphics();
	            }
		}
		if(POSITION==LEFT){
			dbg.drawImage(MainFrame.gameController.background,
					0,0,
					WIDTH,HEIGHT,
					0,0,
					WIDTH,HEIGHT,
					null);
		}else{
			dbg.drawImage(MainFrame.gameController.background,
					0,0,
					WIDTH,HEIGHT,
					MainFrame.WIDTH-291,0,
					MainFrame.WIDTH-291+WIDTH,HEIGHT,
					null);
		}

		if(POSITION==LEFT){		
			dbg.setColor(Color.WHITE);
			dbg.drawString("FPS:"+df.format(actualFPS),15,20);
		MainFrame.user.draw(dbg);
		}
		else MainFrame.opponent.draw(dbg);
	}
	private void print(){
		try{
			Graphics g = getGraphics();
			if(g!=null&&dBuffer!=null){
				g.drawImage(dBuffer,0,0,null);
			}

			Toolkit.getDefaultToolkit().sync();
			if(g!=null)g.dispose();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void calcFPS(){//FPS計算
		frameCount++;
		calcInterval += PERIOD;
		if(calcInterval >= INTERVAL){
			long timeNow = System.nanoTime();
			long actualElapsedTime = timeNow - prevCalcTime;
			actualFPS = ((double)frameCount/actualElapsedTime)*1000000000L;
			frameCount=0L;
			calcInterval=0L;
			prevCalcTime=timeNow;
		}
	}
	public void newHPUpdate(int HP){
		newHP=HP;
	}
	public void loopStop(){
		STATE = STOP;
	}
}
