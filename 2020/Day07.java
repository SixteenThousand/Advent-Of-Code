import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
// import java.util.stream;

public class Day7 {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println(contentsSize("shiny gold"));
	}
	
	public static Set<String> containers(String colour) 
			throws FileNotFoundException {
		File fp = new File("./Day7-input.txt");
		Scanner scan = new Scanner(fp);
		Pattern p = Pattern.compile(
				String.format("(.*) bags contain .* %s .*",colour));
		Matcher m;
		Set<String> result = new HashSet<String>();
		Set<String> directContainers = new HashSet<String>();
		String line;
		do {
			line = scan.nextLine();
			m = p.matcher(line);
			if(m.matches()) {
				directContainers.add(m.group(1));
				result.add(m.group(1));
			}
		} while(scan.hasNextLine());
		for(String container: directContainers) {
			result.addAll(containers(container));
		}
		scan.close();
		return result;
	}
	
	public static int contentsSize(String colour)
			throws FileNotFoundException {
		int result = 0;
		File fp = new File("./Day7-input.txt");
		Scanner scan = new Scanner(fp);
		Pattern container = Pattern.compile(
				String.format("%s bags contain .*",colour)
		);
		Pattern contentPat = Pattern.compile("( ?)(\\d+) (.*) bag(s?)(\\.?)");
		String line;
		String contentsInfo;
		Matcher m;
		int numBags;
		do {
			line = scan.nextLine();
			m = container.matcher(line);
			if(m.matches()) {
				contentsInfo = line.split("bags contain")[1];
				for(String content: contentsInfo.split(", ")) {
					m = contentPat.matcher(content);
					if(m.matches()) {
						numBags = Integer.parseInt(m.group(2));
						System.out.printf("debug: [[%s]];%d\n",content,numBags);
						result += numBags + 
							numBags*contentsSize(m.group(3));
					} else {
						System.out.printf("debug: ||%s||\n",content);
					}
				}
				break;
			}
		} while(scan.hasNextLine());
		return result;
	}
}
