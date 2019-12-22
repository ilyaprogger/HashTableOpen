import org.junit.jupiter.api.*;

import static org.junit.Assert.*;


public class HashTableTest {
    @Test
    void HashTable() {
        HashTableOpen<Integer, Integer> q = new HashTableOpen<>();
        for (int i = 0; i < 20; i++) {
            q.put(i, i + 2);
        }
        assertEquals(3, (int) q.get(1));
        assertEquals(5, (int) q.get(3));
        assertEquals(6, (int) q.get(4));
        assertEquals(20, q.size());
        assertFalse(q.isEmpty());
        q.clear();
        assertEquals(0, q.size());
        HashTableOpen<String, Integer> q2 = new HashTableOpen<>();
        assertTrue(q2.isEmpty());
        q2.put("asd", 1);
        q2.put("asd", 3);
        assertEquals(3, (int) q2.get("asd"));
        assertEquals(1, q2.size());
        assertTrue(q2.contains(3));
        assertTrue(q2.containsKey("asd"));
        assertFalse(q2.containsKey("asd2"));
        HashTableOpen<Integer, Integer> q3 = new HashTableOpen<>();
        for (int i = 0; i < 20; i++) {
            q3.put(i + 2, i + 2);
        }
        assertNull(q3.remove(99));
        q3.remove(3);
        assertFalse(q3.containsKey(3));
    }
}
