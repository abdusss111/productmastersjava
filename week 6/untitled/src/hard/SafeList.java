package hard;

import java.util.*;

public class SafeList<E> implements List<E> {
    private final List<E> list = new ArrayList<>();

    @Override
    public boolean add(E e) {
        if (e == null || list.contains(e)) {
            return false; // Prevent null values and duplicates
        }
        return list.add(e);
    }

    @Override
    public void add(int index, E e) {
        if (e == null || list.contains(e)) {
            return; // Prevent null values and duplicates
        }
        if (index >= 0 && index <= list.size()) {
            list.add(index, e);
        }
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= list.size()) {
            return null; // Return null instead of throwing an error
        }
        return list.get(index);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= list.size()) {
            return null; // Return null instead of throwing an error
        }
        return list.remove(index);
    }

    @Override
    public E set(int index, E element) {
        if (element == null || list.contains(element) || index < 0 || index >= list.size()) {
            return null;
        }
        return list.set(index, element);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (e != null && !list.contains(e)) {
                list.add(e);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        boolean modified = false;
        for (E e : c) {
            if (e != null && !list.contains(e) && index >= 0 && index <= list.size()) {
                list.add(index++, e);
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
