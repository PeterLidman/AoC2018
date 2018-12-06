package dag5;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Dag5 {
	public static void main(final String[] args) throws IOException {
		String str = new String(Files.readAllBytes(Paths.get("C:\\git\\AoC2018\\Dag5\\src\\dag5\\input.txt")),
				StandardCharsets.UTF_8);
		int best = 50000;

		StringBuffer insb = new StringBuffer();
		StringBuffer outsb = new StringBuffer();
		insb.append(str.trim());// helvete! utan trim kommer LF med!!!

		do {
			outsb.append(reduce(insb, '@'));
			if (outsb.length() < insb.length()) {
				insb.setLength(0);
				insb.append(outsb);
				outsb.setLength(0);

			} else {
				break;
			}
		} while (true);

		System.out.println("out size: " + outsb.length());
		System.out.println("det var inte 11547");

		for (char c = 'a'; c <= 'z'; c++) {
			System.out.println("remove " + c);

			insb.setLength(0);
			insb.append(str.trim());
			outsb.setLength(0);

			do {
				outsb.append(reduce(insb, c));
				if (outsb.length() < insb.length()) {
					insb.setLength(0);
					insb.append(outsb);
					outsb.setLength(0);

				} else {
					break;
				}
			} while (true);
			System.out.println("run result " + outsb.length());

			if(outsb.length() < best) {
				best = outsb.length();
			}
		}
		System.out.println("Best run result " + best);
	}

	public static StringBuffer reduce(StringBuffer in, char remove) {
		StringBuffer out = new StringBuffer();
		int i = 0;
		int length = in.length();
		if (length < 2) {
			return in;
		}

		do {
			if (in.charAt(i) == otherCase(in.charAt(i + 1))) {
				i += 2;
			} else {
				if (in.charAt(i) == remove || in.charAt(i) == otherCase(remove)) {
					i++;
				} else {
					out.append(in.charAt(i));
					i++;
				}
			}
		} while (i < length - 1);
		if (i < length) {
			out.append(in.charAt(i));
		}
		return out;
	}

	public static char otherCase(char inChar) {
		int offset = (int) 'a' - (int) 'A';
		if (inChar >= 'a') {
			return (char) (inChar - offset);
		}
		return (char) (inChar + offset);
	}

}
