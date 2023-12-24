import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import javax.swing.JLabel;

public class Game2048Logic implements KeyListener {
    Block[] blocks;
    boolean numFlag;		// 用于判断是否还能加入新的数字
    JLabel scoreValue;		//显示分数
    int[] blocksarr =new int[4];		//保存一行/列方块中的数值
    JFrame myJFrame;
    int score =0;
    Game2048View game2048View;

    //初始化键盘事件
    public Game2048Logic(Game2048View game2048View, JFrame myJFrame, Block[] blocks,boolean numFlag,JLabel scoreValue) {
        // TODO 自动生成的构造函数存根
        this.blocks=blocks;
        this.numFlag=numFlag;
        this.scoreValue =scoreValue;
        this.myJFrame=myJFrame;
        this.game2048View=game2048View;
    }


    @Override
    public void keyPressed(KeyEvent e) {

        int[] blocksarr=getBlock();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                colBlock("up");
                hasEmptyBlock();
                if (!Arrays.equals(blocksarr, getBlock())) {
                    refreshBlock();
                }
                isGameFail("up");
            }
            case KeyEvent.VK_DOWN -> {
                colBlock("down");
                hasEmptyBlock();
                if (!Arrays.equals(blocksarr, getBlock())) {
                    refreshBlock();
                }
                isGameFail("down");
            }
            case KeyEvent.VK_LEFT -> {
                rowBlock("left");
                hasEmptyBlock();
                if (!Arrays.equals(blocksarr, getBlock())) {
                    refreshBlock();
                }
                isGameFail("left");
            }
            case KeyEvent.VK_RIGHT -> {
                rowBlock("right");
                hasEmptyBlock();
                if (!Arrays.equals(blocksarr, getBlock())) {
                    refreshBlock();
                }
                isGameFail("right");
            }
            default -> {
            }
        }
        scoreValue.setText(""+ score);
        win();
    }

    /**
     * 垂直方向方块移动处理函数
     */
    public void colBlock(String direction){
        int tmp1=0;
        int tmp2=0;
        int index=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (blocks[tmp1].getText().trim().isEmpty()) {
                    tmp1+=4;
                    if (tmp1>=16) {
                        break;
                    }
                } else {
                    blocksarr[index]=Integer.parseInt(blocks[tmp1].getText().trim());
                    index+=1;
                    tmp1+=4;
                    if (tmp1>=16 || index>=4) {
                        break;
                    }
                }
            }
            switch (direction) {
                case "up" -> blocksarr = handleBlocksarr(blocksarr);
                case "down" -> blocksarr = reverseArr(handleBlocksarr(reverseArr(blocksarr)));
                default -> {
                }
            }

            for (int j : blocksarr) {
                if (j == 0) {
                    blocks[tmp2].setText("");
                    blocks[tmp2].setBackground(Color.gray);
                } else {
                    blocks[tmp2].setValue(j + "");
                }
                tmp2 += 4;
            }
            index=0;
            tmp1=i+1;
            tmp2=i+1;
            //清空数组blocksarr
            Arrays.fill(blocksarr, 0);
        }
    }

    /**
     * 水平方向方块移动处理函数
     */
    public void rowBlock(String direction) {
        int tmp1=0;
        int tmp2=0;
        int index=0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (blocks[tmp1].getText().trim().isEmpty()) {
                    tmp1+=1;
                    if (tmp1>=16) {
                        break;
                    }
                } else {
                    blocksarr[index]=Integer.parseInt(blocks[tmp1].getText().trim());
                    index+=1;
                    tmp1+=1;
                    if (tmp1>=16 || index>=4) {
                        break;
                    }
                }
            }

            switch (direction) {
                case "left" -> blocksarr = handleBlocksarr(blocksarr);
                case "right" -> blocksarr = reverseArr(handleBlocksarr(reverseArr(blocksarr)));
                default -> {
                }
            }

            for (int j : blocksarr) {
                if (j == 0) {
                    blocks[tmp2].setText("");
                    blocks[tmp2].setBackground(Color.gray);
                } else {
                    blocks[tmp2].setValue(j + "");
                }
                tmp2 += 1;
            }
            index=0;
            //清空数组blocksarr
            Arrays.fill(blocksarr, 0);
        }
    }

    /**
     * 处理并返回一个数组
     */
    public int[] handleBlocksarr(int[] blocksarr) {
        int index=0;
        int[] result=new int[4];
        for (int j : blocksarr) {
            //排序
            if (j != 0) {
                result[index] = j;
                index++;
            }
        }
        if (index==0 || index==1) {
            for (int i = index; i < result.length; i++) {
                result[i]=0;
            }
        } else {
            Arrays.fill(blocksarr, 0);
            switch (index) {
                case 2 -> {
                    if (result[0] == result[1]) {
                        blocksarr[0] = result[0] + result[1];
                        score += result[0] * 2;
                    } else {
                        blocksarr = result;
                    }
                }
                case 3 -> {
                    if (result[0] == result[1]) {
                        blocksarr[0] = result[0] + result[1];
                        score += result[0] * 2;
                        blocksarr[1] = result[2];
                    } else {
                        if (result[1] == result[2]) {
                            blocksarr[0] = result[0];
                            blocksarr[1] = result[1] + result[2];
                            score += result[1] * 2;
                        } else {
                            blocksarr = result;
                        }
                    }
                }
                case 4 -> {
                    if (result[0] == result[1]) {
                        blocksarr[0] = result[0] + result[1];
                        score += result[0] * 2;
                        if (result[2] == result[3]) {
                            blocksarr[1] = result[2] + result[3];
                            score += result[2] * 2;
                        } else {
                            blocksarr[1] = result[2];
                            blocksarr[2] = result[3];
                        }
                    } else {
                        if (result[1] == result[2]) {
                            blocksarr[0] = result[0];
                            blocksarr[1] = result[1] + result[2];
                            score += result[1] * 2;
                            blocksarr[2] = result[3];
                        } else {
                            blocksarr[0] = result[0];
                            blocksarr[1] = result[1];
                            if (result[2] == result[3]) {
                                blocksarr[2] = result[2] + result[3];
                                score += result[2] * 2;
                            } else {
                                blocksarr = result;
                            }
                        }
                    }
                }
                default -> {
                }
            }
            result=blocksarr;
        }
        return result;
    }

    /**
     * 反转数组eg:45000 --> 00054
     */
    public int[] reverseArr(int[] arr) {
        int[] tmp=new int[arr.length];
        int index=arr.length-1;
        for (int j : arr) {
            tmp[index] = j;
            index--;
        }
        return tmp;
    }

    /**
     * 刷新方块，添加新出现的2或4
     */
    public void refreshBlock(){
        while (numFlag) {
            int index=(int) (Math.random()*16);
            if (blocks[index].getText().trim().isEmpty()) {
                if (Math.random()<0.8) {
                    blocks[index].setValue("256");
                } else {
                    blocks[index].setValue("4");
                }
                break;
            }
        }
    }

    /**
     * 判断是否有空的方块
     */
    public void hasEmptyBlock() {
        for (Block block : blocks) {
            if (block.getText().trim().isEmpty()) {
                this.numFlag = true;
                break;
            } else {
                this.numFlag = false;
            }
        }
    }

    /**
     * 判断游戏是否失败
     */
    public void isGameFail(String direction) {
        boolean result=true;	//true代表失败 false代表没有失败
        int tmp=0;
        if (!numFlag) { // 表示没有空的方块
            switch (direction) {
                case "up", "down" -> {
                    for (int i = 0; i < 4; i++) {
                        tmp = i * 4;
                        for (int j = 0; j < 3; j++) {
                            if (blocks[tmp].getText().trim().equals(blocks[tmp + 1].getText().trim())) {
                                result = false;    //游戏未失败
                                break;
                            } else {
                                tmp++;
                            }
                        }
                        if (!result) {
                            break;
                        }
                    }
                }
                case "left", "right" -> {
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 3; j++) {
                            if (blocks[tmp].getText().trim().equals(blocks[tmp + 4].getText().trim())) {
                                result = false;    //游戏未失败
                                break;
                            } else {
                                tmp += 4;
                                if (tmp >= 16) {
                                    break;
                                }
                            }
                        }
                        tmp = i + 1;
                        if (!result) {
                            break;
                        }
                    }
                }
                default -> {
                }
            }
        } else {
            result=false;
        }
        if (result) {
            score=0;
            JOptionPane.showMessageDialog( null , "Game Over",null , JOptionPane.ERROR_MESSAGE) ;
            game2048View.newGame();
        }
    }

    /**
     * 判断游戏是否成功,即成功累加至2048
     */
    public void win() {
        for (Block block : blocks) {
            if (block.getText().trim().equals("2048")) {
                score=0;
                JOptionPane.showMessageDialog(null, "YOU ARE WIN", null, JOptionPane.INFORMATION_MESSAGE);
                game2048View.newGame();
                break;
            }
        }
    }

    /**
     * 获得全部方块内容，用于对比
     */
    public int[] getBlock() {
        int[] blocksarr=new int[16];
        for (int i = 0; i < blocks.length; i++) {
            if (blocks[i].getText().trim().isEmpty()) {
                blocksarr[i]=0;
            } else {
                blocksarr[i]=Integer.parseInt(blocks[i].getText().trim());
            }
        }
        return blocksarr;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO 自动生成的方法存根
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO 自动生成的方法存根
    }

}

