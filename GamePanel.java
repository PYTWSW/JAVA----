package edu.csuft.wsw.go;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 面板
 *
 * @author wsw
 */

public class GamePanel extends JPanel {

    GameModel model;

    /**
     * 使用鼠标适配器实现点击事件的监听器
     */
    MouseAdapter listener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);

            //每次鼠标点击都创建一个棋子
            Piece piece = new Piece(e.getX(),e.getY());
            piece.isBlack = (pieceList.size() % 2 == 0);  //黑白交替
            pieceList.add(piece);

            //更新模型
            model.update(piece);
            model.show();

            //画布需要重新绘制
            repaint();
        }
    };

    /**
     * 存储所有棋子的容器
     */
    ArrayList<Piece> pieceList = new ArrayList<>();

    /**
     * 棋盘
     */
    Image bg;

    /**
     *创建游戏面板
     */
    public GamePanel(GameModel model) {

        this.model = model;

        try {
            //加载工程目录中的图片
            bg = ImageIO.read(new File("res/Blank_Go_board_9x9.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        //注册一个点击事件
        addMouseListener(listener);
    }


    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D g = (Graphics2D) graphics;

        //开启2D图形渲染的抗锯齿选项
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

        //棋盘
        g.drawImage(bg,0,0,getWidth(),getHeight(),null);

        //棋子

        for(Piece piece : pieceList)
        {
            piece.paint(g);
        }
    }
}
