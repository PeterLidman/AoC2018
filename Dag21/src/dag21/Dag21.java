package dag21;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Dag21 {
//	   #ip 4
//	 0 seti 123 0 3             r3 = 123                          endast r0 kan påverkas!
//	 1 bani 3 456 3             r3 = r3 & 456
//	 2 eqri 3 72 3              r3 = r3 == 72 (123 & 456 = 72)
//	 3 addr 3 4 4               r4 = r3 + r4        hoppa till 5 om 72 ovan
//	 4 seti 0 0 4               r4 = 0              hoppa till 1
//	 5 seti 0 5 3               r3 = 0
//	 6 bori 3 65536 2           r2 = r3 | 65536 
//	 7 seti 10736359 9 3        r3 = 10736359       7: start
//	 8 bani 2 255 1             r1 = r2 & 255
//	 9 addr 3 1 3               r3 = r3 + r1
//	10 bani 3 16777215 3        r3 = r3 & 16777215 
//	11 muli 3 65899 3           r3 = r3 * 65899
//	12 bani 3 16777215 3        r3 = r3 & 16777215
//	13 gtir 256 2 1             r1 = 256 > r2
//	14 addr 1 4 4               r4 = r1 + r4        hoppa till 16 om 256 > r2 dvs hoppa till 28
//	15 addi 4 1 4               r4 = r4 + 1         hoppa till 17
//	16 seti 27 2 4              r4 = 27             hoppa till 28
//	17 seti 0 3 1               r1 = 0
//	18 addi 1 1 5               r5 = r1 + 1         18: start
//	19 muli 5 256 5             r5 = r5 * 256
//	20 gtrr 5 2 5               r5 = r5 > r2
//	21 addr 5 4 4               r4 = r5 + r4        hoppa till 23 om r5 > r2 dvs hoppa till 26
//	22 addi 4 1 4               r4 = r4 + 1         hoppa till 24 
//	23 seti 25 8 4              r4 = 25             hoppa till 26
//	24 addi 1 1 1               r1 = r1 + 1
//	25 seti 17 6 4              r4 = 17             hoppa till 18
//	26 setr 1 5 2               r2 = r1
//	27 seti 7 7 4               r4 = 7              hoppa till 8
//	28 eqrr 3 0 1               r1 = r3 == r0
//	29 addr 1 4 4               r4 = r1 + r4        hoppa till end program
//	30 seti 5 1 4               r4 = 5              hoppa till 6

	static List<String> program = new ArrayList<>();
	static int ip = 0;
	static int pc = 0;
	static int r[] = new int[6];
	static int eop = 0;
	static int i[] = new int[4];
	static HashSet<Integer> p = new HashSet<>();

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag21\\src\\dag21\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		str = br.readLine();
		ip = Integer.parseInt(str.substring(4, 5));
		while ((str = br.readLine()) != null) {
			program.add(str);
		}
		br.close();
		eop = program.size();

		String[] instr;
		do {
			r[ip] = pc;
			// System.out.println("ip=" + pc +"[" + r[0] + "," + r[1] + "," + r[2] + "," +
			// r[3]+ "," + r[4]+ "," + r[5] + "]");
			instr = program.get(pc).substring(5).split(" ");
			i[0] = 0;
			i[1] = Integer.valueOf(instr[0]);
			i[2] = Integer.valueOf(instr[1]);
			i[3] = Integer.valueOf(instr[2]);
//			System.out.print(program.get(pc).substring(0, 4) + " " + i[1] + " " + i[2] + " " + i[3] + " ");
			switch (program.get(pc).substring(0, 4)) {
			case "addr":
				r[i[3]] = r[i[1]] + r[i[2]];
				break;
			case "addi":
				r[i[3]] = r[i[1]] + i[2];
				break;
			case "mulr":
				r[i[3]] = r[i[1]] * r[i[2]];
				break;
			case "muli":
				r[i[3]] = r[i[1]] * i[2];
				break;
			case "banr":
				r[i[3]] = r[i[1]] & r[i[2]];
				break;
			case "bani":
				r[i[3]] = r[i[1]] & i[2];
				break;
			case "borr":
				r[i[3]] = r[i[1]] | r[i[2]];
				break;
			case "bori":
				r[i[3]] = r[i[1]] | i[2];
				break;
			case "setr":
				r[i[3]] = r[i[1]];
				break;
			case "seti":
				r[i[3]] = i[1];
				break;
			case "gtir":
				r[i[3]] = i[1] > r[i[2]] ? 1 : 0;
				break;
			case "gtri":
				r[i[3]] = r[i[1]] > i[2] ? 1 : 0;
				break;
			case "gtrr":
				r[i[3]] = r[i[1]] > r[i[2]] ? 1 : 0;
				break;
			case "eqir":
				r[i[3]] = i[1] == r[i[2]] ? 1 : 0;
				break;
			case "eqri":
				r[i[3]] = r[i[1]] == i[2] ? 1 : 0;
				break;
			case "eqrr":
				r[i[3]] = r[i[1]] == r[i[2]] ? 1 : 0;
				if (p.add(r[i[1]])) {
					System.out.println("r0 to match " + r[i[1]]);// first is part1, the last is part2
				}
				break;
			default:
				System.out.println("got unknown opcode");
			}
//			System.out.println("ip=" + pc + "[" + r[0] + "," + r[1] + "," + r[2] + "," + r[3] + "," + r[4] + "," + r[5] + "]");
			pc = r[ip] + 1;
		} while (pc < eop);
		System.out.println("End of program r0= " + r[0]);
	}
}
