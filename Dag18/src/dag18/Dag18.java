package dag18;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dag18 {
	static char[][] f = new char[60][60];
	static char[][] f2 = new char[60][60];
	static int sizeY = 0;
	static int sizeX = 0;

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag18\\src\\dag18\\input2.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));

		for (int j = 0; j < 60; j++) {
			for (int i = 0; i < 60; i++) {
				f[i][j] = '.';
				f2[i][j] = '.';
			}
		}
		while ((str = br.readLine()) != null) {
			sizeX = str.length();
			for (int i = 0; i < sizeX; i++) {
				f[i + 5][sizeY + 5] = str.charAt(i);
			}
			sizeY++;
		}
		br.close();

		for(int i=0;i<1_000;i++) {
			print();
			oneMinute();
		}
		print();
		System.out.println("total " + totalResourceValue());
	}

	private static void oneMinute() {
		for (int j = 5; j < sizeY + 5; j++) {
			for (int i = 5; i < sizeX + 5; i++) {
				f2[i][j] = calc(i, j);
			}
		}
		copy();
	}

	private static void copy() {
		for (int j = 0; j < 60; j++) {
			for (int i = 0; i < 60; i++) {
				f[i][j] = f2[i][j];
			}
		}
	}

	private static char calc(int x, int y) {
		int c = 0;
		int c2 = 0;

		if (f[x][y] == '.') {
			for (int j = -1; j < 2; j++) {
				for (int i = -1; i < 2; i++) {
					if (f[i + x][j + y] == '|') {
						c++;
					}
				}
			}
			if (c > 2) {
				return '|';
			} else {
				return '.';
			}
		}
		c = 0;
		if (f[x][y] == '|') {
			for (int j = -1; j < 2; j++) {
				for (int i = -1; i < 2; i++) {
					if (f[i + x][j + y] == '#') {
						c++;
					}
				}
			}
			if (c > 2) {
				return '#';
			} else {
				return '|';
			}
		}
		c = 0;
		if (f[x][y] == '#') {
			for (int j = -1; j < 2; j++) {
				for (int i = -1; i < 2; i++) {
					if (f[i + x][j + y] == '#') {
						c++;
					}
					if (f[i + x][j + y] == '|') {
						c2++;
					}
				}
			}
			if (c > 1 && c2 > 0) {// obs subtract # in center
				return '#';
			} else {
				return '.';
			}
		}
		return '$';// not reachable
	}

	private static void print() {
		for (int j = 5; j < sizeY + 5; j++) {
			for (int i = 5; i < sizeX + 5; i++) {
				System.out.print(f[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}

	private static int totalResourceValue() {
		int wood = 0;
		int lumberyard = 0;
		for (int j = 0; j < 60; j++) {
			for (int i = 0; i < 60; i++) {
				if (f[i][j] == '#') {
					lumberyard++;
				}
				if (f[i][j] == '|') {
					wood++;
				}
			}
		}
		return wood * lumberyard;
	}

}