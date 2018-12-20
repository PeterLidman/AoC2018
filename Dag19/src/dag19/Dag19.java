package dag19;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag19 {
	static List<String> program = new ArrayList<>();
	static int ip = 0;
	static int pc = 0;
	static int r[] = new int[6];
	static int eop = 0;
	static int i[] = new int[4];

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag19\\src\\dag19\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		str = br.readLine();
		ip = Integer.parseInt(str.substring(4, 5));
		while ((str = br.readLine()) != null) {
			program.add(str);
		}
		br.close();
		eop = program.size();

		String[] instr;
//r[0]=1;//del2 tar för lång tid
		do {
			r[ip] = pc;// init
			// System.out.println("ip=" + pc +"[" + r[0] + "," + r[1] + "," + r[2] + "," +
			// r[3]+ "," + r[4]+ "," + r[5] + "]");
			instr = program.get(pc).substring(5).split(" ");
			i[0] = 0;
			i[1] = Integer.valueOf(instr[0]);
			i[2] = Integer.valueOf(instr[1]);
			i[3] = Integer.valueOf(instr[2]);
			// System.out.println(program.get(pc).substring(0, 4) + " " + i[1] + " " + i[2]
			// + " " + i[3]);
			// System.out.print(program.get(pc).substring(0, 4) + " " + i[1] + " " + i[2] +
			// " " + i[3] + " ");
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
				r[i[3]] = r[i[1]];// obs b irrelevant
				break;
			case "seti":
				r[i[3]] = i[1];// obs b irrelevant
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
				break;
			default:
				System.out.println("got unknown opcode");
			}
//		System.out.println("ip=" + pc +"[" + r[0] + "," + r[1] + "," + r[2] + "," + r[3]+ "," + r[4]+ "," + r[5] + "]");
			pc = r[ip] + 1;
		} while (pc < eop);
		System.out.println("End of program r0= >10551378 " + r[0]);

		int r0 = 1;
		int r1 = 0;
		int r2 = 0;
		int r4 = 0;
		int r5 = 0;

		r4 = 10551378; // programmet letar de faktorer som bildar tal upp till r4
		r0 = 0;

		r5 = 1;
		do {
			r2 = r4 / r5;// snabba på startvärdet
			do {
				r1 = r5 * r2;
				if (r1 == r4) {
					r0 = r0 + r5;
				}
				r2++;
			} while (r2 <= r4 / r5);// avsluta tidigare
			r5++;
		} while (r5 <= r4);
		System.out.println("Part II r0= " + r0);
	}
}
