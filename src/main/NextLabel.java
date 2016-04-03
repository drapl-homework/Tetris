/**
 * NextLabel.java
 *
 * The author disclaims copyright to this source code.  In place of
 * a legal notice, here is a blessing:
 *
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 *
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import tetromino.Tetromino;

/**
 * @author 陈宇非<yufei.chen@pku.edu.cn>
 * @since 2016-3-31
 * 显示下一张骨牌
 */
public class NextLabel extends JLabel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Tetromino t;
	int blockSize =  GameLabel.blockSize / 2;
	int width = 5;
	int height = 5;
	
	public NextLabel() {
		setPreferredSize(new Dimension(width * blockSize, height * blockSize));
		setBackground(Color.BLACK);
		setOpaque(true);
		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		GameLabel g = (GameLabel) e.getSource();
		t = g.getNextTetromino();
		repaint();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if(t == null)
			return;
		Coordinate[] b = t.getBlock(); // 获取下一个方块的坐标
		g.setColor(t.getColor());
		for(int i=0; i<4; i++)				
			g.fillRect((b[i].x + 2) * blockSize,
					// 背景坐标系Y轴方向和Java默认坐标系相反
					(height - b[i].y - 1 - 2) * blockSize,
					blockSize, blockSize);
	}
}
