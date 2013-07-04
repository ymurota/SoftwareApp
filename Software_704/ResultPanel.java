import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ResultPanel extends JPanel implements ActionListener{
	private final static int WIDTH = MainFrame.WIDTH;
	private final static int HEIGHT = MainFrame.HEIGHT;
	
	private JLabel result,exit;
	
	private ExitTask exitTask;
	private Timer exitTimer;
	
	private JButton end;
	
	public ResultPanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(null);
		
		result = new JLabel();
		result.setFont(FontCreator.getFont(2).deriveFont(80.0f));
		result.setForeground(Color.WHITE);
		result.setBounds(WIDTH/2-100,HEIGHT/2-100,400,50);
		add(result);
		
		exit = new JLabel();
		exit.setFont(FontCreator.getFont(1).deriveFont(20.0f));
		exit.setForeground(Color.BLACK);
		exit.setBounds(0,HEIGHT-50,480,50);
		exit.setVisible(false);
		add(exit);
		
		end = new JButton("OK");
		end.setFont(FontCreator.getFont(1).deriveFont(20.0f));
		end.setForeground(Color.BLACK);
		end.setBounds(500,HEIGHT-50,100,50);
		end.setHorizontalTextPosition(JButton.CENTER);
		end.setContentAreaFilled(false);
		end.addActionListener(this);
		end.setVisible(false);
		add(end);
	}
	
	public void paintComponent(Graphics g){
		g.drawImage(ImageEngine.getImage("rBack"), 0, 0, null);
	}
	
	public void judge(){
		if(MainFrame.user.HP != 0 && MainFrame.opponent.HP == 0){
			result.setText("You Win!!");
			SoundEngine.playSE("winSE");
		}
		else if(MainFrame.user.HP==0 && MainFrame.opponent.HP != 0){
			result.setText("You Lose...");
			SoundEngine.playSE("loseSE");
		}
		else {
			result.setText("EVEN");
			SoundEngine.playSE("evenSE");
		}
		MainFrame.cl.show(MainFrame.cardPanel, "Result");
		
		gameExit();
	}
	
	private void gameExit(){
		try{
			Thread.sleep(2000);
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		exitTimer = new Timer();
		exitTask = new ExitTask();
		exit.setVisible(true);
		exitTimer.scheduleAtFixedRate(exitTask, 0, 100);
	}
	
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==end){
			SoundEngine.playSE("SE1");
			System.exit(0);
		}
	}
	
	private class ExitTask extends TimerTask{
		private String exitString ="おつかれさまでした、これでゲームをおわります";
		private int cp=0;
		public void run(){
			exit.setText(exitString.substring(0,++cp));
			if(cp==exitString.length()){
				exitTimer.cancel();
				end.setVisible(true);
			}
		}
	}
}
