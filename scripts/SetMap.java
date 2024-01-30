package scripts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class SetMap extends JFrame implements MapConfig{
    //用来选择素材的下拉表(前面)
    JComboBox<ImageIcon> box;
    //用来选择素材层数的下拉表
    JComboBox<Integer> boxType;


    //用来储存建立的地图数组的二维数组   (map1路  map2障碍物 map3陷阱（判定失败） map4判定通关素材)
    static int[][] map1 = new int[40][40];
    static int[][] map2 = new int[40][40];
    static int[][] map3 = new int[40][40];
    static int[][] map4 = new int[40][40];

    //用来存储对应的图片的二维数组 （这里的icon数组，是用来将我们设置好的数组在界面上显示出来）
    static ImageIcon[][] icons1 = new ImageIcon[40][40];
    static ImageIcon[][] icons2 = new ImageIcon[40][40];
    static ImageIcon[][] icons3 = new ImageIcon[40][40];
    static ImageIcon[][] icons4 = new ImageIcon[40][40];


    //编辑中的地图显示的面板
    static JPanel panel;
    public static void main(String[] args) {
        SetMap sm = new SetMap();
        sm.init();
    }

    //设置窗体的方法
    public void init(){

        this.setTitle("地图数组生成");
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(3);
        this.setLayout(new FlowLayout());
        //布局管理器


        //设置面板类
        panel = new MySetPanel();
        panel.setPreferredSize(new Dimension(MapWidth, MapHeight));
        JScrollPane jsp = new JScrollPane(panel);
        jsp.setPreferredSize(new Dimension(600, 600));
        jsp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //创建选择素材层的下拉列表 (1表示当前编辑的是第一层的元素，2表示的当前编辑第二层元素，3、4同前面)
        boxType = new JComboBox<Integer>();
        boxType.addItem(1);
        boxType.addItem(2);
        boxType.addItem(3);
        boxType.addItem(4);

        //创建选择素材的下拉列表
        box = new JComboBox<ImageIcon>();
        //将前面配置文件中的所有图片素材放入下拉表的方法
        setBox(box);

        //创建按钮
        JButton create = new JButton("创建");
        create.setActionCommand("create");


        this.add(jsp);
        this.add(boxType);
        this.add(box);
        this.add(create);
        this.setVisible(true);

        //给面板安装鼠标监听器
        PanelListenner plis = new PanelListenner();
        panel.addMouseListener(plis);


        //给按钮安装事件监听器
        Buttonlistenner blis = new Buttonlistenner();
        create.addActionListener(blis);
    }

    //设置地图中的素材下拉表
    public void setBox(JComboBox box){
        for(int i=0; i<allicons.length; i++){
            box.addItem(allicons[i]);
        }
    }

    //地图面板类
    class MySetPanel extends JPanel{

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            for(int i=0; i<MapHeight/eleHeight; i++){
                for(int j=0; j<MapWidth/eleWidth; j++){
                    //画第一层元素
                    if(icons1[i][j]!=null){
                        g.drawImage(icons1[i][j].getImage(), getDrawX(j), getDrawY(i), eleWidth, eleHeight, null);
                    }
                    //画第二层元素
                    if(icons2[i][j]!=null){
                        g.drawImage(icons2[i][j].getImage(), getDrawX(j), getDrawY(i), eleWidth, eleHeight, null);
                    }
                    //画第三层元素
                    if(icons3[i][j]!=null){
                        g.drawImage(icons3[i][j].getImage(), getDrawX(j), getDrawY(i), eleWidth, eleHeight, null);
                    }
                    //第四层
                    if(icons4[i][j]!=null){
                        g.drawImage(icons4[i][j].getImage(), getDrawX(j), getDrawY(i), eleWidth, eleHeight, null);
                    }
                }
            }
        }

        //将数组下标转化成对应的图片左上角坐标
        public int getDrawX(int j){
            int x = j*50;
            return x;
        }

        //将数组下标转化成对应的图片左上角坐标
        public int getDrawY(int i){
            int y = i*50;
            return y;
        }
    }

    //按键监听类
    class Buttonlistenner implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            //如果按下了创建按钮，就保存当前设置好的4个二维数组
            if(e.getActionCommand().equals("create")){
                try{
                    System.out.println("开始保存");
                    //得到文件输出流
                    FileOutputStream fos = new FileOutputStream(path);
                    //将文件输出流包装成基本数据输出流
                    DataOutputStream dos = new DataOutputStream(fos);
                    //从配置的接口中得到二维数组的大小
                    int i = MapHeight/eleHeight;
                    int j = MapWidth/eleWidth;
                    //先数组的大小写入文件
                    dos.writeInt(i);
                    dos.writeInt(j);
                    //按顺序将四个二维数组写入文件，后面游戏读取地图的时候也是按这种顺序读回来
                    for(int ii=0;ii<i;ii++){
                        for(int jj=0;jj<j;jj++){
                            dos.writeInt(map1[ii][jj]);
                            dos.writeInt(map2[ii][jj]);
                            dos.writeInt(map3[ii][jj]);
                            dos.writeInt(map4[ii][jj]);
                        }
                    }
                    //强制流中的数据完全输出完
                    dos.flush();
                    //关闭输出流
                    dos.close();
                    System.out.println("保存完成");

                }catch(Exception ef){
                    ef.printStackTrace();
                }
            }
        }
    }



    //面板监听类
    class PanelListenner extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            //得到该位置对应的数组下标
            int j = e.getX()/eleWidth;
            int i = e.getY()/eleHeight;
            System.out.println(i+"<>"+j);
            //得到选择框中的图片
            ImageIcon icon = (ImageIcon)box.getSelectedItem();
            //得到该图片的编号，此处默认取名字的前三位数字
            String p = "src/img/";

            int num = strToint((icon.toString().replace(p, "")).substring(0, 3));
            //修改数组中的值
            if((int) boxType.getSelectedItem()==1){
                map1[i][j] = num;
                icons1[i][j] = icon;
            }else if((int) boxType.getSelectedItem()==2){
                map2[i][j] = num;
                icons2[i][j] = icon;
            }else if((int) boxType.getSelectedItem()==3){
                map3[i][j] = num;
                icons3[i][j] = icon;
            } else if ((int)boxType.getSelectedItem()==4) {
                map4[i][j] = num;
                icons4[i][j] = icon;
            }
            System.out.println((int) boxType.getSelectedItem());

            panel.repaint();
        }

        //将三位数的字符串转换成int
        public int strToint(String numstr){
            for(int i=0;i<3;i++){
                if(numstr.charAt(i)!=0){
                    numstr = numstr.substring(i);
                    int num = Integer.parseInt(numstr);
                    return num;
                }
            }
            numstr = numstr.substring(2);
            return Integer.parseInt(numstr);
        }
    }
}