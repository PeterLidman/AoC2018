package dag20;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Dag20 {
	static StringBuilder s = new StringBuilder();
	static char[][] map = new char[2000][2000];
	static int maxx = 1000, maxy = 1000, minx = 1000, miny = 1000;
	static int[][] dist = new int[2000][2000];
	static boolean[][] visited = new boolean[2000][2000];
	static int sum = 0;
	static int maxDistance = 0;

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag20\\src\\dag20\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			s.append(str);
		}
		br.close();
//		s.append("^WNE$");
//		s.append("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))$");
//		s.append("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))$");
		s.deleteCharAt(0);
		s.deleteCharAt(s.length() - 1);
		int x = 1000, y = 1000;
		map[x][y] = 'X';
		parse(x, y, 0, 0);
		printMap();
		System.out.println("Del 1: " + maxDistance);
		System.out.println("Del 2: " + sum);
	}

	private static void parse(int x, int y, int stringPosition, int distance) {
		while (stringPosition < s.length()) {
			if (s.charAt(stringPosition) == 'E') {
				x++;
				map[x][y] = '|';
				x++;
				map[x][y] = '.';
				walls(x, y);
				if (x > maxx) {
					maxx = x;
				}
			} else if (s.charAt(stringPosition) == 'W') {
				x--;
				map[x][y] = '|';
				x--;
				map[x][y] = '.';
				walls(x, y);
				if (x < minx) {
					minx = x;
				}
			} else if (s.charAt(stringPosition) == 'N') {
				y--;
				map[x][y] = '-';
				y--;
				map[x][y] = '.';
				walls(x, y);
				if (y < miny) {
					miny = y;
				}
			} else if (s.charAt(stringPosition) == 'S') {
				y++;
				map[x][y] = '-';
				y++;
				map[x][y] = '.';
				walls(x, y);
				if (y > maxy) {
					maxy = y;
				}
			} else if (s.charAt(stringPosition) == '(') {
				int parentLevel = 0;
				boolean newBranch = true;
				while (stringPosition < s.length()) {
					stringPosition++;
					if (s.charAt(stringPosition) == '(') {
						parentLevel++;
					} else if (s.charAt(stringPosition) == ')') {
						parentLevel--;
						if (parentLevel < 0) {
							parse(x, y, stringPosition + 1, distance);
							return;
						}
					} else if (s.charAt(stringPosition) == '|') {
						if (parentLevel == 0) {
							newBranch = true;
						}
					} else if (parentLevel == 0) {
						if (newBranch) {
							parse(x, y, stringPosition, distance);
							newBranch = false;
						}
					}
				}
			} else {
				return;
			}
			stringPosition++;
			distance++;
			if (distance >= 1000 && !visited[x][y]) {
				visited[x][y] = true;
				sum++;
			}
			if (dist[x][y] == 0 || dist[x][y] > distance) {
				dist[x][y] = distance;
				if (distance > maxDistance) {
					maxDistance = distance;
				}
			}
		}
	}

	private static void printMap() {
		for (int j = minx; j <= maxx; j++) {
			System.out.print("#");
		}
		System.out.println("##");
		for (int i = miny; i <= maxy; i++) {
			System.out.print("#");
			for (int j = minx; j <= maxx; j++) {
				if (map[j][i] == 0 || map[j][i] == '?') {
					System.out.print("#");
				} else {
					System.out.print(map[j][i]);
				}
			}
			System.out.print("#");
			System.out.println();
		}
		for (int j = minx; j <= maxx; j++) {
			System.out.print("#");
		}
		System.out.println("##");
	}

	private static void setIfNotSet(int x, int y, char c) {
		if (map[x][y] == 0 || map[x][y] == '?') {
			map[x][y] = c;
		}
	}

	private static void walls(int x, int y) {
		setIfNotSet(x + 1, y + 1, '#');
		setIfNotSet(x + 1, y - 1, '#');
		setIfNotSet(x - 1, y - 1, '#');
		setIfNotSet(x - 1, y + 1, '#');
		setIfNotSet(x, y + 1, '?');
		setIfNotSet(x, y - 1, '?');
		setIfNotSet(x + 1, y, '?');
		setIfNotSet(x - 1, y, '?');
	}

}
