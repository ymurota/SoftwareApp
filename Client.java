import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) 
			throws IOException {
		 InetAddress addr
		   = InetAddress.getByName("localhost");
	  System.out.println("addr = "+addr);
		Socket socket =
		new Socket(addr, Server.PORT);
		 try{
			System.out.println("socket" + socket);	
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedWriter out =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			String Inputline;
			String Outputline;
		while((Inputline = in.readLine()) != null){
			    out.write(Outputline); 
				out.newLine();
				out.flush();		
           }
          out.close();
          in.close();
          socket.close();
		 }
			catch(IOException e)
			{
				System.out.println(e);
			}
	  }   
}