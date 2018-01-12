import java.nio.file.Files;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;

public class WordSolver {

	public static void main(String[] args) throws Exception {
		if(args.length < 1){
			System.out.println("Please give me a word!! (Run like: gradle run '-Pcliargs=hello')");
			return;
		} else {
			System.out.println("Checking " + args[0] + "...");
		}

		String word = args[0];
		int word_length = word.length();
		int min_word_length = 3;

		ClassLoader loader = WordSolver.class.getClassLoader();
		String resourcePath = loader.getClass().getResource("/words.dict").getPath();
		System.out.println("words file:" + resourcePath);

		HashMap<String, Integer> orig_word_map = new HashMap<>();
		String[] chars = word.split("(?!^)");
		for (int i = 0; i < chars.length; i++) {
			if(orig_word_map.get(chars[i]) == null){
				orig_word_map.put(chars[i], 1);
			} else {
				orig_word_map.put(chars[i], orig_word_map.get(chars[i])+1);
			}
		}

		System.out.println("map:" + orig_word_map.toString());

		ArrayList<String> words = new ArrayList<>();
		
		try(BufferedReader br = new BufferedReader(new FileReader(resourcePath))) {
			for(String line; (line = br.readLine()) != null; ) {
				HashMap<String, Integer> word_map = (HashMap<String, Integer>)orig_word_map.clone();
				if(line.length() >= min_word_length && line.length() <= word_length){
					String[] c = line.split("(?!^)");
					boolean good = true;
					for (int i = 0; i < c.length; i++) {
						if(word_map.get(c[i]) == null){
							//System.out.println(line + " failed on invalid char.");
							good = false;
							break;
						} else if(word_map.get(c[i]).intValue() == 0){
							//System.out.println(line + " too many of the same char.");
							good = false;
							break;
						} else {
							//System.out.println(line + " removing a \"" +c[i]+"\"");
							word_map.put(c[i], word_map.get(c[i])-1);
						}
					}
					// If we've got to the end, then we have reached the end of the word and it has been ok, so show it.
					if(good)
						words.add(line);
				}
			}
		}

		Collections.sort(words, new LengthComparator());
		printList(words, 6);
	}

	private static void printList(List<String> words, int cols){
		int words_size = words.size();
		int longest_word_length = words.get(words_size-1).length()+4;
		for (int i = 0; i < words_size; i+=cols) {
			for (int j = 0; j < cols; j++) {
				if(i+j < words_size){
					System.out.print(String.format("%-"+longest_word_length+"s", words.get(i+j)));
				}
			}
			System.out.println("");
		}
	}

	private static void printList2(List<String> words, int cols){
		int words_size = words.size();
		int longest_word_length = words.get(words_size-1).length()+4;

		for (int c = 0; c <= cols; c++) {
			int index = 0;
			for (int i = 0; i < words_size; i++) {
				if(index < words_size) {
					System.out.print(String.format("%-"+longest_word_length+"s", words.get(index)));
				}
				index += c + cols;
			}
			System.out.println("");
		}
	}
}
