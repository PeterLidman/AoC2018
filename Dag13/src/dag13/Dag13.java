package dag13;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Dag13 {
	static List<Cart> carts = new ArrayList<Cart>();
	static char[][] mm = new char[150][150];

	public static void main(final String[] args) throws IOException {
		String str;
		final FileInputStream in = new FileInputStream("C:\\git\\AoC2018\\Dag13\\src\\dag13\\input.txt");
		final BufferedReader br = new BufferedReader(new InputStreamReader(in));
		int i = 0;
		int j = 0;
		char c;
		boolean crash = false;
		Cart s;

		while ((str = br.readLine()) != null) {
			for (i = 0; i < 150; i++) {
				c = str.charAt(i);
				switch (c) {
				case '^':
					carts.add(new Cart(i, j, '^'));
					c = '|';
					break;
				case '>':
					carts.add(new Cart(i, j, '>'));
					c = '-';
					break;
				case 'v':
					carts.add(new Cart(i, j, 'v'));
					c = '|';
					break;
				case '<':
					carts.add(new Cart(i, j, '<'));
					c = '-';
					break;
				}
				mm[i][j] = c;
			}
			j++;
		}
		br.close();
		int ant = 16777777;
		while (!crash && ant-- > 0) {
			//print();
			carts.sort(new Comparator<Cart>() {
				@Override
				public int compare(Cart c1, Cart c2) {
					if (c1.x > c2.x) {
						return 1;
					} else if (c1.x < c2.x) {
						return -1;
					}
					if (c1.y > c2.y) {
						return 1;
					} else if (c1.y < c2.y) {
						return -1;
					}
					return 0;
				}
			});
			for (i = 0; i < carts.size(); i++) {
				s = carts.get(i);
				System.out.println(String.valueOf(s.direction) + " " + s.lastIntersection + " " + s.x + " " + s.y);
				switch (s.direction) {
				case 'X':
					break;
				case '^':
					s.y--;
					if (mm[s.x][s.y] == '/') {
						s.direction = '>';
					} else if (mm[s.x][s.y] == '\\') {
						s.direction = '<';
					} else if (mm[s.x][s.y] == '+') {
						if (s.lastIntersection == 'r') {
							s.lastIntersection = 'l';
							s.direction = '<';
						} else if (s.lastIntersection == 's') {
							s.lastIntersection = 'r';
							s.direction = '>';
						} else {// l keep
							s.lastIntersection = 's';
						}
					}
					break;
				case '>':
					s.x++;
					if (mm[s.x][s.y] == '/') {
						s.direction = '^';
					} else if (mm[s.x][s.y] == '\\') {
						s.direction = 'v';
					} else if (mm[s.x][s.y] == '+') {
						if (s.lastIntersection == 'r') {
							s.lastIntersection = 'l';
							s.direction = '^';
						} else if (s.lastIntersection == 's') {
							s.lastIntersection = 'r';
							s.direction = 'v';
						} else {// l keep
							s.lastIntersection = 's';
						}
					}
					break;
				case 'v':
					s.y++;
					if (mm[s.x][s.y] == '/') {
						s.direction = '<';
					} else if (mm[s.x][s.y] == '\\') {
						s.direction = '>';
					} else if (mm[s.x][s.y] == '+') {
						if (s.lastIntersection == 'r') {
							s.lastIntersection = 'l';
							s.direction = '>';
						} else if (s.lastIntersection == 's') {
							s.lastIntersection = 'r';
							s.direction = '<';
						} else {// l keep
							s.lastIntersection = 's';
						}
					}
					break;
				case '<':
					s.x--;
					if (mm[s.x][s.y] == '/') {
						s.direction = 'v';
					} else if (mm[s.x][s.y] == '\\') {
						s.direction = '^';
					} else if (mm[s.x][s.y] == '+') {
						if (s.lastIntersection == 'r') {
							s.lastIntersection = 'l';
							s.direction = 'v';
						} else if (s.lastIntersection == 's') {
							s.lastIntersection = 'r';
							s.direction = '^';
						} else {// l keep
							s.lastIntersection = 's';
						}
					}
					break;
				}
				getCollision();
			}
			removeWrecks();
			if (carts.size() == 1) {
				crash = true;
				System.out.println(
						String.valueOf(carts.get(0).direction) + "xy " + carts.get(0).x + "," + carts.get(0).y);

			}
		}
	}

	private static boolean getCollision() {
		Cart c1;
		Cart c2;
		for (int i = 0; i < carts.size() - 1; i++) {
			c1 = carts.get(i);
			for (int j = i + 1; j < carts.size(); j++) {
				c2 = carts.get(j);
				if (c1.x == c2.x && c1.y == c2.y) {
					c1.direction = 'X';
					c2.direction = 'X';
					return true;
				}
			}
		}
		return false;
	}

	private static void removeWrecks() {
		Cart c1;
		int i = 0;
		do {
			c1 = carts.get(i);
			if (c1.direction == 'X') {
				carts.remove(i);

			} else {
				i++;
			}
		} while (i < carts.size());
	}

	private static void print() {
		char c;
		for (int j = 0; j < 150; j++) {
			for (int i = 0; i < 150; i++) {
				if ((c = getCart(i, j)) == ' ') {
					System.out.print(mm[i][j]);
				} else {
					System.out.print(c);
				}
			}
			System.out.println();
		}
	}

	private static char getCart(int i, int j) {
		Cart sss;
		for (int k = 0; k < carts.size(); k++) {
			sss = carts.get(k);
			if (sss.x == i && sss.y == j) {
				return sss.direction;
			}
		}
		return ' ';
	}

}

class Cart {
	int x = 0;
	int y = 0;
	char direction = ' ';
	char lastIntersection = 'r';// l s r

	public Cart(int x, int y, char direction) {
		super();
		this.x = x;
		this.y = y;
		this.direction = direction;
	}
}