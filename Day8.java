import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Day8 {
	public static void main(String[] args) throws FileNotFoundException {
		String s = "./Day8-input.txt", t = "./gija.txt";
		File fp = new File(s);
		Scanner reader = new Scanner(fp);
		List<String> program = new ArrayList<String>();
		do {
			program.add(reader.nextLine());
		} while(reader.hasNextLine());
		Game g = new Game(program);
		System.out.println(g.findErrorLine());
	}
}

class Game {
	private int accumulator;
	private int lineNum;
	private List<Integer> linesRun;
	private final Pattern cmdPat = Pattern.compile("(.*) (-|\\+)(\\d+)");
	private List<String> program;
	private final String NOP = "nop";
	private final String ACC = "acc";
	private final String JMP = "jmp";
	private boolean terminated;
	
	public Game(List<String> program) {
		this.program = program;
		accumulator = 0;
		lineNum = 0;
		linesRun = new ArrayList<Integer>();
		terminated = false;
	}
	
	public void runCmd() {
		if(lineNum >= program.size()) {
			System.out.printf(
					"Game booted! Accumulator has value <%d>\n",
					accumulator
			);
			terminated = true;
			return;
		}
		String cmd = program.get(lineNum);
		Matcher m = cmdPat.matcher(cmd);
		if(m.matches()) {
			linesRun.add(lineNum);
			int cmdParam = Integer.parseInt(m.group(3)) * 
				(m.group(2).equals("+") ? 1:-1);
			switch(m.group(1)) {
				case JMP:
					lineNum += cmdParam;
					break;
				case ACC:
					accumulator += cmdParam;
					++lineNum;
					break;
				case NOP:
					++lineNum;
					break;
				default:
					System.out.println("Switch error.");
					System.exit(1);
			}
		} else {
			System.out.println("Your code is bad. try again.");
			System.exit(1);
		}
	}
	
	public void reset() {
		accumulator = 0;
		lineNum = 0;
		linesRun = new ArrayList<Integer>();
		terminated = false;
	}
	
	public int getAcc() {
		return accumulator;
	}
	
	public boolean findInfLoop() {
		// returns true iff the game eventually ends up in an infinite loop
		while(!linesRun.contains(lineNum)) {
			runCmd();
			// System.out.printf("debug; accumluator= %d\n",accumulator);
			if(terminated) {
				break;
			}
		}
		return linesRun.contains(lineNum);
	}
	
	public void swapCmd(int swpLine) {
		// converts a jmp to a nop & vice-versa
		if(program.get(swpLine).startsWith(NOP)) {
			program.set(swpLine,program.get(swpLine).replace(NOP,JMP));
		} else if(program.get(swpLine).startsWith(JMP)) {
			program.set(swpLine,program.get(swpLine).replace(JMP,NOP));
		}
	}
	
	public int findErrorLine() {
		// finds the line number of the one line that needs to be changed
		// 	to make the program run without an infinite loop
		findInfLoop();
		List<Integer> linesInInfLoop = linesRun;
		reset();
		for(int i=linesInInfLoop.size()-1; i>=0; --i) {
			swapCmd(linesInInfLoop.get(i));
			// System.out.printf("debug; program = %s\n",program.toString());
			if(!findInfLoop()) {
				return i;
			}
			swapCmd(linesInInfLoop.get(i));
			reset();
		}
		// if we've got here, there is no infinite loop!
		return -1;
	}
}
