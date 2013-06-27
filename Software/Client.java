import java.net.*;
import java.io.*;

public class Client
{
	static BufferedReader in;
	static BufferedWriter out;

	public static void communication()
	{
		String[] server_data = new String[Server.CLIENT_MAX];

		try
		{
			for(int i = 0; i < Server.CLIENT_MAX; ++i)
			{
				server_data[i] = in.readLine();
			}

			String client_data = MainFrame.updateFrame(server_data);	

			out.write(client_data);
			out.newLine();
			out.flush();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws IOException
	{	
		new DisplaySize().setLocationRelativeTo(null);
		 InetAddress addr  = InetAddress.getByName("localhost");
		 System.out.println("addr = "+addr);
		 Socket socket = new Socket(addr, Server.PORT);

		 try
		 {
			 System.out.println("socket" + socket);
			 in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 out =new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			 while(true)
			 {
				 communication();

				 communication();
			 }
		}
		catch(IOException e)
		{	
			System.out.println(e);
		}
	}
}