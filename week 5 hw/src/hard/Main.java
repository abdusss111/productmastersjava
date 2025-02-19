package hard;

public class Main {
    public static void main(String[] args) {
        Box<String> stringBox = new Box<>("Привет, мир!");
        System.out.println("Содержимое: " + stringBox.getItem());
        stringBox.showType();

        Box<MyData> myDataBox = new Box<>(new MyData("Данные пользователя"));
        System.out.println("Содержимое: " + myDataBox.getItem());
        myDataBox.showType();
    }
}

