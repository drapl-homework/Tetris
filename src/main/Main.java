package main;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JDialog;
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
 * @author 陈宇非<yufei.chen@pku.edu.cn>，沈茜<shenqian569@163.com>
 * @since 2016年3月24日
 *
 */
public class Main {

	private static JFrame window;
	private static JPanel gamePanel;
	static JButton singleGameButton, dualGameButton, aboutButton;
	static JDialog about;
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		window = new JFrame("请选择");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel contentPanel = (JPanel) window.getContentPane();
        contentPanel.setLayout(new GridLayout(0, 3));
        singleGameButton = new JButton("单人游戏");
        singleGameButton.setPreferredSize(new Dimension(200, 200));
        dualGameButton = new JButton("双人游戏");
        about = new JDialog(window);
        aboutButton = new JButton("关于");
        
        
        contentPanel.add(singleGameButton);
        contentPanel.add(dualGameButton);
        contentPanel.add(aboutButton);
        
        about.getContentPane().add(new JLabel("俄罗斯方块游戏！玩家可自由选择单人或者双人模式。请打开音响，我们还有音乐哦！制作者：陈宇非，沈茜"));
		about.setSize(700, 150);
        
        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        
        singleGameButton.addActionListener(e -> {
        	window.dispose();
        	singleGame();
        });
        
        dualGameButton.addActionListener(e -> {
        	window.dispose();
        	dualGame();
        });
        
        aboutButton.addActionListener(e->{
        	about.setVisible(true);
        });
	}
	
	/**
	 * 开始单人游戏
	 */
	static void singleGame() {
        // 创建窗口
        window = new JFrame("Tetris");
        /* TODO: 创建一个启动画面，其中有三个按钮：
         *  “单人游戏”、“双人游戏”和“关于”。
         * 根据玩家的选择进入相应的页面
         */
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
        
        JOptionPane.showMessageDialog(null,
				"单人游戏：\n" +
				"玩家按按上/下/左/右/空格键控制。\n");
	}
	
	/**
	 * 开始双人游戏
	 */
	static void dualGame() {
        // 创建窗口
        window = new JFrame("Tetris 双人游戏");
        gamePanel = new DualGamePanel();
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
        
        JOptionPane.showMessageDialog(null,
				"双人游戏：\n" +
				"左边玩家按W/A/S/D/空格键控制，\n" +
				"右边玩家按上/下/左/右/回车键控制。\n");
	}

}
