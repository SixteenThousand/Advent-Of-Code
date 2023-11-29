import java.util.*;
import java.io.*;
import java.util.regex.*;

class Day2 {
	public static void main(String[] args) {
		System.out.println(tobogganCountValid("./Day2-input.txt"));
	}
	
	public static int sledCountValid(String path) {
		try {
			File fp = new File(path);
			Scanner scan = new Scanner(fp);
			
			String line;
			Pattern p = Pattern.compile("(\\d+)-(\\d+) ([a-z]{1}): ([a-z]+)");
			Matcher m;
			int low, high, n, result = 0;
			do {
				line = scan.nextLine();
				m = p.matcher(line);
				if(!m.matches())
					break;
				low = Integer.parseInt(m.group(1));
				high = Integer.parseInt(m.group(2));
				n = count(m.group(4),m.group(3).charAt(0));
				if(low <= n && n <= high)
					++result;
			} while(scan.hasNextLine());
			return result;
		} catch(FileNotFoundException e) {
			return -1;
		}
	}
	
	public static int count(String s, char target) {
		int result = 0;
		for(char c: s.toCharArray())
			if(c == target)
				++result;
		return result;
	}
	
	public static int tobogganCountValid(String path) {
		try {
			File fp = new File(path);
			Scanner scan = new Scanner(fp);
			
			String line;
			Pattern p = Pattern.compile("(\\d+)-(\\d+) ([a-z]{1}): ([a-z]+)");
			Matcher m;
			int low, high, result = 0;
			do {
				line = scan.nextLine();
				m = p.matcher(line);
				if(!m.matches())
					break;
				low = Integer.parseInt(m.group(1));
				high = Integer.parseInt(m.group(2));
				if(tobogganValid(low,high,m.group(3).charAt(0),m.group(4)))
					++result;
			} while(scan.hasNextLine());
			return result;
		} catch(FileNotFoundException e) {
			return -1;
		}
	}
	
	public static boolean tobogganValid(
			int i, int j, char c, String password) {
		return password.length() >= j && 
			(password.charAt(i-1) == c ^ password.charAt(j-1) == c);
	}
}
