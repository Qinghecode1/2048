import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("2048 Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Game2048View(frame);

        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 请求焦点以确保键盘事件能够被捕获
        frame.requestFocus();
    }
}
