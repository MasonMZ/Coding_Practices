package disjoint_sets;
import org.junit.Test;
import java.lang.Math;
import java.util.Iterator;


import static org.junit.Assert.*;

public class Test_connected_items {
    @Test
    public void connect_test() {
        Naive_connected_items<Integer> t= new Naive_connected_items<>();
        for (int i = 0; i < 7; i += 1) {
            t.add_item(i);
            t.add_item(3);
        }
        t.connect(0, 1);
        t.connect(1, 2);
        t.connect(0, 4);
        t.connect(3, 5);
        assertEquals("Should be true", true, t.isConnected(2, 4));
        assertEquals("Should be false", false, t.isConnected(3, 0));
        t.connect(4, 2);
        t.connect(4, 6);
        t.connect(3, 6);
        assertEquals("Should be true", true, t.isConnected(3, 0));

        Naive_connected_items<Integer> t1= new Naive_connected_items<>();
        Naive_connected_items<Integer> t2= new Naive_connected_items<>();
        for (int k = 10; k < 2561; k *= 2 ) {
            for (int i = 0; i < k; i += 1) {
                int num1 = Math.round((float) Math.random() * 100);
                int num2 = Math.round((float) Math.random() * 100);
                t1.add_item(num1);
                t1.add_item(num2);

                int num3 = Math.round((float) Math.random() * 50);
                int num4 = Math.round((float) Math.random() * 50);
                t2.add_item(num3);
                t2.add_item(num3 + num4);

                t1.connect(num1, num2);
                t2.connect(num3,num3 + num4);
            }
            int test_num1 = Math.round((float) Math.random() * 100);
            int test_num2 = Math.round((float) Math.random() * 100);
            System.out.println("t1 " + "[" + k + "] " + "connection: " + t1.isConnected(test_num1, test_num2));
            System.out.println("t2 " + "[" + k + "] " + "connection: " + t2.isConnected(test_num1, test_num2));
        }




    }
}
