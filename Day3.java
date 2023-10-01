import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;

class Day3 {
	public static void main(String[] args) {
		int[][] slopes = new int[][] {{1,1}, {3,1}, {5,1}, {7,1}, {1,2}};
		int treeTotal;
		long answer = 1;
		for(int[] slope: slopes) {
			treeTotal = numTrees(slope[0], slope[1]);
			System.out.println(treeTotal);
			answer *= treeTotal;
		}
		System.out.printf("Answer: %d\n",answer);
	}
	
	public static List<String> getSlope() {
		try {
			List<String> result = new ArrayList<String>();
			File fp = new File("./Day3Input.txt");
			Scanner scan = new Scanner(fp);
			while(scan.hasNextLine())
				result.add(scan.nextLine());
			return result;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
		return new ArrayList<String>();
	}

	public static int numTrees(int right, int down) {
		List<String> slope = getSlope();
		int row = 0;
		int col = 0;
		int result = 0;
		while(row<slope.size()) {
			if(slope.get(row).charAt(col) == '#')
				++result;
			row += down;
			col = (col+right)%slope.get(0).length();
		}
		return result;
	}

	public static int product(List<Integer> arr) {
		int result = 1;
		for(int x: arr)
			result *= x;
		return result;
	}
}
