import java.awt.Font;
import java.awt.FontFormatException;
import java.io.*;
public class FontCreator{
	private static final Font f1=new FontCreator("れいこフォント.ttf").createFont();
	private static final Font f2= new FontCreator("pettit_v2.ttf").createFont();
	private String filename;
	private FontCreator(String filename){
		this.filename = filename;
	}
	private Font createFont(){
		Font font = null;
		BufferedInputStream stream = null;
		try{
			stream = new BufferedInputStream(new FileInputStream(filename));
			font = Font.createFont(Font.TRUETYPE_FONT, stream);
			stream.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(FontFormatException e){
			e.printStackTrace();
		}
		return font;
	}
	public static Font getFont(int num){
		switch(num){
		case 1:return f1;
		default:return f2;
		}
	}
}
