import javax.swing.*;
public class Main
{
    public static void main(String[] args)
    {
        JFrame frame = new JFrame("2048 Game");//名字
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置窗口框架的默认关闭操作，关了就是结束

        Game2048View gameView = new Game2048View(frame);

        frame.setSize(400, 400);//大小
        frame.setLocationRelativeTo(null);//将窗口框架的位置设置为相对屏幕中心，以便最大化显示窗口。
        frame.setVisible(true);//窗口可视化

        // 请求焦点以确保键盘事件能够被捕获
        frame.requestFocus();
    }
}
