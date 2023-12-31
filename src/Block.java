import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;

public class Block extends JLabel {

    public Block() {
        setHorizontalAlignment(JLabel.CENTER);
        setOpaque(true);
        setBackground(Color.gray); // 设置默认背景颜色
        setFont(new Font("font", Font.PLAIN, 16)); // 设置字体样式
    }

    public void setValue(String value) {
        setText(value);
        // 可以根据值进行其他外观自定义，例如根据不同的值设置不同的背景颜色
        customizeAppearanceBasedOnValue(value);
    }

    private void customizeAppearanceBasedOnValue(String value) {
        switch (value) {
            case "2" -> setBackground(new Color(238, 228, 218)); // 设置背景颜色为"2"的方块颜色
            case "4" -> setBackground(new Color(237, 224, 200)); // 设置背景颜色为"4"的方块颜色
            case "8" -> setBackground(new Color(242, 177, 121));
            case "16" -> setBackground(new Color(245, 149, 99));
            case "32" -> setBackground(new Color(246, 124, 95));
            case "64" -> setBackground(new Color(246, 94, 59));
            case "128" -> setBackground(new Color(237, 207, 114));
            case "256" -> setBackground(new Color(232, 194, 68));
            case "512" -> setBackground(new Color(238, 193, 48));
            case "1024" -> setBackground(new Color(231, 187, 43));
            case "2048" -> setBackground(new Color(241, 188, 7));
            default ->
                // 默认的背景颜色或其他外观设置
                    setBackground(Color.gray);
        }
    }
}
