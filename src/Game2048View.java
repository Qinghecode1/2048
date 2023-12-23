import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game2048View implements ActionListener
{
    Block[] blocks;		//方块
    JPanel myJPanel;	//主面板
    JPanel jp1,jp2;		//子面板
    //	int moveFlag;				// 用于累计移动的次数
    boolean numFlag;		// 用于判断是否还能加入新的数字
    JLabel scoreValue;		//显示分数
/**
 * &#064;brief  有参构造函数，将在这个有参构造函数直接实现代码的逻辑功能
 * <p>
 *  */
    public Game2048View(JFrame myJFrame)
    {
        blocks=new Block[16];//用一维数组代替二维数组，作为游戏的16个格子
//		moveFlag=0;
        numFlag=true;// 用于判断是否还能加入新的数字
        this.myJPanel=(JPanel)myJFrame.getContentPane();///获取内容面板,是主函数的面板
        setJp1();//设置一号小面板
        myJPanel.add(jp1, BorderLayout.NORTH);//在主面板把这个放进去
        setJp2();//设置二号小面板
        myJPanel.add(jp2, BorderLayout.CENTER);//同样
        myJFrame.addKeyListener(new Game2048Logic(this,myJFrame,blocks,numFlag,scoreValue));
    }
    /*
* @brief gbc控制组件的布局和定位。其中包括组件的位置、大小、权重等信息。gbc 对象包含了以下属性：
    gridwidth：组件水平所占用的格子数。如果为 0，则表示该组件是该行的最后一个组件。
    gridheight：组件垂直所占用的格子数。
    weightx：组件水平的拉伸幅度。如果为 0，则表示不拉伸；如果不为 0，则表示随着窗口增大进行拉伸，0 到 1 之间。
    weighty：组件垂直的拉伸幅度。如果为 0，则表示不拉伸；如果不为 0，则表示随着窗口增大进行拉伸，0 到 1 之间。
    gridx 和 gridy：组件在网格中的行和列位置。
    fill：组件的填充方式。如果为 GridBagConstraints.BOTH，则表示组件既可以水平填充也可以垂直填充，即组件会自动适应容器的大小和形状。
* */
    public void addc(JPanel jp1,Component component, GridBagConstraints gbc,int gridwidth,int gridheight, int weightx,int weighty,int gridx,int gridy) {
        //此方法用来添加控件到容器中
        gbc.gridwidth=gridwidth;		//该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        gbc.gridheight=gridheight;		//该方法是设置组件垂直所占用的格子数
        gbc.weightx=weightx;				//该方法设置组件水平的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        gbc.weighty=weighty;				//该方法设置组件垂直的拉伸幅度，如果为0就说明不拉伸，不为0就随着窗口增大进行拉伸，0到1之间
        gbc.gridx=gridx;
        gbc.gridy=gridy;
        gbc.fill=GridBagConstraints.BOTH;
        jp1.add(component,gbc);//jp1 组件中添加一个组件component，并使用GridBagConstraints对象gbc来设置该组件的布局参数。
    }
    public void setJp1()
    {
        GridBagLayout gbLayout=new GridBagLayout();//网格生成
        jp1=new JPanel(gbLayout);
        //主题放置
        JPanel Jtitle=new JPanel();
        JLabel title=new JLabel("2048");//题目
        title.setFont(new Font("font", Font.PLAIN, 45));//类型、风格、大小
        title.setHorizontalAlignment(JLabel.LEFT);//jLabel的文本左右对齐属性设置方法,对齐方式
        Jtitle.add(title);
        jp1.add(Jtitle);
        //分数放置
        JPanel Jscore =new JPanel(new GridLayout(2, 1));//new GridLayout(2, 1)为网格布局样式。其中的参数“2”“1”分别为网格的“行数”和“列数”。
        JLabel score=new JLabel("Score");
        score.setFont(new Font("font", Font.PLAIN, 16));
        score.setHorizontalAlignment(JLabel.CENTER);
        scoreValue =new JLabel("0");
        scoreValue.setFont(new Font("font", Font.PLAIN, 16));
        scoreValue.setHorizontalAlignment(JLabel.CENTER);
        Jscore.add(score);
        Jscore.add(scoreValue);
        jp1.add(Jscore);
        //提示放置
        JPanel Jnotice =new JPanel();
        JLabel noite=new JLabel("方向键移动数字累加至2048");
        noite.setFont(new Font("font", Font.PLAIN, 14));
        noite.setHorizontalAlignment(JLabel.LEFT);
        Jnotice.add(noite);
        jp1.add(Jnotice);
        //重新开始键放置
        JPanel JnewGame=new JPanel();
        JButton newGame=new JButton("New Game");
        newGame.setHorizontalAlignment(JButton.CENTER);
        newGame.addActionListener(this);
        JnewGame.add(newGame);
        jp1.add(JnewGame);
        //网格放置
        GridBagConstraints gbc=new GridBagConstraints();
        addc(jp1, Jtitle, gbc, 3, 2, 60, 60, 0, 0);
        addc(jp1, Jscore, gbc, 0, 2, 40, 60, 3, 0);
        addc(jp1, Jnotice, gbc, 3, 1, 60, 40, 0, 2);
        addc(jp1, JnewGame, gbc, 0, 1, 40, 40, 3, 2);
    }

    public void setJp2()
    {
        addBlock();
        initBlock();
    }
    public void addBlock()
    {
        jp2=new JPanel();
		/*
		 * setLayout是对当前组件设置为流式布局.组件在窗体中从左到右依次排列 如果排到行的末尾 换行排列
		 * GridLayout(int rows, int cols, int hgap, int vgap)
			创建具有指定行数和列数的网格布局。
			rows - 该 rows 具有表示任意行数的值
			cols - 该 cols 具有表示任意列数的值
			hgap - 水平间距
			vgap - 垂直间距
		 */
        jp2.setLayout(new GridLayout(4, 4, 5, 5));
        for (int i = 0; i < blocks.length; i++)
        {
            blocks[i]=new Block();
            blocks[i].setHorizontalAlignment(JLabel.CENTER);// 不透明的标签
            blocks[i].setOpaque(true);// 设置控件不透明
            jp2.add(blocks[i]);
        }
    }
    /**
     * 初始化方块
     */
    public void initBlock()
    {
        while (numFlag)
        {
            int index=(int) (Math.random()*16);
            if (blocks[index].getText().trim().equals("")) {
                blocks[index].setValue("2");
                break;
            } else {
                continue;
            }
        }
    }
    /**
     * @brief 把格子设为空，内容设为0
     * */
    public void newGame()
    {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].setValue("");
        }
        numFlag=true;
        scoreValue.setText("0");
        initBlock();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
// TODO 自动生成的方法存根
        newGame();
    }
}
