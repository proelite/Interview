import java.util.HashMap;
import java.util.Map;

public class DiceGame {
	
	public static final int NUM_ROLLS = 5;
	
	public static enum Category {
		Ones(1), Twos(2), Threes(3), Fours(4), Fives(5), Sixes(6), Sevens(7), Eights(
				8), ThreeOfAKind(3), FourOfAKind(4), FullHouse(0), SmallStraight(
				3), LargeStraight(4), AllDifferent(0), Chance(0), AllSame(0);

		private int mHelperNum;

		Category(int num) {
			mHelperNum = num;
		}

		int getHelperNumber() {
			return mHelperNum;
		}
	}

	public static int getScore(Category category, int[] rolls)
			throws InvalidInputException {

		if (rolls == null || rolls.length != NUM_ROLLS)
			throw new InvalidInputException("Number of rolls must be " + NUM_ROLLS + ".");

		switch (category) {
		case Ones:
		case Twos:
		case Threes:
		case Fours:
		case Fives:
		case Sixes:
		case Sevens:
		case Eights:
			return getNumberScore(category, rolls);
		case ThreeOfAKind:
		case FourOfAKind:
			return getAllOfAKindScore(category, rolls);
		case FullHouse:
			return getFullHouseScore(category, rolls);
		case SmallStraight:
		case LargeStraight:
			return Math.max(getStraightsScore(category, rolls, true), getStraightsScore(category, rolls, false));
		case AllDifferent:
			return getAllDifferentScore(category, rolls);
		case Chance:
			return getChanceScore(category, rolls);
		case AllSame:
			return getAllSameScore(category, rolls);
		default:
			return 0;
		}
	}
	
	private static int getNumberScore(Category category, int[] rolls) throws InvalidInputException
	{
		int sum = 0;

		for (int roll : rolls) {
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			
			if (category.getHelperNumber() == roll)
				sum += roll;
		}

		return sum;
	}
	
	private static int getAllOfAKindScore(Category category, int[] rolls) throws InvalidInputException
	{
		boolean win = false;

		int[] rollCounts = new int[8];
		int sum = 0;

		for (int roll : rolls) {
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			int rollCount = rollCounts[roll - 1];
			rollCount++;
			if (rollCount >= category.getHelperNumber()) {
				win = true;
			}
			rollCounts[roll - 1] = rollCount;
			
			sum += roll;
		}
		
		return win ? sum : 0;
	}
	
	private static int getFullHouseScore(Category category, int[] rolls) throws InvalidInputException 
	{
		
		Map <Integer, Integer> map = new HashMap<Integer, Integer> (2);

		for (int roll : rolls) {
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			if (map.containsKey(roll)) 
			{
				int occurance = map.get(roll);
				if (occurance >= 3) 
				{
					return 0;
				}

				occurance++;
				map.put(roll, occurance);
			}	
			else {
				if (map.size() >= 2) 
				{
					return 0;
				}
				map.put(roll, 1);
			}
		}
		return 25;
	}
	
	private static int getStraightsScore(Category category, int[] rolls, boolean ascending) throws InvalidInputException 
	{
		int straights = 0;
		int previousRoll = rolls[0];
		int difference = ascending ? 1 : -1;
		
		for (int i = 1; i < rolls.length; i++) {
			int roll = rolls[i];
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			
			if (roll - previousRoll == difference) 
			{
				straights++;
				
				if (straights >= category.getHelperNumber()) 
				{
					if (category == Category.SmallStraight)
						return 30;
					if (category == Category.LargeStraight)
						return 40;
				}
			}
			else 
			{
				straights = 0;
			}
			
			previousRoll = roll;
		}  
	
		return 0;
	}
	
	private static int getAllDifferentScore(Category category, int[] rolls) throws InvalidInputException
	{
		boolean[] rollTrack = new boolean[8];
		for (int roll : rolls) {
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			if (rollTrack[roll - 1]) return 0;
			rollTrack[roll - 1] = true;	
		}			
		return 40;
	}
	
	private static int getChanceScore(Category category, int[] rolls) throws InvalidInputException 
	{
		int sum = 0;

		for (int roll : rolls) {
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			sum += roll;
		}

		return sum;
	}
	
	private static int getAllSameScore(Category category, int[] rolls) throws InvalidInputException
	{
		int first = rolls[0];
		for (int roll : rolls) {
			if (roll < 1 || roll > 8)
				throw new InvalidInputException(
						"Dice roll must be 1 through 8");
			if (roll != first)
				return 0;
		}
		return 50;	
	}
	
	public static Category getSuggestion(int[] rolls) throws InvalidInputException {
		
		Category suggestion = null;
		int maxScore = 0;
		
		for (Category cat : Category.values())
		{
			int score = getScore(cat, rolls);
			if (score > maxScore) 
			{
				maxScore = score;
				suggestion = cat;
			}
		}
	
		return suggestion;
	}

	public static void testGame()
	{
		try {
			assert(1 == DiceGame.getScore(DiceGame.Category.Ones, new int[] {1,2,3,4,5}));
			assert(4 == DiceGame.getScore(DiceGame.Category.Twos, new int[] {1,2,2,4,5}));
			assert(9 == DiceGame.getScore(DiceGame.Category.Threes, new int[] {1,3,3,3,5}));
			assert(8 == DiceGame.getScore(DiceGame.Category.Fours, new int[] {1,2,4,4,5}));
			assert(10 == DiceGame.getScore(DiceGame.Category.Fives, new int[] {1,5,3,4,5}));
			assert(12 == DiceGame.getScore(DiceGame.Category.Sixes, new int[] {6,5,6,7,5}));
			assert(7 == DiceGame.getScore(DiceGame.Category.Sevens, new int[] {4,5,6,7,6}));
			assert(8 == DiceGame.getScore(DiceGame.Category.Eights, new int[] {5,6,7,8,1}));
			assert(24 == DiceGame.getScore(DiceGame.Category.Eights, new int[] {5,6,8,8,8}));
			assert(12 == DiceGame.getScore(DiceGame.Category.ThreeOfAKind, new int[] {3,3,3,1,2}));
			assert(13 == DiceGame.getScore(DiceGame.Category.FourOfAKind, new int[] {5,2,2,2,2}));
			assert(25 == DiceGame.getScore(DiceGame.Category.FourOfAKind, new int[] {5,5,5,5,5}));
			assert(0 == DiceGame.getScore(DiceGame.Category.FourOfAKind, new int[] {5,6,1,2,3}));
			assert(25 == DiceGame.getScore(DiceGame.Category.FullHouse, new int[] {1,2,1,2,2}));
			assert(25 == DiceGame.getScore(DiceGame.Category.FullHouse, new int[] {5,6,6,6,5}));
			assert(0 == DiceGame.getScore(DiceGame.Category.FullHouse, new int[] {5,6,4,4,4}));
			assert(0 == DiceGame.getScore(DiceGame.Category.FullHouse, new int[] {1,1,1,1,1}));
			assert(30 == DiceGame.getScore(DiceGame.Category.SmallStraight, new int[] {1,2,3,4,5}));
			assert(30 == DiceGame.getScore(DiceGame.Category.SmallStraight, new int[] {5,2,3,4,5}));
			assert(30 == DiceGame.getScore(DiceGame.Category.SmallStraight, new int[] {8,7,6,5,1}));
			assert(30 == DiceGame.getScore(DiceGame.Category.SmallStraight, new int[] {1,7,6,5,4}));
			assert(0 == DiceGame.getScore(DiceGame.Category.SmallStraight, new int[] {1,2,6,4,5}));
			assert(40 == DiceGame.getScore(DiceGame.Category.LargeStraight, new int[] {1,2,3,4,5}));
			assert(40 == DiceGame.getScore(DiceGame.Category.LargeStraight, new int[] {8,7,6,5,4}));
			assert(0 == DiceGame.getScore(DiceGame.Category.LargeStraight, new int[] {1,2,6,4,5}));
			assert(40 == DiceGame.getScore(DiceGame.Category.AllDifferent, new int[] {8,7,6,5,4}));
			assert(0 == DiceGame.getScore(DiceGame.Category.AllDifferent, new int[] {1,1,6,4,5}));
			assert(12 == DiceGame.getScore(DiceGame.Category.Chance, new int[] {3,3,3,1,2}));
			assert(50 == DiceGame.getScore(DiceGame.Category.AllSame, new int[] {3,3,3,3,3}));
			assert(50 == DiceGame.getScore(DiceGame.Category.AllSame, new int[] {7,7,7,7,7}));
			assert(0 == DiceGame.getScore(DiceGame.Category.AllSame, new int[] {3,3,3,3,1}));
			assert(Category.ThreeOfAKind == DiceGame.getSuggestion(new int[] {1,1,1,2,3}));
			assert(Category.FullHouse == DiceGame.getSuggestion(new int[] {3,2,3,2,2}));
			assert(Category.SmallStraight == DiceGame.getSuggestion(new int[] {1,2,3,4,3}));
			assert(Category.LargeStraight == DiceGame.getSuggestion(new int[] {1,2,3,4,5}));
			assert(Category.AllDifferent == DiceGame.getSuggestion(new int[] {2,1,3,4,5}));
			assert(Category.Chance == DiceGame.getSuggestion(new int[] {8,7,8,7,6}));
			assert(Category.AllSame == DiceGame.getSuggestion(new int[] {1,1,1,1,1}));
		} catch (DiceGame.InvalidInputException e) {
			e.printStackTrace();
		}	
	}
	
	public static class InvalidInputException extends Exception {

		/**
		 * 
		 */
		private static final long serialVersionUID = -5448156649758350771L;

		public InvalidInputException() {
			super();
		}

		public InvalidInputException(String message) {
			super(message);
		}

		public InvalidInputException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
