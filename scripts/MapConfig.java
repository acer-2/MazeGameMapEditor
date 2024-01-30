package scripts;

import javax.swing.*;

public interface MapConfig {
    //素材的大小
    int eleWidth = 50;
    int eleHeight = 50;
    //地图的大小
    int MapWidth = 2000;
    int MapHeight= 2000;
    //地图保存的位置：根据实际位置修改
    String path = "D:\\desktop\\java_project\\MazeGame_MapEditor\\src\\Map\\Map1.map";

    //用到的图片素材
    ImageIcon icon0 = new ImageIcon("src/img/100GrassLand.png");
    ImageIcon icon1 = new ImageIcon("src/img/101GreenTree.png");
    ImageIcon icon2 = new ImageIcon("src/img/102Hill.png");
    ImageIcon icon3 = new ImageIcon("src/img/103cactus.png");
    ImageIcon icon4 = new ImageIcon("src/img/104FallenTree.png");
    ImageIcon icon5 = new ImageIcon("src/img/105Hole.png");
    ImageIcon icon6 = new ImageIcon("src/img/106tombStone.png");
    ImageIcon icon7 = new ImageIcon("src/img/107road1.png");
    ImageIcon icon8 = new ImageIcon("src/img/108Blank.png");
    ImageIcon icon9 = new ImageIcon("src/img/109road2.png");
    ImageIcon icon10 = new ImageIcon("src/img/110water.png");
    ImageIcon icon11 = new ImageIcon("src/img/111coinStack.png");
    ImageIcon icon12 = new ImageIcon("src/img/112RedTree.png");
    //将所有的图片素材对象放入一个数组中，便于窗体上的下拉列表添加所有的图片素材
    ImageIcon[] allicons = {icon0, icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9, icon10, icon11, icon12};
}
