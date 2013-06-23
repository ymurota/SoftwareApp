public class QuestionData
{
	String question;
	String[] selections;
	int answer;
	boolean canSelect;

	public QuestionData(String _question, String[] _selections, int _answer)
	{
		question = _question;
		selections = _selections;
		answer = _answer;
		canSelect = true;
	}
}