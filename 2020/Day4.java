import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.function.Predicate;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.String;


class Day4 {
	public static void main(String[] args) {
		System.out.println(numValidPassports("./Day4Input.txt", new isValid_p2()));
	}

	public static int numValidPassports(String filepath, Predicate<String> isValid) {
		try {
			File fp = new File(filepath);
			Scanner scan = new Scanner(fp);
			int result = 0;
			StringBuilder passport = new StringBuilder();
			String line;
			while(scan.hasNextLine()) {
				line = scan.nextLine();
				passport.append(" ");
				passport.append(line);
				if(line.strip() == "" || !(scan.hasNextLine())) {
					if(isValid.test(passport.toString())) {
						++result;
					} else {
						// System.out.println(passport.toString());
					}
					passport = new StringBuilder();
				}
			}
			return result;
		} catch(FileNotFoundException e) {
			System.out.println("uggggh");
			System.exit(1);
		}
		return -1;
	}
}


class isValid_p1 implements Predicate<String> {
	public boolean test(String passport) {
		String[] fields = new String[] {"byr:", "iyr:", "eyr:", "hgt:", "hcl:", "ecl:", "pid:"};
		for(String field: fields) {
			if(!passport.contains(field))
				return false;
		}
		return true;
	}
}

class isValid_p2 implements Predicate<String> {
	public boolean test(String passport) {
		isValid_p1 debug1 = new isValid_p1();
		
		try {
			File fp = new File("./Day4_Regexes.txt");
			Scanner scan = new Scanner(fp);
			Pattern p;
			Matcher m;
			while(scan.hasNextLine()) {
				String debug0 = scan.nextLine();
				p = Pattern.compile(debug0);
				m = p.matcher(passport);
				if(!m.matches()) {
					if(debug1.test(passport)) {
						System.out.printf("Pattern: [[%s]]\nData: %s\n\n",debug0,passport);
					}
					return false;
				}
			}
			return true;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		System.exit(1);
		return false;
	}
}
