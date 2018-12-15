package dag15;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dag15 {
	static List<String> input = new ArrayList<>();
	static List<CombatUnit> combatUnit = new ArrayList<CombatUnit>();
	static List<CombatUnit> freshUnits = new ArrayList<CombatUnit>();
	static char[][] dungeon;
	static char[][] populatedDungeon;
	static int sizeX = 0;
	static int sizeY = 0;
	static int modifiedElfDamage = 4;
	static boolean deadElves = true;

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag15\\src\\dag15\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int i = 0;
		int j = 0;
		char c;

		// import
		while ((str = br.readLine()) != null) {
			input.add(str);
			sizeY++;
		}
		br.close();
		sizeX = input.get(0).length();
		dungeon = new char[sizeX][sizeY];
		populatedDungeon = new char[sizeX][sizeY];
		j = 0;
		do {
			for (i = 0; i < sizeX; i++) {
				c = input.get(j).charAt(i);
				// populatedDungeon[i][j] = c;
				switch (c) {
				case 'E':
					combatUnit.add(new CombatUnit(i, j, 'E'));
					c = '.';
					break;
				case 'G':
					combatUnit.add(new CombatUnit(i, j, 'G'));
					c = '.';
					break;
				}
				dungeon[i][j] = c;
			}
			j++;
		} while (j < sizeY);
		for (CombatUnit cus : combatUnit) {
			freshUnits.add(new CombatUnit(cus.x, cus.y, cus.affinity));
		}

//		print();
//		populate();
		// printPopulated();

		while (deadElves) {
			combatUnit.clear();
			for (CombatUnit cus : freshUnits) {
				combatUnit.add(new CombatUnit(cus.x, cus.y, cus.affinity));
				System.out.println(cus.affinity + " hp " + cus.hp);
			}
			modifiedElfDamage++;
			deadElves = false;
			populate();
			i = 0;
			do {
				System.out.println("combat iteration " + i++);
				print();
			} while (moveAndOrFight());
			System.out.println("combat iteration " + i--);
			print();
			j = 0;
			for (CombatUnit cw : combatUnit) {
				System.out.println("dmg unit" + cw.hp);
				j += cw.hp;
			}
			System.out.println("Battle result " + j * i + " no dead elves with modified damage " + modifiedElfDamage);
		}
	}

	private static boolean moveAndOrFight() {
		CombatUnit cu;
		char attackVectorAlpha = '.';

		sortCombatUnits();// always attack in correct order
		for (int i = 0; i < combatUnit.size(); i++) {// traverse all combatants
			cu = combatUnit.get(i);
			if (cu.affinity != '.') {// dead don't fight

				if (!fightAdjacentOpponents(cu)) {
					// move and then fight
					populate();
					if ((attackVectorAlpha = floodWithDirectionsUntilContactWithEnemy(cu)) != '.') {
//					printPopulated();
//					System.out.println("attack " + attackVectorAlpha);
						switch (attackVectorAlpha) {
						case '^':
							cu.y++;
							break;
						case '<':
							cu.x++;
							break;
						case '>':
							cu.x--;
							break;
						case 'v':
							cu.y--;
							break;
						}
						fightAdjacentOpponents(cu);
					}
				}
			}
		}
		return !removeDeadReportVictory();
	}

	private static char floodWithDirectionsUntilContactWithEnemy(CombatUnit cu) {
		boolean anyReachableSpaceLeft = true;
		char enemyAffinity = oppositeAffinity(cu.affinity);
		char lastDirection = '.';
		List<Integer> xMark = new ArrayList<>();
		List<Integer> yMark = new ArrayList<>();
		List<Character> pathMark = new ArrayList<>();

		markPathHome(cu.x, cu.y - 1, 'v');
		markPathHome(cu.x - 1, cu.y, '>');
		markPathHome(cu.x + 1, cu.y, '<');
		markPathHome(cu.x, cu.y + 1, '^');
//		System.out.println("jadore");
//		printPopulated();

		while (anyReachableSpaceLeft) {
			for (int j = 0; j < sizeY; j++) {
				for (int i = 0; i < sizeX; i++) {
					if (populatedDungeon[i][j] == '^' || populatedDungeon[i][j] == '<' || populatedDungeon[i][j] == '>'
							|| populatedDungeon[i][j] == 'v') {
//						System.out.println("examine" + i + " " + j);
						// is enemy adjacent
						if (enemyAffinity == populatedDungeon[i][j - 1] || enemyAffinity == populatedDungeon[i - 1][j]
								|| enemyAffinity == populatedDungeon[i + 1][j]
								|| enemyAffinity == populatedDungeon[i][j + 1]) {
//							System.out.println("near enemy");
							// backtrack to considered warrior
							while (populatedDungeon[i][j] != cu.affinity) { // ugly hijack of i and j :-)
								lastDirection = populatedDungeon[i][j];
								switch (populatedDungeon[i][j]) {
								case '^':
									j--;
									break;
								case '<':
									i--;
									break;
								case '>':
									i++;
									break;
								case 'v':
									j++;
									break;
								}
							}
							return lastDirection;
						}
						// add markers for later flooding
						xMark.add(i);
						yMark.add(j - 1);
						pathMark.add('v');

						xMark.add(i - 1);
						yMark.add(j);
						pathMark.add('>');

						xMark.add(i + 1);
						yMark.add(j);
						pathMark.add('<');

						xMark.add(i);
						yMark.add(j + 1);
						pathMark.add('^');

					}
				}
			}
			anyReachableSpaceLeft = false;
			// mark
			boolean result = false;
			for (int i = 0; i < xMark.size(); i++) {
				result = markPathHome(xMark.get(i), yMark.get(i), pathMark.get(i));
//				System.out.println("markerar " + xMark.get(i) + " " + yMark.get(i) + " " + pathMark.get(i));
				if (result) {
					anyReachableSpaceLeft = true;
				}
			}
			xMark.clear();
			yMark.clear();
			pathMark.clear();
//			printPopulated();
//			System.out.println("one more layer");
		}
		return '.';
	}

	private static boolean markPathHome(int x, int y, char c) {
//		System.out.println("markering tog " + x + " " + y + " " + c + " actual" + populatedDungeon[x][y]);
		if (populatedDungeon[x][y] == '.') {
			populatedDungeon[x][y] = c;
			return true;
		}
		return false;
	}

	private static boolean fightAdjacentOpponents(CombatUnit cu) {
		CombatUnit pa;
		int weakest = 301;
		List<CombatUnit> opp = new ArrayList<>();
		// add possible adversaries in order
		if ((pa = getUnit(cu.x, (cu.y - 1))) != null) {
			if (pa.affinity == oppositeAffinity(cu.affinity)) {
				opp.add(pa);
			}
		}
		if ((pa = getUnit((cu.x - 1), cu.y)) != null) {
			if (pa.affinity == oppositeAffinity(cu.affinity)) {
				opp.add(pa);
			}
		}
		if ((pa = getUnit((cu.x + 1), cu.y)) != null) {
			if (pa.affinity == oppositeAffinity(cu.affinity)) {
				opp.add(pa);
			}
		}
		if ((pa = getUnit(cu.x, (cu.y + 1))) != null) {
			if (pa.affinity == oppositeAffinity(cu.affinity)) {
				opp.add(pa);
			}
		}
		// if many choose weakest
		for (int i = 0; i < opp.size(); i++) {
			if (opp.get(i).hp < weakest) {
				weakest = opp.get(i).hp;
			}
		}
		// strike first weakest
		for (int i = 0; i < opp.size(); i++) {
			if (opp.get(i).hp == weakest) {
				if (opp.get(i).damage(modifiedElfDamage) == false) {
					System.out.println("elf died " + i);
					deadElves = true;
				}
				return true;
			}
		}
		return false;
	}

	private static boolean removeDeadReportVictory() {
		int elves = 0;
		int goblins = 0;
		CombatUnit c;
		int i = 0;
		do {
			c = combatUnit.get(i);
			if (c.affinity == '.') {
				combatUnit.remove(i);
			} else {
				if (c.affinity == 'E') {
					elves++;
				} else {
					goblins++;
				}
				i++;
			}
		} while (i < combatUnit.size());
		if (elves == 0 || goblins == 0) {
			return true;
		}
		return false;
	}

	private static CombatUnit getUnit(int x, int y) {
		CombatUnit cu;
		for (int k = 0; k < combatUnit.size(); k++) {
			cu = combatUnit.get(k);
			if (cu.x == x && cu.y == y) {
				return cu;
			}
		}
		return null;
	}

	private static void print() {
		CombatUnit c;
		String hps = "  ";
		for (int j = 0; j < sizeY; j++) {
			for (int i = 0; i < sizeX; i++) {
				c = getUnit(i, j);
				if (c != null) {
					hps += "(" + c.affinity + "," + c.hp + "),";
					System.out.print(c.affinity);
				} else {
					System.out.print(dungeon[i][j]);
				}
			}
			System.out.println(hps.substring(0, hps.length() - 1));
			hps = "  ";
		}
	}

	private static void populate() {
		CombatUnit c;
		for (int j = 0; j < sizeY; j++) {
			for (int i = 0; i < sizeX; i++) {
				c = getUnit(i, j);
				if (c != null) {
					populatedDungeon[i][j] = c.affinity;
				} else {
					populatedDungeon[i][j] = dungeon[i][j];
				}
			}
		}
	}

	private static void printPopulated() {
		CombatUnit c;
		String hps = "  ";
		for (int j = 0; j < sizeY; j++) {
			for (int i = 0; i < sizeX; i++) {
				c = getUnit(i, j);
				if (c != null) {
					hps += "(" + c.affinity + "," + c.hp + "),";
				}
				System.out.print(populatedDungeon[i][j]);
			}
			System.out.println(hps.substring(0, hps.length() - 1));
			hps = "  ";
		}
	}

	private static char oppositeAffinity(char in) {
		if (in == 'E') {
			return 'G';
		}
		return 'E';
	}

	private static void sortCombatUnits() {
		combatUnit.sort(new Comparator<CombatUnit>() {
			@Override
			public int compare(CombatUnit c1, CombatUnit c2) {
				if (c1.y > c2.y) {
					return 1;
				} else if (c1.y < c2.y) {
					return -1;
				}
				if (c1.x > c2.x) {
					return 1;
				} else if (c1.x < c2.x) {
					return -1;
				}
				return 0;
			}
		});
	}

}

class CombatUnit {
	char affinity = '.';// G E .
	int x = 0;
	int y = 0;
	int hp = 200;

	public CombatUnit(int x, int y, char affinity) {
		super();
		this.x = x;
		this.y = y;
		this.affinity = affinity;
	}

	public boolean damage(int modfiedElfDamage) {
		if (this.affinity == 'E') {
			hp -= 3;
		} else {
			hp -= modfiedElfDamage;
		}
		if (hp <= 0) {
			if (this.affinity == 'E') {// we can't have elves dying
				this.affinity = '.';
				return false;
			} else {
				this.affinity = '.';
			}
		}
		return true;
	}

}
