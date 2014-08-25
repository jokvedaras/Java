package befunge_interpreter;


/**
 Befunge Interpreter. Reddit Daily Programmer Challenge 164
 Basic compiler to run Befunge.
 @author Joe Kvedaras
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Stack;


@SuppressWarnings("serial")
public class BefungeInterpreter extends Stack<Integer> {
	
	public enum Direction {
		UP, RIGHT, LEFT, DOWN
	}

	private final char[][] myProgram;
	private boolean stringMode; // toggles on and off with quotes.
	private Direction myDirection; // changes based on arrow chars ^><v
	private int row, col; // position within the program
	private boolean hasEnded;

	private BefungeInterpreter() {
		stringMode = false;
		hasEnded = false;
		myDirection = Direction.RIGHT;
		row = 0;
		col = 0;
		myProgram = new char[25][80];
	}


	/**
	 * Read the program from a text file. Any code outside the 15*80 bounds is ignored
	 * @param File source
	 * @throws IOException
	 */
	public void loadProgram(File source) throws IOException {
		try{
			BufferedReader in = new BufferedReader(new FileReader(source));
			for (int row = 0; row < myProgram.length; row++) {
				String line = in.readLine();
				for (int col = 0; line != null && col < line.length()
						&& col < myProgram[0].length; col++){
					myProgram[row][col] = line.charAt(col);
				}
			}
			//close BufferedReader
			in.close();
		}
		catch(IOException e){
			System.out.println("Can't load file");
			e.printStackTrace();
		}
	}


	/**
	 * In befunge an empty stack is an infinite amount of zeros
	 */
	@Override
	public synchronized Integer pop() {
		return isEmpty() ? 0 : super.pop();
	}

	/**
	 * change position based on direction
	 */
	private void move() {
		switch (myDirection) {
		case DOWN:
			row++;
			break;
		case LEFT:
			col--;
			break;
		case RIGHT:
			col++;
			break;
		case UP:
			row--;
			break;
		}
		//bounds check
		if (row < 0)
			row = 24;
		if (row > 24)
			row = 0;
		if (col < 0)
			col = 79;
		if (col > 79)
			col = 0;
	}

	/**
	 * Takes the current character and does the appropriate action
	 * @throws IOException
	 */
	public void interperate() throws IOException {
		char currChar = myProgram[row][col];
		// string mode. The ASCII value is pushed instead of any action
		if (stringMode && currChar != '\"') {
			// Handling escape char
			if (currChar == '\\') {
				move();
				switch (myProgram[row][col]) {
				case 'n':
					currChar = '\n';
					break;
				case 't':
					currChar = '\t';
					break;
				case 'b':
					currChar = '\b';
					break;
				case '"':
					currChar = '"';
					break;
				default:
					currChar = myProgram[row][col];
					break;
				}

			}
			push((int) currChar);
		}
		// literal numbers.
		else if (currChar >= '0' && currChar <= '9')
			push(currChar - '0');
		else {
			// everything else...
			switch (currChar) {
			case '+':
				push(pop() + pop());
				break;
			case '-':
				int fst = pop();
				int snd = pop();
				push(snd - fst);
				break;
			case '*':
				push(pop() * pop());
				break;
			case '/':
				int fst2 = pop();
				int snd2 = pop();
				push(snd2 / fst2);
				break;
			case '%':
				int fst3 = pop();
				int snd3 = pop();
				push(snd3 % fst3);
				break;
			case '!':
				push(pop() == 0 ? 1 : 0);
				break;
			case '`':
				int fst4 = pop();
				int snd4 = pop();
				push(snd4 > fst4 ? 1 : 0);
				break;
			case '>':
				myDirection = Direction.RIGHT;
				break;
			case '<':
				myDirection = Direction.LEFT;
				break;
			case '^':
				myDirection = Direction.UP;
				break;
			case 'v':
				myDirection = Direction.DOWN;
				break;
			case '?':
				int rand = new Random().nextInt(4);
				myDirection = rand == 0 ? Direction.UP
						: rand == 1 ? Direction.RIGHT
								: rand == 2 ? Direction.DOWN : Direction.LEFT;
				break;
			case '_':
				myDirection = pop() == 0 ? Direction.RIGHT : Direction.LEFT;
				break;
			case '|':
				myDirection = pop() == 0 ? Direction.DOWN : Direction.UP;
				break;
			case '\"':
				stringMode = !stringMode;
				break;
			case ':':
				int peek = pop();
				push(peek);
				push(peek);
				break;
			case '\\':
				int temp = pop();
				int temp2 = pop();
				push(temp);
				push(temp2);
				break;
			case '$':
				pop();
				break;
			case '.':
				System.out.print(pop());
				break;
			case ',':
				int popped = pop();
				System.out.print((char) popped);
				break;
			case '#':
				move();
				break;
			case 'g':
				push((int) myProgram[pop()][pop()]);
				break;
			case 'p':
				myProgram[pop()][pop()] = (char) pop().intValue();
				break;
			case '&':
				BufferedReader in = new BufferedReader(new InputStreamReader(
						System.in));
				push(Integer.parseInt(in.readLine()));
				break;
			case '~':
				BufferedReader in2 = new BufferedReader(new InputStreamReader(
						System.in));
				push(in2.read());
				break;
			case '@':
				hasEnded = true;
				break;

			}
		}

		if (!hasEnded)
			move();

	}

	// make running code simpler
	public static void runBefunge(File source) throws IOException {
		BefungeInterpreter bi = new BefungeInterpreter();
		bi.loadProgram(source);
		while (!bi.hasEnded) {
			bi.interperate();
		}
	}

	public static void main(String[] args) throws IOException {
		runBefunge(new File("test.txt"));
	}

}

/**
 * ##Bonus Challenges
 * 
 * 
 * #High/Low game
 * 
 * v v ,,,,&lt;
 * &gt;:" :rebmun a retnE",,,,,,,,,,,,,,,,&amp;-:#v_$"!tcerroC"&gt;#v,,,,&lt;
 * v&lt;&lt; &gt;&gt;v ^ "Low!n\"_v#`0&lt; @ v&lt;2^&lt; &gt;^2&gt;v ^
 * ,"High!n\"&lt; v&lt;&lt;1?3^ ^1?3&gt;v v&lt; 4 ^ v ^ 4&gt;v v5?&lt;?&lt;
 * ?9&gt;?&gt;?5v &gt;v 6 v v 6v&lt; &gt;&gt;v7?8v v7?8v&lt;
 * &gt;v9v&lt;9&gt;v9v&lt; &gt;v&lt; 9 &gt;&gt;&gt;v v&lt; + &gt;v v&lt;1 v
 * 7&gt;v v2?&lt;?&gt;?8&gt;v &gt;v3 v 9&gt;^+ &gt;v4?6&gt;^ &gt; ^ &gt;v5&gt;^
 * &gt;&gt;^
 * 
 * #99 Bottles
 * 
 * &gt;9919+*+:v,, &gt;#,,,,,,,,,&lt;
 * &gt;:." .llaw eht no reeb fo selttob "#v,,,,,,,,,,,,,,,&lt;
 * &gt;,,,,,,,,,v#" bottles of beer! ".:&lt; ,
 * &gt;1-::"!dnuora ti ssap ,nwod eno ekaT"#v,,,,,,,,,,,,,,,&lt; ^
 * ^,,,,,,,,," bottles of beer on the wall! "._v#&lt; &gt;
 * &gt;,,,,,,,,,,,,,v#,," No more bottles of beer on the wall!"&lt;
 * &gt;"!llaw eht no reeb fo selttob 99 ,erom emos yub dna pohs eht ot og"
 * &gt;:#,_^ ^ ,,,,,,,,,,&lt;
 */
