import java.util.*;

public class HashTableOpen<K, V> implements Map<K, V> {
    private final float LOAD_FACTOR = 0.75F;
    private final int MULTIPLIER = 2;
    private final int MAX_SIZE = Integer.MAX_VALUE - 3;
    private Set<K> keySet;
    private Set<Map.Entry<K, V>> entrySet;
    private Collection<V> values;

    private HashEntry<?, ?>[] data;
    private int size;
    private int threshold;

    public HashTableOpen(int initialCapacity, float loadFactor) {
        if (initialCapacity == 0)
            initialCapacity = 1;
        data = new HashEntry<?, ?>[initialCapacity];
        threshold = (int) (initialCapacity * loadFactor);
    }

    public HashTableOpen() {
        this(11, 0.75f);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("{");
        int counter = 0;
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] != null) {
                str.append(data[i].toString()).append(", ");
                counter++;
            }
        }
        if (counter == 0) {
            str.delete(0, 1);
            str.append("{Empty HashTable}");
        } else
            str.append("end }");
        return str.toString();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }


    public boolean containsKey(Object key) {
        HashEntry<?, ?>[] tab = data;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        if (data[index] != null) {
            if (data[index].key.equals(key)) {
                return true;
            } else {
                int r = index;
                while (!data[index].key.equals(key)) {
                    index++;
                    if (index >= data.length) {
                        index = 0;
                    }
                    if (index == r) return false;
                }
                return true;
            }
        }
        return false;
    }

    boolean contains(Integer value) {
        return containsValue(value);
    }

    public boolean containsValue(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        HashEntry<?, ?>[] tab = data;
        for (int i = 0; i < tab.length - 1; i++) {
            if (tab[i] != null && tab[i].value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public V put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        HashEntry<?, ?>[] tab = data;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        if (size <= threshold) {
            if (data[index] == null) {
                data[index] = new HashEntry<>(key, value);
            } else if (data[index].key == key) {
                V a = (V) data[index].value;
                data[index] = new HashEntry<>(key, value);
                return a;
            } else {
                while (data[index] != null) {
                    index++;
                    if (index > tab.length) {
                        index = 0;
                    }
                }
                data[index] = new HashEntry<>(key, value);
            }
            size++;
        } else {
            data[index] = new HashEntry<>(key, value);
            rehashUp();
        }
        return value;
    }


    void rehashUp() {
        int oldCapacity = data.length;
        HashEntry<?, ?>[] oldMap = data;
        int newCapacity = oldCapacity * MULTIPLIER + 1;
        HashEntry<?, ?>[] newMap = new HashEntry<?, ?>[newCapacity];
        if (newCapacity - MAX_SIZE > 0) {
            if (oldCapacity == MAX_SIZE)
                return;
            newCapacity = MAX_SIZE;
        }
        threshold = (int) (newCapacity * LOAD_FACTOR);
        size = 0;
        data = newMap;
        for (int i = 0; i < oldMap.length; i++) {
            HashEntry<K, V> old = (HashEntry<K, V>) oldMap[i];
            if (old != null) {
                put((K) old.key, (V) old.value);
            }
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public V remove(Object key) {
        HashEntry<?, ?>[] tab = data;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        V retVal = null;
        if (data[index] != null) {
            if (data[index].key == key) {
                retVal = (V) data[index].value;
                data[index] = null;
            } else {
                int a = index;
                while (data[index] == null || !data[index].key.equals(key)) {
                    index++;
                    if (index >= data.length) {
                        index = 0;
                    }
                    if (a == index) return null;
                }
                retVal = (V) data[index].value;
                data[index] = null;
            }
        }
        size--;
        return retVal;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        HashTableOpen entry = (HashTableOpen) m;
        for (HashEntry<K, V> hashEntry : entry.data) {
            if (hashEntry != null)
                put(hashEntry.getKey(), hashEntry.getValue());
        }
    }

    public void clear() {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] != null) {
                data[i] = null;
                size--;
            }
        }
    }

    @Override
    public Set<K> keySet() {
        keySet = new HashSet<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null)
                keySet.add((K) data[i].getKey());
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        values = new HashSet<>();
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null)
                values.add((V) data[i].getValue());
        }
        return values;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        entrySet = new HashSet<>();
        HashEntry[] s = data;
        for (HashEntry hashEntry : s) {
            if (hashEntry != null)
                entrySet.add(hashEntry);
        }
        return entrySet;
    }

    public V get(Object key) {
        HashEntry<?, ?>[] tab = data;
        int hash = key.hashCode();
        int index = (hash & 0x7FFFFFFF) % tab.length;
        if (data[index] != null) {
            if (data[index].key.equals(key))
                return (V) data[index].value;
            else {
                while (data[index] == null || !data[index].key.equals(key)) {
                    index++;
                    if (index >= tab.length) {
                        index = 0;
                    }
                }
                return (V) data[index].value;
            }
        } else
            return null;
    }

    private static class HashEntry<K, V> implements Map.Entry<K, V> {
        K key;
        V value;

        HashEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            return this.value = value;
        }


        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?, ?> e = (Map.Entry<?, ?>) o;
            return key.equals(e.getKey()) && value.equals(e.getValue());
        }

        @Override
        public int hashCode() {
            return key.hashCode() * value.hashCode();
        }

        @Override
        public String toString() {
            return key.toString() + "=" + value.toString();
        }

    }
}
