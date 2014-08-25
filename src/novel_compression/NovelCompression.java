package novel_compression;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Pattern;

import static java.util.Arrays.binarySearch;

/**
 * Novel Compression, Reddit Daily Programmer Challenge 162.
 * 
 * Input Description: The program will take 3 arguments on the command line: the
 * first one will be one of the following: -c Will compress the input. -d Will
 * decompress the input. If it is anything other than these, return an error
 * message. The second argument will be a path to a file that the input data
 * will be read from, and the third argument will be a path to a file that
 * output data will be written to. If there are any more or less than three
 * arguments given, return another error message.
 * 
 * Output Description: Using the given operation (compress or decompress), the
 * data in the input file will be processed, and the resulting data written to
 * the output file.
 * 
 */

public class NovelCompression {

	public enum ACTION {
		COMPRESS, DECOMPRESS
	}

	public enum TYPE {
		UPPER, LOWER, FIRSTCAP
	}

	// One variable to controller behavior
	private ACTION action;
	// Dictionary of words
	private String[] dictionary;
	// Text to compress/decompress.
	private ArrayList<String> text = new ArrayList<String>();

	// For dictionary's creation.
	private HashSet<String> dictionaryWords = new HashSet<String>(50);

	public NovelCompression(String file, ACTION action) throws IOException {
		this.action = action;
		BufferedReader br = new BufferedReader(new FileReader(file));
		String aux;

		switch (action) {
		case DECOMPRESS:
			dictionary = new String[Integer.valueOf(br.readLine())];

			// Load of dictionary.
			for (int i = 0; i < dictionary.length; dictionary[i++] = br.readLine());

			// Load of text.
			while ((aux = br.readLine()) != null)
				text.add(aux);

			br.close();

			decompress();
			break;
		case COMPRESS:
			while ((aux = br.readLine()) != null)
				text.add(aux);
			br.close();

			compress();
			break;
		default:
			break;
		}
	}

	public void compress() {
		createDictionary();
		for (int i = 0; i < text.size(); i++) {
			text.set(i, compressLine(text.get(i)));
		}
		// EOF
		String newValue = text.get(text.size() - 1) + "E ";
		text.set(text.size() - 1, newValue);
	}

	private void createDictionary() {
		for (String line : text) {
			String[] words = line.split(" ");
			for (String word : words) {

				// Remove special characters.
				if (Pattern.matches("[a-zA-Z]+[.|,|?|!|;|:]", word)) {
					dictionaryWords.add(word.substring(0, word.length() - 1)
							.toLowerCase());

				} else {
					dictionaryWords.add(word.toLowerCase());
				}
			}
		}

		dictionary = new String[dictionaryWords.size()];
		int i = 0;
		for (String word : dictionaryWords) {
			dictionary[i++] = word;
		}

		//Alphabetize dictionary
		Arrays.sort(dictionary);
	}

	private String compressLine(String line) {
		String result = "";
		String[] splittedLine = line.split(" ");
		for (String word : splittedLine) {
			if (Pattern.matches("[a-zA-Z]+[.|,|?|!|;|:]", word)) {
				result += binarySearch(dictionary,
						word.substring(0, word.length() - 1).toLowerCase());
				switch (typeOfWord(word.substring(0, word.length() - 1))) {
				case FIRSTCAP:
					result += "^";
					break;
				case UPPER:
					result += "!";
					break;
				default:
					break;
				}
				result += " " + String.valueOf(word.charAt(word.length() - 1))
						+ " ";
			} else if (word.contains("-")) {
				String[] subWords = word.split("-");
				for (String sWord : subWords) {
					result += binarySearch(dictionary, word.toLowerCase());
					switch (typeOfWord(word)) {
					case FIRSTCAP:
						result += "^";
						break;
					case UPPER:
						result += "!";
						break;
					default:
						break;
					}
					result += " " + "-";
				}
				// Remove last "-"
				result = result.substring(0, result.length() - 1);
			} else {
				result += binarySearch(dictionary, word.toLowerCase());
				switch (typeOfWord(word.substring(0, word.length()))) {
				case FIRSTCAP:
					result += "^";
					break;
				case UPPER:
					result += "!";
					break;
				default:
					break;
				}
				result += " ";
			}
		}
		return result += "R ";
	}

	private TYPE typeOfWord(String word) {
		if (word.equals(word.toLowerCase())) {
			return TYPE.LOWER;
		} else if (word.equals(word.toUpperCase())) {
			return TYPE.UPPER;
		} else {
			return TYPE.FIRSTCAP;
		}
	}

	public void decompress() {
		for (int i = 0; i < text.size(); i++) {
			String chunk = text.get(i);
			String[] line = chunk.split(" ");
			String result = "";

			for (String word : line) {
				if (Pattern.matches("[0-9]+[!|^]?", word)) {

					char lastChar = word.charAt(word.length() - 1);
					int lastIndex = (word.endsWith("^") || word.endsWith("!")) ? 1
							: 0;
					int dictIndex = Integer.valueOf(word.substring(0,
							word.length() - lastIndex));

					switch (lastChar) {
					case '^':
						result += firstLetterToUpper(dictionary[dictIndex])
								+ " ";
						break;
					case '!':
						result += dictionary[dictIndex].toUpperCase() + " ";
						break;
					default:
						result += dictionary[dictIndex] + " ";
						break;
					}

				} else if (word.equals("R")) {
					/* result += System.getProperty("line.separator"); */
				} else if (word.equals("-")) {
					result = result.substring(0, result.length() - 1) + "-";
				} else if (Pattern.matches("[.|,|?|!|;|:]", word)) {
					result = result.substring(0, result.length() - 1) + word
							+ " ";
				}
			}
			text.set(i, result);
		}
	}

	public void saveToFile(String fileName) throws IOException {
		PrintWriter pw = new PrintWriter(new FileWriter(fileName));

		if (action.equals(ACTION.COMPRESS)) {
			pw.println(dictionary.length);
			for (String word : dictionary) {
				pw.println(word);
			}
		}

		for (String line : text) {
			pw.println(line);
		}

		pw.close();
	}

	public String firstLetterToUpper(String toFormat) {
		String result = toFormat.substring(0, 1).toUpperCase();
		result += toFormat.substring(1);
		return result;
	}

	public static void main(String[] args) {
		ACTION act = null;

		switch (args[0]) {
		case "-c":
			act = ACTION.COMPRESS;
			break;
		case "-d":
			act = ACTION.DECOMPRESS;
			break;
		default:
			System.err.println("java NovelCompress [-c|-d] <file in> <file out>");
			System.exit(-1);
		}

		try {

			NovelCompression test = new NovelCompression(args[1], act);
			test.saveToFile(args[2]);

		} catch (IOException e) {
			System.err.println("Something went wrong: " + e.getMessage());
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err.println("java NovelCompress [-c|-d] <file in> <file out>");
			System.exit(-1);
		}
	}
}