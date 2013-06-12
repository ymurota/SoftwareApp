import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.TimerTask;


public class AnswerPanel extends JPanel implements ActionListener{
	private static final int WIDTH=MainFrame.WIDTH/3+103;
	private static final int HEIGHT=MainFrame.HEIGHT;
	private JButton[] ansButton;
	private BufferedImage question;
	private FontCreator fontCreator;
	private Font font;
	private ImageIcon icon;
	private Timer remainTimer,readyTimer;
	private RemainTask remainTask;
	private ReadyTask readyTask;
	private JLabel remainTime,readyTime;
	private double ready;
	private JLabel answer;
	private JButton confirmButton;
	private DecimalFormat df = new DecimalFormat("0.0");
	private String Question;
	private String[] Selection;
	
	public double remain;
	public int STATE;
	public int QSTATE=0,ASTATE=1,FIRST=-1;
	
	public AnswerPanel(){
		icon = new ImageIcon(getClass().getResource("btn056_09.gif"));
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		try{
			question = ImageIO.read(getClass().getResource("img06527fbfzikazj.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
		Question="";
		Selection = new String[4];
		for(int i=0;i<4;i++){
			Selection[i]=" ";
		}
		setLayout(null);
		ansButton = new JButton[4];
		for(int i=0;i<4;i++){
			ansButton[i] = new JButton(Selection[i],icon);
			ansButton[i].setBounds(WIDTH/2-130,HEIGHT/2+40*i,icon.getIconWidth(),icon.getIconHeight());
			ansButton[i].setForeground(Color.BLACK);
			ansButton[i].setHorizontalTextPosition(JButton.CENTER);
			ansButton[i].addActionListener(this);
			add(ansButton[i]);
		}
		fontCreator = new FontCreator("�ꂢ���t�H���g.TTF");
		font = fontCreator.createFont();
		
		readyTime = new JLabel("�J�n�܂�:"+ready);
		readyTime.setForeground(Color.WHITE);
		readyTime.setBounds(WIDTH/8,250,150,80);
		readyTime.setVisible(false);
		add(readyTime);
		
		remainTime = new JLabel("�c�莞��:"+remain);
		remainTime.setForeground(Color.WHITE);
		remainTime.setBounds(WIDTH/8,250,150,80);
		remainTime.setVisible(false);
		add(remainTime);
		
		answer = new JLabel();
		answer.setBounds(WIDTH/2-130,HEIGHT-100,200,100);
		answer.setVisible(false);
		answer.setForeground(Color.WHITE);
		answer.setFont(font.deriveFont(20.0f));
		add(answer);
		
		confirmButton = new JButton("����",icon);
		confirmButton.setBounds(WIDTH/2-130,HEIGHT-150,icon.getIconWidth(),icon.getIconHeight());
		confirmButton.setHorizontalTextPosition(JButton.CENTER);
		confirmButton.setVisible(false);
		confirmButton.addActionListener(this);
		add(confirmButton);
		
		STATE=FIRST;
		if(STATE==FIRST){
			confirmButton.setEnabled(true);
			confirmButton.setVisible(true);
		}
	}
	
	public void update(String[] selection,String question){
		for(int i=0;i<4;i++){
			Selection[i] = selection[i];
			ansButton[i].setEnabled(false);
		}
		ready=3.0;
		remain=10.0;
		Question = question;
		if(STATE==QSTATE){
			
			readyTimer = new Timer();
			readyTask = new ReadyTask();
			readyTime.setVisible(true);
			readyTime.repaint();
			readyTimer.scheduleAtFixedRate(readyTask,0,1*10);
			
		}else if(STATE==ASTATE){
			confirmButton.setEnabled(true);
			for(int i =0;i<4;i++){
				ansButton[i].setEnabled(false);
			}
			remainTime.setVisible(false);
			confirmButton.setVisible(true);
		}
	}
	
	public void paintComponent(Graphics g){
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		g.drawImage(question,WIDTH/2-question.getWidth()/2,HEIGHT/2-question.getHeight(),null);
		g.setFont(font.deriveFont(23.0f));
		g.setColor(Color.WHITE);
		if(STATE==QSTATE){
			g.drawString("���",50 , 180);
			g.drawString(Question, 50, 210);
		}
		else if(STATE==ASTATE){
			g.drawString("��", 50, 180);
			g.drawString(Question, 50, 210);
		}
		else if(STATE==FIRST){
			g.drawString("�ҋ@��",50 , 180);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(STATE==QSTATE){
			for(int i =0;i<4;i++){
			if(e.getSource()==ansButton[i]){
				remainTimer.cancel();
				MainFrame.user.Ans=i;
			}
			ansButton[i].setEnabled(false);
			}
		MainFrame.user.Remain=remain;
		answer.setText("���Ȃ��̉𓚂�"+MainFrame.user.Ans+"��");
		answer.setVisible(true);
		STATE=ASTATE;
		}
		else if(STATE==ASTATE){
			if(e.getSource()==confirmButton)
			{
				STATE=QSTATE;//���\����Ԃ�
				confirmButton.setEnabled(false);
			}
		}
		else if(STATE==FIRST){
			if(e.getSource()==confirmButton){
				confirmButton.setEnabled(false);
				STATE=QSTATE;	
			}
		}
	}

private class RemainTask extends TimerTask{
	public void run(){
		remain -= 0.01;
		remainTime.setText("�c�莞��:"+df.format(remain));
		if(remain<=0){
			remainTimer.cancel();
			for(int i=0;i<4;i++){
				ansButton[i].setEnabled(false);
			}
			MainFrame.user.Ans=-1;
			MainFrame.user.Remain=0;
			MainFrame.user.Remain=remain;
			answer.setText("���Ԑ؂�ł�");
			answer.setVisible(true);
			STATE=ASTATE;
		}
	}
}

private class ReadyTask extends TimerTask{
	public void run(){
		ready -= 0.01;
		readyTime.setText("�J�n�܂�:"+df.format(ready));
		if(ready<=0){
			readyTimer.cancel();
			readyTime.setVisible(false);
			for(int i=0;i<4;i++){
				ansButton[i].setText(Selection[i]);
				ansButton[i].setEnabled(true);
			}
			repaint();
			confirmButton.setVisible(false);
			answer.setVisible(false);
			
			remainTimer = new Timer();
			remainTask = new RemainTask();
			remainTime.setVisible(true);
			remainTime.repaint();
			remainTimer.scheduleAtFixedRate(remainTask, 0, 1*10);
		}
	}
}
}