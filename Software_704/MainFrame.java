import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame{
	public static int WIDTH;
	public static int HEIGHT;
	
	public static JPanel cardPanel;
	public static CardLayout cl;
	public static String userName;
	public static TitlePanel titlePanel;
	public static LoginPanel loginPanel;
	public static HomePanel homePanel;
	public static GameController gameController;
	public static ResultPanel resultPanel;
	public static User user;
	public static User opponent;
	public static int WAIT=0,START=1;
	public static int STATE=WAIT;
	public MainFrame(){
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		setTitle("ソフトウェア制作B-7班");
		setResizable(false);
		
		titlePanel = new TitlePanel();
		loginPanel = new LoginPanel();
		homePanel = new HomePanel();
		gameController=new GameController();
		resultPanel = new ResultPanel();
		
		cardPanel.add(titlePanel,"Title");
		cardPanel.add(loginPanel,"Login");
		cardPanel.add(homePanel,"Home");
		cardPanel.add(gameController,"Controller");
		cardPanel.add(resultPanel,"Result");
		
		cl = (CardLayout)(cardPanel.getLayout());
		getContentPane().add(cardPanel);
		
		titlePanel.title.FadeIn();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static String updateFrame(String[] data){
		while(true){
			if(STATE==START)break;
			try{
				Thread.sleep(500);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
		String cData;
		cData = MainFrame.gameController.gameUpdate(data);
		return cData;
	}
	public static void exit(){
		MainFrame.gameController.toResult();
	}
}
