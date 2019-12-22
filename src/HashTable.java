public interface HashTable<K, V> {
    //Размер таблицы
    public int size();
    //Кладем элемент
    public V put(K key, V value);
    //Очищаем таблицу
    public void clear();
    //Получаем элемент по ключу
    public V get(Object key);
    //Епсли таблица пустая true
    public boolean isEmpty();
    //Содержится ли элемент с таким значением в таблице
    public boolean contains(Object value);
    //Удаление элемента по ключу
    public V remove(Object key);
    //Содержится ли такой ключ в таблице
    public boolean containsKey(Object key);
}
