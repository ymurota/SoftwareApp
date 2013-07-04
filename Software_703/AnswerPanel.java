import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.TimerTask;


public class AnswerPanel extends JPanel implements ActionListener{
	private static final int WIDTH=MainFrame.WIDTH-291*2;
	private static final int HEIGHT=MainFrame.HEIGHT;
	private JButton[] ansButton;
	private BufferedImage question,time,answerImg,sButton,next;
	private BufferedImage[] selectionImg;
	private Font font = FontCreator.getFont(1);;
	private Timer remainTimer,readyTimer;
	private RemainTask remainTask;
	private ReadyTask readyTask;
	private JLabel remainTime,readyTime,yourAns;
	private JLabel[][] sLabel;
	private double ready;
	private JButton confirmButton;
	private DecimalFormat df = new DecimalFormat("0.0");
	private String Question;
	private String[] Selection;	
	private double remain;
	
	private int MAX=20;
	private int LINE_MAX=10;
	private char[][] qSentence = new char[LINE_MAX][MAX];
	private QuestionTask questionTask; 
	private Timer questionTimer;
	private int qSentencePos = 0;
	private int lineCount= 0;
	
	public int STATE;
	public int QSTATE=0,ASTATE=1,FIRST=-1;
	
	public AnswerPanel(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(null);
		
		sButton = ImageEngine.getImage("sButton");
		question = ImageEngine.getImage("question");
		time = ImageEngine.getImage("time");
		answerImg = ImageEngine.getImage("yourAns");
		next = ImageEngine.getImage("next");
		selectionImg = new BufferedImage[4];
		for(int i=0;i<4;i++){
			selectionImg[i] = ImageEngine.getImage("selection");
		}
		
		
		Question=" ";
		Selection = new String[4];
		for(int i=0;i<4;i++){
			Selection[i]=" ";
		}
		sLabel = new JLabel[4][2];
		for(int i=0;i<4;i++){
			for(int j=0;j<2;j++){
				sLabel[i][j] = new JLabel(" ");
				sLabel[i][j].setForeground(Color.WHITE);
				sLabel[i][j].setFont(font.deriveFont(18.0f));
				sLabel[i][j].setBounds(WIDTH/2-330,
					HEIGHT/2-80+80*i+35*j,
					450,
					45);
				add(sLabel[i][j]);
			}
		}
		
		ansButton = new JButton[4];
		for(int i=0;i<4;i++){
			ansButton[i] = new JButton("これにする",new ImageIcon(sButton));
			ansButton[i].setBounds(WIDTH/2+130,
					HEIGHT/2-70+80*i,
					sButton.getWidth(),
					sButton.getHeight());
			ansButton[i].setForeground(Color.WHITE);
			ansButton[i].setFont(font.deriveFont(18.0f));
			ansButton[i].setHorizontalTextPosition(JButton.CENTER);
			ansButton[i].addActionListener(this);
			ansButton[i].setContentAreaFilled(false);
			add(ansButton[i]);
		}
		
		readyTime = new JLabel("開始まで:"+ready);
		readyTime.setForeground(Color.WHITE);
		readyTime.setBounds(WIDTH/2-180,HEIGHT/2-160,150,80);
		readyTime.setVisible(false);
		add(readyTime);
		
		remainTime = new JLabel("残り時間:"+remain);
		remainTime.setForeground(Color.WHITE);
		remainTime.setBounds(WIDTH/2-180,HEIGHT/2-160,150,80);
		remainTime.setVisible(false);
		add(remainTime);
		
		yourAns = new JLabel();
		yourAns.setBounds(WIDTH/2-130,HEIGHT-90,200,100);
		yourAns.setVisible(false);
		yourAns.setForeground(Color.WHITE);
		yourAns.setFont(font.deriveFont(20.0f));
		add(yourAns);
		
		confirmButton = new JButton("NEXT",new ImageIcon(next));
		confirmButton.setBounds(WIDTH/2-next.getWidth()/2,
				HEIGHT-100,
				next.getWidth(),
				next.getHeight());
		confirmButton.setForeground(Color.WHITE);
		confirmButton.setHorizontalTextPosition(JButton.CENTER);
		confirmButton.setVisible(false);
		confirmButton.setFont(FontCreator.getFont(1).deriveFont(20.0f));
		confirmButton.addActionListener(this);
		confirmButton.setContentAreaFilled(false);
		add(confirmButton);
		
		STATE=FIRST;
		if(STATE==FIRST){
			confirmButton.setEnabled(true);
			confirmButton.setVisible(true);
		}
	}
	
	public void update(String[] selection,String question){
		for(int i=0;i<4;i++){
			Selection[i]=selection[i];
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
			setSelection(Selection);
			for(int i =0;i<4;i++){
				ansButton[i].setEnabled(false);
			}
			repaint();
			remainTime.setVisible(false);
			confirmButton.setVisible(true);
		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		int qw = question.getWidth();
		int qh = question.getHeight();
		
		g.drawImage(MainFrame.gameController.background,
				0,0,
				WIDTH,HEIGHT,
				291,0,
				291+WIDTH,HEIGHT,
				null);
		for(int i=0;i<4;i++){
			g.drawImage(selectionImg[i],
					WIDTH/2-350,
					HEIGHT/2-80+80*i,
					null);
		}
		g.drawImage(question,
				WIDTH/2-qw/2,
				HEIGHT/2-qh-100,null);
		g.drawImage(time,
				WIDTH/2-qw/2+20,
				HEIGHT/2-140,null);
		g.setFont(font.deriveFont(20.0f));
		g.setColor(Color.WHITE);
		if(STATE==QSTATE){
			g.drawString("問題",
					WIDTH/2-qw/2+30, 
					HEIGHT/2-qh-50);
			if(Question.length()/MAX!=0){
				for(int i=0;i<lineCount;i++){
				g.drawChars(qSentence[i],
						0,
						MAX, 
						WIDTH/2-qw/2+30, 
						HEIGHT/2-qh-20+20*i);
				}
				g.drawChars(qSentence[lineCount],0, qSentencePos, 
						WIDTH/2-qw/2+30, 
						HEIGHT/2-qh-20+20*lineCount);
				
			}else{
				g.drawChars(qSentence[lineCount],
						0,
						qSentencePos, 
						WIDTH/2-qw/2+30, 
						HEIGHT/2-qh-20);	
			}
		}
		else if(STATE==ASTATE){
			g.drawString("解答", 
					WIDTH/2-qw/2+30, 
					HEIGHT/2-qh-50);
			if(lineCount!=0){
				for(int i=0;i<lineCount;i++){
				g.drawChars(qSentence[i],0, 
						MAX, 
						WIDTH/2-qw/2+30, 
						HEIGHT/2-qh-20+20*i);
				}
				g.drawChars(qSentence[lineCount],
						0, 
						qSentencePos, 
						WIDTH/2-qw/2+30, 
						HEIGHT/2-qh-20+20*lineCount);
				
			}else{
				g.drawChars(qSentence[lineCount],
						0,
						qSentencePos, 
						WIDTH/2-qw/2+30, 
						HEIGHT/2-qh-20);	
			}
		}
		else if(STATE==FIRST){
			g.drawString("待機中",
					WIDTH/2-qw/2+30 ,
					HEIGHT/2-qh-50);
		}
		g.drawImage(answerImg,
				WIDTH/2-answerImg.getWidth()/2,
				HEIGHT-70,
				null);
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
		yourAns.setText("あなたの解答は"+(MainFrame.user.Ans+1)+"番");
		yourAns.setVisible(true);
		STATE=ASTATE;
		}
		else if(STATE==ASTATE){
			if(e.getSource()==confirmButton)
			{
				STATE=QSTATE;
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
	private void setSelection(String[] selection){
		for(int i=0;i<4;i++){
			if(selection[i].length()<=25){
				sLabel[i][0].setText(selection[i]);
				sLabel[i][1].setText(" ");
			}
			else{
				sLabel[i][0].setText(selection[i].substring(0,25));
				sLabel[i][1].setText(selection[i].substring(25,selection[i].length()));
			}
		}
	}

private class RemainTask extends TimerTask{
	public void run(){
		remainTime.setForeground(Color.WHITE);
		remain -= 0.01;
		remainTime.setText("残り時間:"+df.format(remain));
		if(remain<=3.0){
			remainTime.setForeground(Color.YELLOW);
		}
		if(remain<=1.0){
			remainTime.setForeground(Color.RED);
		}
		if(remain<=0){
			remainTimer.cancel();
			for(int i=0;i<4;i++){
				ansButton[i].setEnabled(false);
			}
			MainFrame.user.Ans=-1;
			MainFrame.user.Remain=0;
			MainFrame.user.Remain=remain;
			yourAns.setText("時間切れ");
			yourAns.setVisible(true);
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
			
			setSelection(Selection);
			for(int i=0;i<4;i++){
				ansButton[i].setEnabled(true);
			}
			setQuestion(Question);
			confirmButton.setVisible(false);
			yourAns.setVisible(false);
			
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
