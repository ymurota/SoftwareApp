import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GameController extends JPanel{
	private static final int WIDTH=MainFrame.WIDTH;
	private static final int HEIGHT=MainFrame.HEIGHT;
	
    private ImageIcon enemyIcon;
    
    public AnswerPanel ansPanel;
	public GamePanel gamePanelLeft,gamePanelRight;
	
	public GameController(){
		enemyIcon = new ImageIcon(getClass().getResource("22024.png"));
		MainFrame.enemy = new User("enemy",enemyIcon,User.ENEMY);
		
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setLayout(new BorderLayout());
		
		gamePanelLeft = new GamePanel(GamePanel.LEFT);
		gamePanelRight = new GamePanel(GamePanel.RIGHT);
		ansPanel = new AnswerPanel();
		
		add(gamePanelLeft,BorderLayout.WEST);
		add(ansPanel,BorderLayout.CENTER);
		add(gamePanelRight,BorderLayout.EAST);
	}
	public void setup(){
		gamePanelLeft.gameLoop.start();
		gamePanelRight.gameLoop.start();
	}
	public void gameUpdate(){
		MainFrame.user.statusUpdate(ServerData.getInstance().status1);
		MainFrame.enemy.statusUpdate(ServerData.getInstance().status2);
		gamePanelLeft.newHPUpdate(ServerData.getInstance().HP1);
		gamePanelRight.newHPUpdate(ServerData.getInstance().HP2);
		ansPanel.update(ServerData.getInstance().selections,ServerData.getInstance().question);
			
		while(true){
			if(ansPanel.STATE==ansPanel.ASTATE) {
				ClientData.getInstance().set(MainFrame.user.Ans,MainFrame.user.Remain);
				break;
			}
			try{
				Thread.sleep(500);//0.5sec–ˆ‚ÉŠm”F
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
}
