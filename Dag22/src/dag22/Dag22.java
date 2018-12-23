package dag22;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Dag22 {
//	static int depth = 510, targetx = 10, targety = 10, dimensionx = 20, dimensiony = 20;
	static int depth = 8103, targetx = 9, targety = 758, dimensionx = 50, dimensiony = 800;
	static int[][] caveGeologicalIndex = new int[dimensionx][dimensiony],
			caveErosionLevel = new int[dimensionx][dimensiony];
	static char[][] caveType = new char[dimensionx][dimensiony];
	static int[][] caveTypeInt = new int[dimensionx][dimensiony];
	static int[][] caveBestTrack = new int[dimensionx][dimensiony];
	static int bestTimeToPruneWith = 1458;

	public static void main(final String[] args) {
		calculateGeologicalIndexAndErosionLevel();
		print();
		System.out.println("Part 1, Risk level " + calculateRiskLevelArea(targetx, targety));
		System.out.println("Part 2, Best route took " + getBestTimeToTarget() + " minutes.");
//		track('T', 0, 0, 0);// target should hold best route
//		System.out.println("Best route took " + caveBestTrack[targetx][targety] + " minutes.");
	}

	private static void track(char tool, int x, int y, int time) {
		if (x == targetx && y == targety) {// framme
			if (tool != 'T') {
				time += 7;
			}
			if (bestTimeToPruneWith > time) {
				bestTimeToPruneWith = time;
				System.out.println("framme! " + time);
			}
		}
		if (time > y * 2 + 5) {// prune
			// System.out.println("lenght prune at " +time + " " + routeBack);
			return;
		}
		if (time > bestTimeToPruneWith) {// prune
			// System.out.println("time prune at " + time + " " + routeBack);
			return;
		}
		if (caveBestTrack[x][y] < time && caveBestTrack[x][y] != 0) {// this is a worse route, abort!
			// System.out.println("worse route abort");
			return;
		}
		caveBestTrack[x][y] = time; // ny bästa tid
		if (y < dimensiony - 1) {// kolla ner
			if (caveType[x][y + 1] == badTool(tool)) {// nästa kräver tool-byte
				track(nextTool(tool), x, y + 1, time + 8);
				track(nextTool(nextTool(tool)), x, y + 1, time + 8);
			} else {
				track(tool, x, y + 1, time + 1);
			}
		}
		if (x < dimensionx - 1) {// kolla höger
			if (caveType[x + 1][y] == badTool(tool)) {// nästa kräver tool-byte
				track(nextTool(tool), x + 1, y, time + 8);
				track(nextTool(nextTool(tool)), x + 1, y, time + 8);
			} else {
				track(tool, x + 1, y, time + 1);
			}
		}
		if (x > 0) {// kolla vänster
			if (caveType[x - 1][y] == badTool(tool)) {// nästa kräver tool-byte
				track(nextTool(tool), x - 1, y, time + 8);
				track(nextTool(nextTool(tool)), x - 1, y, time + 8);
			} else {
				track(tool, x - 1, y, time + 1);
			}
		}
		if (y > 0) {// kolla upp
			if (caveType[x][y - 1] == badTool(tool)) {// nästa kräver tool-byte
				track(nextTool(tool), x, y - 1, time + 8);
				track(nextTool(nextTool(tool)), x, y - 1, time + 8);
			} else {
				track(tool, x, y - 1, time + 1);
			}
		}
		return;
	}

	private static char nextTool(char tool) {
		switch (tool) {
		case 'N':
			return 'T';
		case 'T':
			return 'C';
		}
		return 'N';
	}

	private static int nextTool(int tool) {
		return (tool + 1) % 3;
	}

	private static char badTool(char tool) {
		switch (tool) {
		case 'N':
			return '.';
		case 'C':
			return '|';
		}
		return '=';
	}

	private static int calculateRiskLevelArea(int endx, int endy) {
		int sum = 0;
		for (int j = 0; j < endy + 1; j++) {
			for (int i = 0; i < endx + 1; i++) {
				sum += caveErosionLevel[i][j] % 3;
			}
		}
		return sum;
	}

	static private void calculateGeologicalIndexAndErosionLevel() {
		for (int i = 0; i < dimensionx; i++) {
			caveGeologicalIndex[i][0] = i * 16807;
			caveErosionLevel[i][0] = (caveGeologicalIndex[i][0] + depth) % 20183;
			setErosionLevel(i, 0);
		}
		for (int i = 0; i < dimensiony; i++) {
			caveGeologicalIndex[0][i] = i * 48271;
			caveErosionLevel[0][i] = (caveGeologicalIndex[0][i] + depth) % 20183;
			setErosionLevel(0, i);
		}
		if (dimensionx > dimensiony) {
			for (int j = 1; j < dimensiony; j++) {
				for (int i = 1; i < dimensionx; i++) {
					calculateErosionLevelAndTypeAt(i, j);
				}
			}
		} else {
			for (int j = 1; j < dimensionx; j++) {
				for (int i = 1; i < dimensiony; i++) {
					calculateErosionLevelAndTypeAt(j, i);
				}
			}
		}
	}

	static private void calculateErosionLevelAndTypeAt(int x, int y) {
		if ((x == 0 && y == 0) || (x == targetx && y == targety)) {// (0,0) and target
			caveGeologicalIndex[x][y] = 0;
			caveErosionLevel[x][y] = (caveGeologicalIndex[x][y] + depth) % 20183;
		} else {
			caveGeologicalIndex[x][y] = caveErosionLevel[x - 1][y] * caveErosionLevel[x][y - 1];
			caveErosionLevel[x][y] = (caveGeologicalIndex[x][y] + depth) % 20183;
		}
		setErosionLevel(x, y);
	}

	private static void setErosionLevel(int x, int y) {
		switch (caveErosionLevel[x][y] % 3) {
		case 0:
			caveType[x][y] = '.';// rocky
			caveTypeInt[x][y] = 0;// rocky
			break;
		case 1:
			caveType[x][y] = '=';// wet
			caveTypeInt[x][y] = 1;// wet
			break;
		case 2:
			caveType[x][y] = '|';// narrow
			caveTypeInt[x][y] = 2;// narrow
			break;
		}
	}

	static private void print() {
		for (int j = 0; j < dimensiony; j++) {
			for (int i = 0; i < dimensionx; i++) {
				if (i == 0 && j == 0) {
					System.out.print('M');
				} else if (i == targetx && j == targety) {
					System.out.print('T');
				} else {
					System.out.print(caveType[i][j]);
				}
			}
			System.out.println("");
		}
	}

	private static int getBestTimeToTarget() {
		// 0 Rocky<>Neither 1 Wet<>Torch 2 Narrow<>Climbing gear
		PriorityQueue<CaveRoom> queue = new PriorityQueue<>(Comparator.comparing(CaveRoom::getMinutes));
		queue.add(new CaveRoom(0, 0, 0, 1));
		CaveRoom target = new CaveRoom(targetx, targety, 0, 1);
		boolean[][][] bio = new boolean[dimensionx][dimensiony][3];

		while (!queue.isEmpty()) {
			CaveRoom c = queue.poll();
			// System.out.println("min " + c.minute + " x " + c.x + " y " + c.y);

			if (bio[c.x][c.y][c.tool]) {
				// System.out.println("besökt");
				continue;
			}
			bio[c.x][c.y][c.tool] = true;
			if (caveTypeInt[c.x][c.y] == c.tool) {
				// System.out.println("fel verktyg");
				continue;
			}
			if (c.x == target.x && c.y == target.y && c.tool == target.tool) {
				return c.minute;
			}
			queue.add(new CaveRoom(c.x, c.y, c.minute + 7, nextTool(c.tool)));
			queue.add(new CaveRoom(c.x, c.y, c.minute + 7, nextTool(nextTool(c.tool))));
			if (c.y < dimensiony - 1) {// kolla ner
				queue.add(new CaveRoom(c.x, c.y + 1, c.minute + 1, c.tool));
			}
			if (c.x < dimensionx - 1) {// kolla höger
				queue.add(new CaveRoom(c.x + 1, c.y, c.minute + 1, c.tool));
			}
			if (c.x > 0) {// kolla vänster
				queue.add(new CaveRoom(c.x - 1, c.y, c.minute + 1, c.tool));
			}
			if (c.y > 0) {// kolla upp
				queue.add(new CaveRoom(c.x, c.y - 1, c.minute + 1, c.tool));
			}
		}
		return 0;
	}
}

class CaveRoom {
	int x;
	int y;
	int minute;
	int tool;// NTC=012

	public CaveRoom(int posx, int posy, int minutes, int tool) {
		this.x = posx;
		this.y = posy;
		this.minute = minutes;
		this.tool = tool;
	}

	int getMinutes() {
		return minute;
	}
}
