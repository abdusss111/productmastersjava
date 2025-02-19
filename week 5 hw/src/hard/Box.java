package hard;

public class Box<T> {
    private T item;

    public Box(T item) {
        this.item = item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }

    public void showType() {
        System.out.println("Тип: " + item.getClass().getName());
    }
}

