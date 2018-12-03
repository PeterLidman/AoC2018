package dag3;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag3 {
	static List<String> claims = new ArrayList<String>();
	static int[][] fabric = new int[1000][1000];
	static int overlaps = 0;
	static String str[];
	static String coord[];
	static String size[];

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag3\\src\\dag3\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			claims.add((str));
		}
		br.close();
		del1();
		del2();
	}

	public static void del1() {
		for (final String claim : claims) {
			str = claim.split(" ");
			coord = str[2].split(",");
			size = str[3].split("x");
			mark(Integer.parseInt(str[0].substring(1)), Integer.parseInt(coord[0]),
					Integer.parseInt(coord[1].substring(0, coord[1].length() - 1)), Integer.parseInt(size[0]),
					Integer.parseInt(size[1]));
		}
		System.out.println("Overlaps: " + overlaps);
	}

	public static void del2() {
		for (final String claim : claims) {
			str = claim.split(" ");
			coord = str[2].split(",");
			size = str[3].split("x");
			if (!mark(Integer.parseInt(str[0].substring(1)), Integer.parseInt(coord[0]),
					Integer.parseInt(coord[1].substring(0, coord[1].length() - 1)), Integer.parseInt(size[0]),
					Integer.parseInt(size[1]))) {
				System.out.println(str[0] + " has no overlap!");
				break;
			}
		}
	}

	public static boolean mark(final int nr, final int x, final int y, final int sx, final int sy) {
		boolean overlap = false;
		for (int xpos = x; xpos < x + sx; xpos++) {
			for (int ypos = y; ypos < y + sy; ypos++) {
				if (fabric[xpos][ypos] == 0) {
					fabric[xpos][ypos] = nr;
				} else if (fabric[xpos][ypos] == -1) {
					overlap = true;
				} else {
					if (fabric[xpos][ypos] != nr) {
						overlap = true;
						overlaps++;
					}
					fabric[xpos][ypos] = -1;
				}
			}
		}
		return overlap;
	}

}
