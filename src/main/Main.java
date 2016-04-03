package main;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
/**
 * Main.java
 *
 * The author disclaims copyright to this source code.  In place of
 * a legal notice, here is a blessing:
 *
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 *
 */

/**
 * @author 陈宇非<yufei.chen@pku.edu.cn>
 * @since 2016年3月24日
 *
 */
public class Main {

	private static JFrame window;
	private static GamePanel gamePanel;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        // 创建窗口
        window = new JFrame("Tetris");
        gamePanel = new GamePanel();
        JPanel testPanel = new JPanel();
        JPanel contentPanel = (JPanel) window.getContentPane();
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.setAlignmentX(SwingConstants.CENTER);
        
        //testPanel.add();
        contentPanel.add(gamePanel);
        
        window.pack();
        window.setResizable(false);
        window.setVisible(true);        	
        
	}

}
