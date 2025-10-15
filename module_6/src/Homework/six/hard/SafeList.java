// src/Homework/six/hard/SafeList.java
package Homework.six.hard;

import java.util.*;

/**
 * SafeList:
 * - запрещает null
 * - запрещает дубликаты (equals)
 * - методы не бросают исключения (get/set/remove по индексу — "тихие")
 * - get(outOfBounds) -> null
 * - set(outOfBounds | null | дубликат) -> null (и ничего не меняет)
 * - remove(outOfBounds) -> null
 */
public class SafeList<T> implements List<T> {
    private final ArrayList<T> data = new ArrayList<>();

    // --- базовые ---
    @Override public int size() { return data.size(); }
    @Override public boolean isEmpty() { return data.isEmpty(); }

    @Override
    public boolean contains(Object o) {
        if (o == null) return false;
        return data.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        // оборачиваем, чтобы не бросать при next/remove
        final Iterator<T> it = data.iterator();
        return new Iterator<>() {
            @Override public boolean hasNext() { return it.hasNext(); }
            @Override public T next() { return it.hasNext() ? it.next() : null; }
            @Override public void remove() { try { it.remove(); } catch (Exception ignored) {} }
        };
    }

    @Override
    public Object[] toArray() { return data.toArray(); }

    @Override
    public <E> E[] toArray(E[] a) {
        try {
            return data.toArray(a);
        } catch (Exception e) {
            // безопасный возврат — новый массив нулевой длины
            @SuppressWarnings("unchecked")
            E[] empty = (E[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), 0);
            return empty;
        }
    }

    // --- добавление / модификация ---
    @Override
    public boolean add(T e) {
        if (e == null) return false;
        if (contains(e)) return false;
        return data.add(e);
    }

    @Override
    public void add(int index, T element) {
        if (element == null || contains(element)) return;
        if (index < 0 || index > size()) { // за пределами — добавим в конец
            data.add(element);
        } else {
            data.add(index, element);
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (c == null) return false;
        boolean changed = false;
        for (T e : c) changed |= add(e);
        return changed;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (c == null) return false;
        if (index < 0 || index > size()) index = size();
        boolean changed = false;
        int pos = index;
        for (T e : c) {
            int before = size();
            add(pos, e);
            // увеличим pos только если реально вставили (размер вырос)
            if (size() > before) { changed = true; pos++; }
        }
        return changed;
    }

    @Override
    public T set(int index, T element) {
        if (element == null) return null;
        if (index < 0 || index >= size()) return null;
        T current = data.get(index);
        // не даём превратить в дубликат (если element уже где-то есть и это не тот же индекс)
        if (!Objects.equals(current, element) && contains(element)) return null;
        return data.set(index, element);
    }

    // --- удаление ---
    @Override
    public boolean remove(Object o) {
        if (o == null) return false;
        return data.remove(o);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size()) return null;
        return data.remove(index);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (c == null) return false;
        boolean changed = false;
        for (Object o : c) {
            if (o != null) changed |= data.remove(o);
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (c == null) {
            boolean changed = !isEmpty();
            clear();
            return changed;
        }
        // оставим только те, что присутствуют в c
        boolean changed = false;
        for (int i = 0; i < data.size(); ) {
            if (!c.contains(data.get(i))) {
                data.remove(i);
                changed = true;
            } else i++;
        }
        return changed;
    }

    @Override public void clear() { data.clear(); }

    // --- доступ ---
    @Override
    public T get(int index) {
        if (index < 0 || index >= size()) return null;
        return data.get(index);
    }

    @Override public int indexOf(Object o) { return (o == null) ? -1 : data.indexOf(o); }
    @Override public int lastIndexOf(Object o) { return (o == null) ? -1 : data.lastIndexOf(o); }

    @Override
    public ListIterator<T> listIterator() { return listIterator(0); }

    @Override
    public ListIterator<T> listIterator(int index) {
        int safe = Math.max(0, Math.min(index, size()));
        final ListIterator<T> it = data.listIterator(safe);
        return new ListIterator<>() {
            @Override public boolean hasNext() { return it.hasNext(); }
            @Override public T next() { return it.hasNext() ? it.next() : null; }
            @Override public boolean hasPrevious() { return it.hasPrevious(); }
            @Override public T previous() { return it.hasPrevious() ? it.previous() : null; }
            @Override public int nextIndex() { return it.nextIndex(); }
            @Override public int previousIndex() { return it.previousIndex(); }
            @Override public void remove() { try { it.remove(); } catch (Exception ignored) {} }
            @Override public void set(T e) { try { SafeList.this.set(it.previousIndex(), e); } catch (Exception ignored) {} }
            @Override public void add(T e) { try { SafeList.this.add(it.nextIndex(), e); } catch (Exception ignored) {} }
        };
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if (fromIndex < 0) fromIndex = 0;
        if (toIndex > size()) toIndex = size();
        if (fromIndex > toIndex) return new SafeList<>();
        SafeList<T> sub = new SafeList<>();
        for (int i = fromIndex; i < toIndex; i++) sub.add(data.get(i));
        return sub;
    }

    // --- равенство/хеш ---
    @Override public boolean equals(Object o) { return (o instanceof List<?> other) && data.equals(new ArrayList<>(other)); }
    @Override public int hashCode() { return data.hashCode(); }

    @Override public String toString() { return data.toString(); }
}
