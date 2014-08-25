package string;

//Challenge 168
/**
 * String Index, Challenge 168 on Reddit Daily Programmer 
 * Program is to find any numbered word in a string. 
 * 
 * Words is defined as [a-zA-Z0-9]+ so at least one of these and
 * many more in a row defines a word. Any other character is just a buffer
 * between words." Index can be any integer (this oddly enough includes negative
 * value). If the index into the string does not make sense because the word
 * does not exist then return an empty string.
 * 
 * @author Joe Kvedaras
 *
 */

public class StringIndex {

	// private static String[] words;//maybe, maybe just pass array to getWord

	public static void main(String[] args) {
		// Hard coded string for the problem
		String input = "...You...!!!@!3124131212 Hello have this is a --- string Solved !!...? to test @\n\n\n#!#@#@%$**#$@ Congratz this !!!!!!!!!!one --- Problem\n\n";
		// split input into an array. Use regex, splitting at anything that isn't a letter or number
		String[] words = input.split("[^a-zA-Z0-9]+");
		// Hard coded positions for the problem.
		int[] positions = { 12, -1, 1, -10, 0, 4, 1000, 9, -1000, 16, 13, 17,
				15 };

		// For every item in array, get the word at that position.
		for (int pos : positions) {
			String word = (String) getWord(pos, words);
			System.out.print(word + (word.isEmpty() ? "" : " "));
		}
	}

	/**
	 * Get indexed item in an array after checking the index.
	 * Return -1 if index out of range.
	 * @param index
	 * @param array
	 * @return Object at index
	 */
	private static Object getWord(int i, Object[] array) {
		if (i >= 0 && i < array.length)
			return array[i];
		else
			return -1;
	}

}