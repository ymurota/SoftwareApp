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
	public static GameController gameControllerPanel;
	public static User user;
	public static User enemy;
	public static ClientTest clientTest;
	public MainFrame(){
		clientTest = new ClientTest();
		cardPanel = new JPanel();
		cardPanel.setLayout(new CardLayout());
		setTitle("MainFrame");
		setResizable(false);
		titlePanel = new TitlePanel();
		loginPanel = new LoginPanel();
		homePanel = new HomePanel();
		gameControllerPanel=new GameController();
		cardPanel.add(titlePanel,"Title");
		cardPanel.add(loginPanel,"Login");
		cardPanel.add(homePanel,"Home");
		cardPanel.add(gameControllerPanel,"Controller");
		cl = (CardLayout)(cardPanel.getLayout());
		getContentPane().add(cardPanel);
		titlePanel.title.FadeIn();
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	public static void main(String[] args){
		new DisplaySize().setLocationRelativeTo(null);
		}
}
