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
	static int q_index;

	public static final int MAX_HP = 100;


	public static void makeQuestionList()
	{
		try
		{
			File file = new File("QuizData.txt");
			BufferedReader reader = new BufferedReader(new FileReader(file));

			String question;
			
			while((question = reader.readLine()) != null)
			{
				String[] selections = new String[4];
				int answer;
				for (int i = 0; i < 4; ++i)
				{
					selections[i] = reader.readLine();
				}

				answer = Integer.parseInt(reader.readLine());;


				for(int i=0; i<3; ++i)
				{
					for(int j=i+1; j<4; ++j)
					{
						if((int)(Math.random() * 2.0) == 1)
						{
							String stmp = selections[i];
							selections[i] = selections[j];
							selections[j] = stmp;
							
							if(i == answer)
							{
								answer = j;
							}
							else if(j == answer)
							{
								answer = i;
							}
						}
					}
				}

				question_list.add(new QuestionData(question, selections, answer));
			}

			reader.close();

			for(int i=0; i<question_list.size()-1; ++i)
			{
				for(int j=i+1; j<question_list.size(); ++j)
				{
					if((int)(Math.random() * 2.0) == 1)
					{
						QuestionData tmp = question_list.get(i);
						question_list.set(i, question_list.get(j));
						question_list.set(j, tmp);
					}
				}
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
		q_index = 0;

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
		question_data = question_list.get(q_index);
		String _status = "‰ð“š’†";
		q_index = (q_index + 1) % question_list.size();

		for(int i = 0; i < client_number; ++i)
		{
			server_data[i].set(question_data.question, question_data.selections, _status, server_data[i].HP);
		}
	}

	public static void judge()
	{
		final int answer = question_data.answer;
		String[] answer_selection = new String[4];
		int damage;
		int HP = 0;

		for(int i = 0; i < 4; ++i)
		{
			if(i != answer)
			{
				answer_selection[i] = " ";
			}
			else
			{
				answer_selection[i] = question_data.selections[i];
			}
		}

		for(int i = 0; i < client_number; ++i)
		{	
			damage = 0;

			if(i == 0)
			{
				if(client_data[1].selected == answer)
				{
					damage = (int)(client_data[1].time * 2) + 5;
				}
				else
				{	
					damage = 0;
				}
			}
			else if(i == 1)
			{
				if(client_data[0].selected == answer)
				{
					damage = (int)(client_data[0].time * 2) + 5;
				}
				else
				{	
					damage = 0;
				}
			}

			HP = server_data[i].HP - damage;

			if(HP < 0)
			{
				HP = 0;
				is_dead = true;
			}

			String status = " " + damage;

			server_data[i].set(server_data[i].question, answer_selection, status, HP);
		}
	}
}
