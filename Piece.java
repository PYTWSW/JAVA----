package edu.csuft.wsw.go;

import java.awt.*;

/**
 * 棋子
 *
 * @author wsw
 */
public class Piece {
    /**
     * x坐标
     */
    int x;

    /**
     * Y坐标
     */
    int y;

    /**
     * 颜色
     */
    boolean isBlack = true;

    /**
     * 棋子大小
     */
    int size = 50;

    /**
     * 落子：创建一个棋子
     *
     * @param x
     * @param y
     */
    public Piece(int x,int y) {
        this.x = x - (size / 2);
        this.y = y - (size / 2);
    }

    /**
     * 绘制（显示在画布中）
     *
     * @param g
     */
    public void paint(Graphics2D g) {

        g.setColor(isBlack? Color.black : Color.white);
        g.fillOval(x,y,size,size);
    }

}
