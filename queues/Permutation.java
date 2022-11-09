/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rdQueue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            rdQueue.enqueue(str);
        }
        Iterator<String> rdQiter = rdQueue.iterator();
        for (int i = 0; i < Integer.parseInt(args[0]); i += 1) {
            if (rdQiter.hasNext()) {
                System.out.println(rdQiter.next());
            }
        }
    }
}
