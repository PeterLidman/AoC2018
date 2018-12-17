package dag17;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Dag17 {
	static List<String> input = new ArrayList<>();
	static char[][] g;
	static int dimensionX;
	static int dimensionY;
	static int maxX = 552;
	static int minX = 332;
	static int maxY = 1622;
	static int minY = 3;
//	static int maxX = 506;
//	static int minX = 495;
//	static int maxY = 13;
//	static int minY = 0;

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag17\\src\\dag17\\input1.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		while ((str = br.readLine()) != null) {
			input.add(str);
		}
		br.close();
		// precalculated dimensions
		int tempX;
		int tempY;
		int tempX2;
		int tempY2;
		dimensionX = maxX - minX + 5;
		dimensionY = maxY + 1;
		g = new char[dimensionX][dimensionY];// maxx-minx+3,maxy+1
		int sourceX = 500 - minX + 1;// 500-minx+1
		for (int j = 0; j < dimensionY; j++) {
			for (int i = 0; i < dimensionX; i++) {
				g[i][j] = '.';
			}
		}
		g[sourceX][0] = '+';
		String inp[];
		for (String s : input) {
			inp = s.split(" ");
			if (s.substring(0, 2).equals("x=")) {
				tempX = Integer.valueOf(inp[0].substring(2, inp[0].length() - 1));
				if (tempX > maxX) {
					maxX = tempX;
				}
				if (tempX < minX) {
					minX = tempX;
				}
				inp = inp[1].substring(2, inp[1].length()).split("[.]");
				tempY = Integer.valueOf(inp[0]);
				tempY2 = Integer.valueOf(inp[2]);
				if (tempY2 > maxY) {
					maxY = tempY2;
				}
				if (tempY < minY) {
					minY = tempY;
				}
				for (int i = tempY; i <= tempY2; i++) {
					g[tempX - minX + 2][i] = '#';
				}
			} else { // y
				tempY = Integer.valueOf(inp[0].substring(2, inp[0].length() - 1));
				if (tempY > maxY) {
					maxY = tempY;
				}
				if (tempY < minY) {
					minY = tempY;
				}
				inp = inp[1].substring(2, inp[1].length()).split("[.]");
				tempX = Integer.valueOf(inp[0]);
				tempX2 = Integer.valueOf(inp[2]);
				if (tempX2 > maxX) {
					maxX = tempX2;
				}
				if (tempX < minX) {
					minX = tempX;
				}
				for (int i = tempX; i <= tempX2; i++) {
					g[i - minX + 2][tempY] = '#';
				}
			}
		}
		System.out.println("maxX " + maxX + " maxY " + maxY);
		System.out.println("minX " + minX + " minY " + minY);

		// start: travel down from pointOfOrigin, mark with | until reach # or ~
		// first to reach maxY it ends
		// mark | both sides until hit # or get to freefall if beneath is (. or | ) then
		// call start
		// if enclosed by # mark all with ~
		// repeat until reach maxY

		int yolo = 0;
		while (!fill(sourceX, 1)) {
//			if(yolo++ % 100 == 0) {
//				print();
//			}
		}
//		print();
		yolo = totalWaterTiles() - minY;
//		System.out.println("Wet tiles >31649 : " + yolo);
		int oldYolo = 0;
		while (oldYolo < yolo) {
			oldYolo = yolo;
			System.out.println("YOLO!");
			fill(sourceX, 1);
			yolo = totalWaterTiles() - minY;
		}
		print();
		System.out.println("Wet tiles >31649 <32214: " + yolo);
		System.out.println("Wet Still water tiles : " + totalStillWaterTiles());
	}

	private static boolean fill(int x, int y) {
		int keepX;
		int keepY;
		boolean bedRock = false;
		while (g[x][y] == '.' || g[x][y] == '|') {
			g[x][y++] = '|';
			if (y > maxY) {
				return true;
			}
		}
		y--;
		keepX = x;
		keepY = y;
		x++;
		while (g[x][y] == '.' || g[x][y] == '|') {
			g[x][y] = '|';
			if (g[x][y + 1] == '.' || g[x][y + 1] == '|') {// fall
				bedRock |= fill(x, y);
				break;// break while

			}
			x++;
		}
		x = keepX;
		y = keepY;
		x--;
		while (g[x][y] == '.' || g[x][y] == '|') {
			g[x][y] = '|';
			if (g[x][y + 1] == '.' || g[x][y + 1] == '|') {// fall
				bedRock |= fill(x, y);
				break;// break while
			}
			x--;
		}
		markIfStillWater(keepX, keepY);
		return bedRock;
	}

	private static void markIfStillWater(int x, int y) {
		int boundaries = 0;
		int keepX = x;
		int toX = 0;
		int fromX = 0;
		while (g[x][y] == '|') {
			if((y+1) < maxY && g[x][y+1] == '|' ) { //drain = no still
				return;				
			}	
			x++;
		}
		if (g[x][y] == '#') {
			toX = x - 1;
			boundaries++;
		}
		x = keepX;
		while (g[x][y] == '|') {
			if((y+1) < maxY && g[x][y+1] == '|' ) { //drain = no still
				return;				
			}			
			x--;
		}
		if (g[x][y] == '#') {
			fromX = x + 1;
			boundaries++;
		}
		if (boundaries == 2) {
			for (int i = fromX; i <= toX; i++) {
				g[i][y] = '~';
			}
		}
	}

	private static void print() {
		for (int j = 0; j < dimensionY; j++) {
			for (int i = 0; i < dimensionX; i++) {
				System.out.print(g[i][j]);
			}
			System.out.println();
		}
	}

	private static int totalWaterTiles() {
		int totalCount = 0;
		for (int j = 0; j < dimensionY; j++) {
			for (int i = 0; i < dimensionX; i++) {
				if (g[i][j] == '~' || g[i][j] == '|') {
					totalCount++;
				}
			}
		}
		return totalCount;
	}
	
	private static int totalStillWaterTiles() {
		int totalCount = 0;
		for (int j = 0; j < dimensionY; j++) {
			for (int i = 0; i < dimensionX; i++) {
				if (g[i][j] == '~' ) {
					totalCount++;
				}
			}
		}
		return totalCount;
	}
	
}