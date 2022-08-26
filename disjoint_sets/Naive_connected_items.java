package disjoint_sets;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/** Using set to implement connected items. **/
public class Naive_connected_items<T> {
    private Set<Set<T>> items;

    public Naive_connected_items() {
        items = new HashSet<>();
    }

    /** Put each new item into a set which is isolated from other sets */
    public void add_item(T item) {
        Iterator<Set<T>> iter = items.iterator();
        boolean existed = false;
        while (iter.hasNext()) {  // check if each set contains the item already
            Set<T> item_being_checked = iter.next();
            if (item_being_checked.contains(item)) {
                existed = true;
            }
        }
        if (!existed) {
            Set<T> new_item = Set.of(item);
            items.add(new_item);
        }
    }

    /** Combine two sets to implement connection. */
    public void connect(T a, T b) {
        boolean exist_a = false;
        boolean exist_b = false;
        Set<T> target_a = null;
        Set<T> target_b = null;
        Iterator<Set<T>> it = items.iterator();
        while (it.hasNext()) {
            Set<T> examined_item = it.next();
            if (examined_item.contains(a)) {
                exist_a = true;
                target_a =  examined_item;
            }
            if (examined_item.contains(b)) {
                exist_b = true;
                target_b =  examined_item;
            }
        }
        if (!exist_a && !exist_b) { //both item a and b are NOT in the collection
            Set<T> new_item = Set.of(a, b);
            items.add(new_item);
        } else if (exist_a && !exist_b) { //item a in the collection, b NOT
            items.remove(target_a);
            target_a.add(b);
            items.add(target_a);
        } else if (!exist_a && exist_b) { //item b in the collection, a NOT
            items.remove(target_b);
            target_b.add(a);
            items.add(target_b);
        } else if (exist_a && exist_b && !target_a.equals(target_b)) { //item a and b in the different set in the collection
            items.remove(target_a);
            items.remove(target_b);
            Set<T> new_item = new HashSet<>();
            new_item.addAll(target_a);
            new_item.addAll(target_b);
            items.add(new_item);
        }
    }

    public boolean isConnected(T a, T b) {
        boolean result = false;
        Iterator<Set<T>> iter = items.iterator();
        while (iter.hasNext()) {
            Set<T> tesing_item = iter.next();
            if (tesing_item.contains(a) && tesing_item.contains(b)) {
                result = true;
            }
        }
        return result;
    }



}
