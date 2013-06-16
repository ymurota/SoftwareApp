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
	private double remain;
	
	private int MAX=10;
	private int LINE_MAX=10;
	private char[][] qSentence = new char[LINE_MAX][MAX];
	private QuestionTask questionTask; 
	private Timer questionTimer;
	private int qSentencePos = 0;
	private int lineCount= 0;
	
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
		fontCreator = new FontCreator("れいこフォント.TTF");
		font = fontCreator.createFont();
		
		readyTime = new JLabel("開始まで:"+ready);
		readyTime.setForeground(Color.WHITE);
		readyTime.setBounds(WIDTH/8,HEIGHT/2-50,150,80);
		readyTime.setVisible(false);
		add(readyTime);
		
		remainTime = new JLabel("残り時間:"+remain);
		remainTime.setForeground(Color.WHITE);
		remainTime.setBounds(WIDTH/8,HEIGHT/2-50,150,80);
		remainTime.setVisible(false);
		add(remainTime);
		
		answer = new JLabel();
		answer.setBounds(WIDTH/2-130,HEIGHT-100,200,100);
		answer.setVisible(false);
		answer.setForeground(Color.WHITE);
		answer.setFont(font.deriveFont(20.0f));
		add(answer);
		
		confirmButton = new JButton("次へ",icon);
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
			qSentencePos=0;
			lineCount=0;
			questionTimer = new Timer();
			readyTimer = new Timer();
			readyTask = new ReadyTask();
			readyTime.setVisible(true);
			readyTime.repaint();
			readyTimer.scheduleAtFixedRate(readyTask,0,1*10);
			
		}else if(STATE==ASTATE){
			confirmButton.setEnabled(true);
			for(int i =0;i<4;i++){
				ansButton[i].setText(Selection[i]);
				ansButton[i].setEnabled(false);
			}
			repaint();
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
			g.drawString("問題",WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+60);
			if(Question.length()/MAX!=0){
				for(int i=0;i<lineCount;i++){
				g.drawChars(qSentence[i],0, MAX, WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+90+20*i);
				}
				g.drawChars(qSentence[lineCount],0, qSentencePos, WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+90+20*lineCount);
				
			}else{
				g.drawChars(qSentence[lineCount],0,qSentencePos, WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+90);	
			}
		}
		else if(STATE==ASTATE){
			g.drawString("解答", WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+60);
			if(lineCount!=0){
				for(int i=0;i<lineCount;i++){
				g.drawChars(qSentence[i],0, MAX, WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+90+20*i);
				}
				g.drawChars(qSentence[lineCount],0, qSentencePos, WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+90+20*lineCount);
				
			}else{
				g.drawChars(qSentence[lineCount],0,qSentencePos, WIDTH/2-question.getWidth()/2+10, HEIGHT/2-question.getHeight()+90);	
			}
		}
		else if(STATE==FIRST){
			g.drawString("待機中",WIDTH/2-question.getWidth()/2+10 , HEIGHT/6+60);
		}
	}
	
	public void actionPerformed(ActionEvent e){
		if(STATE==QSTATE){
			for(int i =0;i<4;i++){
			if(e.getSource()==ansButton[i]){
				remainTimer.cancel();
				questionTimer.cancel();
				if(Question.length()/MAX==0){
					qSentencePos = Question.length();
				}
				else{
					lineCount=Question.length()/MAX;
					qSentencePos = 	Question.length()%MAX;
				}
				MainFrame.user.Ans=i;
			}
			ansButton[i].setEnabled(false);
			}
		MainFrame.user.Remain=remain;
		answer.setText("あなたの解答は"+MainFrame.user.Ans+"番");
		answer.setVisible(true);
		STATE=ASTATE;
		}
		else if(STATE==ASTATE){
			if(e.getSource()==confirmButton)
			{
				STATE=QSTATE;//問題表示状態へ
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
	private void setQuestion(String Question){
		int line=0;
		int cp=0;
		for(int i=0;i<qSentence.length;i++){
			for(int j=0;j<MAX;j++){
				qSentence[i][j] = ' ';
			}
		}
		
		for(int j=0;cp<Question.length();j++){
			if(j==MAX){
				line++;
				j=0;
			}
			qSentence[line][j]=Question.charAt(cp++);
		}
		questionTask = new QuestionTask();
		questionTimer.schedule(questionTask,0L,60L);
	}

private class RemainTask extends TimerTask{
	public void run(){
		remain -= 0.01;
		remainTime.setText("残り時間:"+df.format(remain));
		if(remain<=0){
			remainTimer.cancel();
			for(int i=0;i<4;i++){
				ansButton[i].setEnabled(false);
			}
			MainFrame.user.Ans=-1;
			MainFrame.user.Remain=0;
			MainFrame.user.Remain=remain;
			answer.setText("時間切れです");
			answer.setVisible(true);
			STATE=ASTATE;
		}
	}
}

private class ReadyTask extends TimerTask{
	public void run(){
		ready -= 0.01;
		readyTime.setText("開始まで:"+df.format(ready));
		if(ready<=0){
			readyTimer.cancel();
			readyTime.setVisible(false);
			for(int i=0;i<4;i++){
				ansButton[i].setText(Selection[i]);
				ansButton[i].setEnabled(true);
			}
			setQuestion(Question);
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

private class QuestionTask extends TimerTask{
	int length=Question.length();
	public void run(){
		if(qSentencePos==length)cancel();
		qSentencePos++;
		if(qSentencePos==MAX){
			lineCount++;
			qSentencePos=0;
			length -= MAX;
		}
		repaint();
	}
}
}
