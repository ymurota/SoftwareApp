import java.io.*;
import java.util.*;

public class ServerSystem
{	//private static ArrayList<QuestionData> question_list;
	private static ArrayList<QuestionData> question_list = new ArrayList<QuestionData>();
	static QuestionData question_data;

	static int client_number;
	static ServerData[] server_data;
	static ClientData[] client_data;
	static int data_valid_number;
	static boolean is_dead;

	public static final int MAX_HP = 100;


	public static void makeQuestionList()
	{
		try
		{
			File file = new File("QuizData.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String question;
			String[] selections = new String[4];
			int answer;
			while((question = reader.readLine()) != null)
			{
				for (int i = 0; i < 4; ++i)
				{
					selections[i] = reader.readLine();
				}

				answer = Integer.parseInt(reader.readLine())-1;//-1

				question_list.add(new QuestionData(question, selections, answer));
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println(e);
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}

	public static void set(int _client_number)
	{
		client_number = _client_number;
		server_data = new ServerData[client_number];
		client_data = new ClientData[client_number];
		is_dead = false;

		for(int i = 0; i < client_number; ++i)
		{	server_data[i]=new ServerData();
			client_data[i]=new ClientData();
			server_data[i].HP = MAX_HP;
		}
	}

	public static void reset()
	{
		data_valid_number = 0;
	}

	public static void selectQuestionData()
	{
		int index = (int)(Math.random() * question_list.size());
		question_data = question_list.get(index);

		String _status = null;	

		for(int i = 0; i < client_number; ++i)
		{
			server_data[i].set(question_data.question, question_data.selections, _status, server_data[i].HP);
		}
	}

	public static void judge()
	{
		final int answer = question_data.answer;
		String[] answer_selection = question_data.selections;
		int damage;
		int HP;

		for(int i = 0; i < 4; ++i)
		{
			if(i != answer)
			{
				answer_selection[i] = null;
			}
		}

		for(int i = 0; i < client_number; ++i)
		{	
			if(client_data[i].selected == answer)
			{
				damage = (int)(client_data[i].time * 2);
			}
			else
			{	
				damage = 0;
			}

			HP = server_data[i].HP - damage;

			if(HP < 0)
			{
				HP = 0;
				is_dead = true;
			}

			String status = "" + damage;

			server_data[i].set(server_data[i].question, answer_selection, status, HP);
		}
	}
}
