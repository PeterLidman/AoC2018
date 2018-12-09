package dag9;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Dag9 {
	public static void main(final String[] args) {
//"427 players; last marble is worth 70723 points";
//	10 players; last marble is worth 1618 points: high score is 8317
//	13 players; last marble is worth 7999 points: high score is 146373
//	17 players; last marble is worth 1104 points: high score is 2764
//	21 players; last marble is worth 6111 points: high score is 54718
//	30 players; last marble is worth 5807 points: high score is 37305
//   9                                 25                       32
		Deque<Integer> c = new ArrayDeque<>();
		int p = 427;// players
		int m = 7072300;// marbles
		long[] s = new long[p];
		c.add(0);

		for (int i = 1; i <= m; i++) {
			if (i % 23 == 0) {
				// rotera -7
				c.addFirst(c.pollLast());
				c.addFirst(c.pollLast());
				c.addFirst(c.pollLast());
				c.addFirst(c.pollLast());
				c.addFirst(c.pollLast());
				c.addFirst(c.pollLast());
				c.addFirst(c.pollLast());
				s[i % p] += i + c.pollLast();
				c.add(c.pollFirst());
			} else {
				c.add(c.pollFirst());// rotera så nästa skall läggas sist
				c.add(i);
			}
		}
		System.out.println("winning score " + Arrays.stream(s).max().getAsLong());
	}
}