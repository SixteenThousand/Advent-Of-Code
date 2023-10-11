import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
// import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;

public class Day6 {
	public static void main(String[] args) {
		// List<String> ans = new ArrayList<String>();
		// ans.add("abef");
		// ans.add("acd");
		// ans.add("acz");
		System.out.printf("Answer: %d\n",part2());
	}

	public static String yesQs(StringBuilder groupQs) {
		StringBuilder result = new StringBuilder();
		char c = 'a';
		while(c != 'z'+1) {
			if(groupQs.indexOf(String.valueOf(c)) > -1) {
				result.append(c);
			}
			++c;
		}
		return result.toString();
	}
	public static String yesQs(String groupQs) {
		return yesQs(new StringBuilder(groupQs));
	}

	public static int part1() {
		try {
			File fp = new File("./Day6-input.txt");
			Scanner scan = new Scanner(fp);
			StringBuilder groupAns = new StringBuilder();
			String line;
			int sum = 0;
			while(scan.hasNextLine()) {
				line = scan.nextLine();
				groupAns.append(line);
				if(!scan.hasNextLine()) {
					System.out.printf("\n[[%s]]\n",groupAns.toString());
				}
				if(line.strip() == "" || !scan.hasNextLine()) {
					// System.out.printf("\n[[%s]]\n",groupAns.toString());
					sum += yesQs(groupAns).length();
					groupAns.delete(0,groupAns.capacity());
				}
			}
			scan.close();
			return sum;
		} catch(FileNotFoundException e) {
			System.exit(1);
			return -1;
		}
	}

	public static int allYes(List<String> answers) {
		int result = 26;
		boolean everyoneYes = false;
		for(int i=0; i<26; ++i) {
			for(String answer: answers) {
				if(answer.indexOf('a'+i) == -1) {
					--result;
					break;
				}
			}
		}
		return result;
	}

	public static int part2() {
		try {
			File fp = new File("./Day6-input.txt");
			Scanner scan = new Scanner(fp);
			List<String> answers = new ArrayList<String>();
			String line;
			int sum = 0;
			while(true) {
				line = scan.nextLine();
				if(line == "") {
					sum += allYes(answers);
					answers.clear();
					continue;
				}
				if(!scan.hasNextLine()) {
					sum += allYes(answers);
					break;
				}
				answers.add(line);
			}
			return sum;
		} catch(FileNotFoundException e) {
			System.out.println("darn.");
			System.exit(1);
			return -1;
		}
	}
}
