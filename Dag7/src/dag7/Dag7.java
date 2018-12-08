package dag7;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Dag7 {
	static List<String> steps = new ArrayList<String>();
	static List<String> steps2 = new ArrayList<String>();
	static String lastStep = "";
	static String answer = "";
	static int totalTime = 0;

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag7\\src\\dag7\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			steps2.add((str.substring(5, 6) + str.substring(36, 37)));
		}
		br.close();
		del1();
		del2();
	}

	public static void del1() {
		String c = "";
		steps.clear();
		for (String s : steps2) {
			steps.add(s);
		}
		steps.sort(Comparator.naturalOrder());

		do {
			System.out.println("size left: " + steps.size());
			c = getCandidates();
			System.out.println("candidates " + c);
			if (c.length() > 0) {
				answer += c.substring(0, 1);
				removeStepsThatBeginsWith(c.substring(0, 1));
			} else {
				break;
			}

			System.out.println("steps left: " + steps.size());
		} while (steps.size() > 0);

		answer += lastStep;
		System.out.println("Answer: " + answer);
		System.out.println("NOT! BCEFLDMQTXHZGKIV");
		System.out.println("NOT! BCEFLDMQTXHZGKIAV");
		System.out.println("NOT! BCEFLDMQTXHZGKIASVJYORPUW");
	}

	public static void del2() {
		answer = "";
		String c = "";
		Workers w = new Workers();
		steps.clear();
		for (String s : steps2) {
			steps.add(s);
		}
		steps.sort(Comparator.naturalOrder());

		do {
			System.out.println("size left: " + steps.size());
			c = getCandidates();
			System.out.println("candidates " + c);
			if (c.equals("")) {
				if (lastStep.length() > 0) {
					c = lastStep;
					lastStep = "";
				} else {
					break;
				}
			}
			c = cleanOutDuplicates(c);
			while (w.anyFree() && c.length() > 0) {
				w.startWorkOn(c.substring(0, 1));
				c = c.substring(1);
			}
			c = "";
			do {
				w.print(totalTime);
				c = w.tickAndReportWorkDone();
				totalTime++;
			} while (c.equals(""));
			c = sortString(c);
			for (int i = 0; i < c.length(); i++) {
				answer += c.substring(0, 1);
				removeStepsThatBeginsWith(c.substring(0, 1));
			}
			System.out.println("steps left: " + steps.size());
		} while (steps.size() > 0 || lastStep.length() > 0);
		System.out.println("Answer: " + answer);
		System.out.println("Time spent: " + totalTime);

	}

	public static String sortString(String inputString) {
		char tempArray[] = inputString.toCharArray();
		Arrays.sort(tempArray);
		return new String(tempArray);
	}

	public static String cleanOutDuplicates(String in) {
		char[] chars = in.toCharArray();
		Set<Character> charSet = new LinkedHashSet<Character>();
		for (char c : chars) {
			charSet.add(c);
		}

		StringBuilder sb = new StringBuilder();
		for (Character character : charSet) {
			sb.append(character);
		}
		return sb.toString();
	}

	private static void removeStepsThatBeginsWith(String s) {
		int i = 0;

		if (steps.size() == 0) {
			return;
		}
		do {
			if (s.equals(steps.get(i).substring(0, 1))) {
				lastStep = steps.get(i).substring(1);
				steps.remove(i);
				System.out.println("raderar " + i + " s= " + s);
				i--;
			}
			i++;
		} while (steps.size() > i);
	}

	private static String getCandidates() {
		int hits = 0;
		String starter = "";

		for (int i = 0; i < steps.size(); i++) {
			hits = 0;
			for (int j = 0; j < steps.size(); j++) {
				if (steps.get(i).substring(0, 1).equals(steps.get(j).substring(1))) {
					hits++;
				}
			}
			if (hits == 0) {
				starter = starter + steps.get(i).substring(0, 1);
			}
		}
		return starter;
	}

	public static String nextInstruction(String s) {
		String ret = "";

		for (String ss : steps) {
			if (s.equals(ss.substring(0, 1))) {
				ret = ret + ss.substring(1);
			}
		}
		return ret;
	}

}

class Workers {
	String task[] = new String[5];
	int timeLeft[] = new int[5];

	public Workers() {
		super();
		for (int i = 0; i < 5; i++) {
			task[i] = "";
			timeLeft[i] = 0;
		}
	}

	public void print(int tt) {
		String s = "tick=" + tt + " ";
		for (int i = 0; i < 5; i++) {
			s += "[" + task[i] + "," + timeLeft[i] + "]";
		}
		System.out.println(s);
	}

	public String tasksInProgress() {
		String s = "";
		for (int i = 0; i < 5; i++) {
			s += task[i];
		}
		return s;
	}

	public String tickAndReportWorkDone() {
		String ret = "";
		for (int i = 0; i < 5; i++) {
			if (--timeLeft[i] <= 0) {
				ret += task[i];
				task[i] = "";
			}
		}
		return ret;
	}

	public boolean anyFree() {
		return task[0].equals("") || task[1].equals("") || task[2].equals("") || task[3].equals("")
				|| task[4].equals("");
	}

	public void startWorkOn(String s) {
		int i = 0;

		if (s.equals(task[0]) || s.equals(task[1]) || s.equals(task[2]) || s.equals(task[3]) || s.equals(task[4])) {
			return;
		}
		while (i < 5 && !task[i].equals("")) {
			i++;
		}
		if (i >= 5) {
			System.out.println("Error, no free elfs");
			return;
		}
		task[i] = s;
		timeLeft[i] = (s.charAt(0) - 'A') + 61;
	}

}
