import org.junit.jupiter.api.*;

import static org.junit.Assert.*;


class HashTableTest {
    @Test
    void HashTable() {
        HashTableOpen<Integer, Integer> m = new HashTableOpen<>();
        for (int i = 0; i < 20; i++) {
            m.put(i, i + 2);
        }
        assertEquals(3, (int) m.get(1));
        assertEquals(5, (int) m.get(3));
        assertEquals(6, (int) m.get(4));
        assertEquals(20, m.size());
        assertFalse(m.isEmpty());
        m.clear();
        assertEquals(0, m.size());

        HashTableOpen<String, Integer> m2 = new HashTableOpen<>();
        assertTrue(m2.isEmpty());
        m2.put("asd", 1);
        m2.put("asd", 3);
        assertEquals(3, (int) m2.get("asd"));
        assertEquals(1, m2.size());
        assertTrue(m2.contains(3));
        assertTrue(m2.containsKey("asd"));
        assertFalse(m2.containsKey("asd2"));

        HashTableOpen<Integer, Integer> m3 = new HashTableOpen<>();
        for (int i = 0; i < 20; i++) {
            m3.put(i + 2, i + 2);
        }
        assertNull(m3.remove(99));
        m3.remove(3);
        assertFalse(m3.containsKey(3));

        HashTableOpen<Integer, String > m4 = new HashTableOpen<>();
        HashTableOpen<Integer, String> m5 = new HashTableOpen<>();
        m4.put(1,"Hash");
        m4.put(2,"Bash");
        m5.put(3,"Pash");
        m5.put(4,"Smash");
        m5.putAll(m4);

        assertEquals(m5.toString(),"{1=Hash, 2=Bash, 3=Pash, 4=Smash, end }");
    }
}
