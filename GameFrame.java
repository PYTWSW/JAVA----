package edu.csuft.wsw.go;

import javax.swing.*;

/**
 * 游戏窗口
 *
 * @author wsw
 */
public class GameFrame extends JFrame {

    /**
     * 游戏面板
     */
    GamePanel gamePanel;

    /**
     * 依赖游戏的模型
     */
    GameModel model;

    /**
     * 定义游戏窗口
     */
    public GameFrame(GameModel model)
    {
        this.model = model;

        //标题
        setTitle("五子棋 - wsw作品");
        //大小不可调整
        setResizable(false);
        //在屏幕的中间显示
        setLocationRelativeTo(null);
        //大小
        setSize(600,622);
        //关闭窗口则退出程序（进程）
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //添加游戏面板
        gamePanel = new GamePanel(model);
        setContentPane(gamePanel);

        //可视化
        setVisible(true);
    }

}
