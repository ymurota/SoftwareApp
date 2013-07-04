import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;


public class ImageEngine {
	private HashMap<String,BufferedImage> imageMap = new HashMap<String,BufferedImage>();
	private static final ImageEngine imageEngine = new ImageEngine();
	
	private ImageEngine(){
		try{
			imageMap.put("time", ImageIO.read(getClass().getResource("time.png")));
			imageMap.put("question", ImageIO.read(getClass().getResource("hakkou1.png")));
			imageMap.put("status1", ImageIO.read(getClass().getResource("status4.png")));
			imageMap.put("status2", ImageIO.read(getClass().getResource("status3.png")));
			imageMap.put("p1", ImageIO.read(getClass().getResource("mrt1.png")));
			imageMap.put("p2", ImageIO.read(getClass().getResource("mrt4.png")));
			imageMap.put("gBack", ImageIO.read(getClass().getResource("l_126.png")));
			imageMap.put("HP", ImageIO.read(getClass().getResource("HP6.png")));
			imageMap.put("button2", ImageIO.read(getClass().getResource("Button3.png")));
			imageMap.put("lBack", ImageIO.read(getClass().getResource("back.png")));;
			imageMap.put("yourAns", ImageIO.read(getClass().getResource("answer.png")));
			imageMap.put("button1", ImageIO.read(getClass().getResource("Button.gif")));
			imageMap.put("hBack", ImageIO.read(getClass().getResource("l_185.jpg")));
			imageMap.put("selection", ImageIO.read(getClass().getResource("selection2.png")));
			imageMap.put("sButton", ImageIO.read(getClass().getResource("selectionButton.png")));
			imageMap.put("next", ImageIO.read(getClass().getResource("NEXT.png")));
			imageMap.put("rBack", ImageIO.read(getClass().getResource("035.jpg")));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public static BufferedImage getImage(String key){
		return imageEngine.imageMap.get(key);
	}
}
