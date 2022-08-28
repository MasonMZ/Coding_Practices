package disjoint_sets;

public interface DisjointSets<T> {
    public void connect(T p, T q);
    public boolean isConnected(T p, T q);
}
