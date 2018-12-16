package dag16;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Dag16 {
	static List<String> input = new ArrayList<>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag16\\src\\dag16\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			input.add(str);
		}
		br.close();

		int r[] = new int[4];
		int rr[] = new int[4];
		HashSet<String> badMatch = new HashSet<String>();
		HashSet<String> goodMatch = new HashSet<String>();
		int opCodeNumber = 0;
		int i[] = new int[4];
		// get sample
		String[] instr;
		int sampleBehaveLikeThreeOrMore = 0;
		int test = 0;

		while (input.get(test).length() > 0) {
//				System.out.println("row " + test + " " + input.get(test));
			int matchingResults = 0;
			for (int o = 0; o < 16; o++) {// testa alla opcodes
				// read test
				instr = input.get(test).substring(9, input.get(test).length() - 1).split(",");
				r[0] = Integer.valueOf(instr[0].trim());
				r[1] = Integer.valueOf(instr[1].trim());
				r[2] = Integer.valueOf(instr[2].trim());
				r[3] = Integer.valueOf(instr[3].trim());
//					System.out.println("read registers " + r[0] + " " + r[1] + " " + r[2] + " " + r[3]);
				instr = input.get(test + 1).split(" ");
				i[0] = Integer.valueOf(instr[0]);
				i[1] = Integer.valueOf(instr[1]);
				i[2] = Integer.valueOf(instr[2]);
				i[3] = Integer.valueOf(instr[3]);
//					System.out.println("read instr " + i[0] + " " + i[1] + " " + i[2] + " " + i[3]);
				instr = input.get(test + 2).substring(9, input.get(test + 2).length() - 1).split(",");
				rr[0] = Integer.valueOf(instr[0].trim());
				rr[1] = Integer.valueOf(instr[1].trim());
				rr[2] = Integer.valueOf(instr[2].trim());
				rr[3] = Integer.valueOf(instr[3].trim());
//					System.out.println("read results " + rr[0] + " " + rr[1] + " " + rr[2] + " " + rr[3]);
				// run instruction
				opCodeNumber = o;
//					System.out.println("testar opcode=" + opCodeNumber);
				switch (opCodeNumber) {
				case 10:// addr
					r[i[3]] = r[i[1]] + r[i[2]];
					break;
				case 6:// addi
					r[i[3]] = r[i[1]] + i[2];
					break;
				case 9:// mulr
					r[i[3]] = r[i[1]] * r[i[2]];
					break;
				case 0:// muli
					r[i[3]] = r[i[1]] * i[2];
					break;
				case 14:// banr
					r[i[3]] = r[i[1]] & r[i[2]];
					break;
				case 2:// bani
					r[i[3]] = r[i[1]] & i[2];
					break;
				case 11:// borr
					r[i[3]] = r[i[1]] | r[i[2]];
					break;
				case 12:// bori
					r[i[3]] = r[i[1]] | i[2];
					break;
				case 15:// setr
					r[i[3]] = r[i[1]];// obs b irrelevant
					break;
				case 1:// seti
					r[i[3]] = i[1];// obs b irrelevant
					break;
				case 7:// gtir
					r[i[3]] = i[1] > r[i[2]] ? 1 : 0;
					break;
				case 3:// gtri
					r[i[3]] = r[i[1]] > i[2] ? 1 : 0;
					break;
				case 4:// gtrr
					r[i[3]] = r[i[1]] > r[i[2]] ? 1 : 0;
					break;
				case 8:// eqir
					r[i[3]] = i[1] == r[i[2]] ? 1 : 0;
					break;
				case 13:// eqri
					r[i[3]] = r[i[1]] == i[2] ? 1 : 0;
					break;
				case 5:// eqrr
					r[i[3]] = r[i[1]] == r[i[2]] ? 1 : 0;
					break;
				default:
					System.out.println("got unknown opcode");
				}
//					System.out.println("comp results " + r[0] + " " + r[1] + " " + r[2] + " " + r[3]);
				// compare registers
				if ((r[0] == rr[0]) && (r[1] == rr[1]) && (r[2] == rr[2]) && (r[3] == rr[3])) {
					matchingResults++;
					if ( i[0] == 11 || i[0] == 12 ) {
						goodMatch.add("" + opCodeNumber + " " + i[0]);
					}
				} else {
					badMatch.remove("" + opCodeNumber + " " + i[0]);
				}
			}
			// finns det 3 eller fler matchande resultat?
			if (matchingResults > 2) {
				sampleBehaveLikeThreeOrMore++;
			}
			test += 4;
		}
		for (String s : badMatch) {
			goodMatch.remove(s);
		}

		List<String> sorted = new ArrayList<String>(goodMatch);
		Collections.sort(sorted);
		for (String s : sorted) {
			System.out.println(s);
		}

		System.out.println("Det fanns " + sampleBehaveLikeThreeOrMore + " samples som passade >293 >368 <705");
		
		// del2	

		r[0] = 0;
		r[1] = 0;
		r[2] = 0;
		r[3] = 0;

		test = 3110;
		while (test < 4048) {
//			System.out.println("row " + test + " " + input.get(test));

			instr = input.get(test).split(" ");
			i[0] = Integer.valueOf(instr[0]);
			i[1] = Integer.valueOf(instr[1]);
			i[2] = Integer.valueOf(instr[2]);
			i[3] = Integer.valueOf(instr[3]);
//			System.out.println("read instr " + i[0] + " " + i[1] + " " + i[2] + " " + i[3]);

			opCodeNumber = i[0];

			switch (opCodeNumber) {
			case 10:// addr
				r[i[3]] = r[i[1]] + r[i[2]];
				break;
			case 6:// addi
				r[i[3]] = r[i[1]] + i[2];
				break;
			case 9:// mulr
				r[i[3]] = r[i[1]] * r[i[2]];
				break;
			case 0:// muli
				r[i[3]] = r[i[1]] * i[2];
				break;
			case 14:// banr
				r[i[3]] = r[i[1]] & r[i[2]];
				break;
			case 2:// bani
				r[i[3]] = r[i[1]] & i[2];
				break;
			case 11:// borr
				r[i[3]] = r[i[1]] | r[i[2]];
				break;
			case 12:// bori
				r[i[3]] = r[i[1]] | i[2];
				break;
			case 15:// setr
				r[i[3]] = r[i[1]];// obs b irrelevant
				break;
			case 1:// seti
				r[i[3]] = i[1];// obs b irrelevant
				break;
			case 7:// gtir
				r[i[3]] = i[1] > r[i[2]] ? 1 : 0;
				break;
			case 3:// gtri
				r[i[3]] = r[i[1]] > i[2] ? 1 : 0;
				break;
			case 4:// gtrr
				r[i[3]] = r[i[1]] > r[i[2]] ? 1 : 0;
				break;
			case 8:// eqir
				r[i[3]] = i[1] == r[i[2]] ? 1 : 0;
				break;
			case 13:// eqri
				r[i[3]] = r[i[1]] == i[2] ? 1 : 0;
				break;
			case 5:// eqrr
				r[i[3]] = r[i[1]] == r[i[2]] ? 1 : 0;
				break;
			default:
				System.out.println("got unknown opcode");
			}
			test++;
		}
		System.out.println("r0=" + r[0]);
	}
}
