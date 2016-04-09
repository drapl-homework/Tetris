/**
 * DualGamePanel.java
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.sound.midi.*;
import java.io.*;

/**
 * @author 陈宇非<yufei.chen@pku.edu.cn>
 * @since 2016-3-31
 *
 */
public class DualGamePanel extends JPanel implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	GameLabel[] g;
	NextLabel[] nextlabel;
	JPanel[] sidepanel;
	JButton startButton;
	JLabel[] levelLabel;
	JLabel[] scoreLabel;
	Sequencer sequencer;
	boolean isRunning = false;

	public DualGamePanel() {
		g = new GameLabel[2];
		nextlabel = new NextLabel[2];
		JPanel[] nextPanel = new JPanel[2];
		levelLabel = new JLabel[2];
		scoreLabel = new JLabel[2];
		sidepanel = new JPanel[2];
		startButton = new JButton("开始");
		startButton.addActionListener(this);
		for(int i=0; i<2; i++) {
			g[i] = new GameLabel();
			nextlabel[i] = new NextLabel();
			nextPanel[i] = new JPanel();
			sidepanel[i] = new JPanel();
			levelLabel[i] = new JLabel("等级：0     ");
			scoreLabel[i] = new JLabel("积分：0     ");
			
			g[i].setScoreListener(this);
			g[i].setFailureListener(this);
			g[i].setNewTetrominoListener(nextlabel[i]);
			g[i].prepare();
			
			nextPanel[i].add(nextlabel[i]);
			sidepanel[i].add(nextPanel[i]); // 很奇怪，直接放入nextlabel，设置为BoxLayout之后会看不见
			sidepanel[i].add(levelLabel[i]);
			sidepanel[i].add(scoreLabel[i]);
			sidepanel[i].setLayout(new BoxLayout(sidepanel[i], 
					BoxLayout.Y_AXIS));
			
			if(i == 1)
				sidepanel[i].add(startButton);
			
			add(g[i]);
			add(sidepanel[i]);
		}
		
		// 背景音乐播放器构造比较耗时，要在面板初始化时完成。
		try {
			sequencer = MidiSystem.getSequencer();
			sequencer.open();
			File BackMusic = new File(this.getClass().getResource("/ppl.mid").getPath());
			Sequence mySeq = MidiSystem.getSequence(BackMusic);
			sequencer.setSequence(mySeq);
		} catch (Exception e) {
			System.err.println("背景音乐初始化错误，不播放背景音乐。");
		}
		
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	public void actionPerformed(ActionEvent e) {
		// 开始/暂停按钮
		if(e.getSource() == startButton) {
			if(!isRunning) { //开始
				// Start the music
				if(sequencer != null)
					sequencer.start();
				for(GameLabel i : g)
					i.start();
				startButton.setText("暂停");
				isRunning = true;
			} else { // 暂停
				for(GameLabel i : g)
					i.pause();
				requestFocus();
				startButton.setText("开始");
				isRunning = false;
			}
			// 点击按钮后，焦点会被按钮抢占，要把焦点重新移回面板
			requestFocus();
		} else if(e.getSource() == g[0]) {
			// 左边游戏面板
			// 等级/积分更新
			if(e.getActionCommand().equals("score")) {
				levelLabel[0].setText(
						String.format(
								"等级：%-6d", g[0].getLevel()));
				scoreLabel[0].setText(
						String.format(
								"积分：%-6d", g[0].getScore()));
			} else if(e.getActionCommand().equals("failure")) {
				// 游戏失败
				JOptionPane.showMessageDialog(null,
						"右边的玩家取得了胜利！");
				startButton.setText("结束");
				startButton.setEnabled(false);
				for(GameLabel i : g)
					i.pause();
				isRunning = false;
			}
		} else if(e.getSource() == g[1]) {
			// 右边游戏面板
			// 等级/积分更新
			if(e.getActionCommand().equals("score")) {
				levelLabel[1].setText(
						String.format(
								"等级：%d", g[1].getLevel()));
				scoreLabel[1].setText(
						String.format(
								"积分：%d", g[1].getScore()));
			} else if(e.getActionCommand().equals("failure")) {
				// 游戏失败
				JOptionPane.showMessageDialog(null,
						"左边的玩家取得了胜利！");
				startButton.setText("结束");
				startButton.setEnabled(false);
				for(GameLabel i : g)
					i.pause();
				isRunning = false;
			}
		}
	}

	/**
	 * 处理按键事件
	 */
	public void keyPressed(KeyEvent e) {
		// 按任意键开始
		if(!isRunning) {
			startButton.doClick();
			return; 
		}
		
		switch(e.getKeyCode()) {
		// 将按键事件代理到左边面板
		case KeyEvent.VK_W:
			e.setKeyCode(KeyEvent.VK_UP);
			g[0].keyPressed(e);
			break;
		case KeyEvent.VK_A:
			e.setKeyCode(KeyEvent.VK_LEFT);
			g[0].keyPressed(e);
			break;
		case KeyEvent.VK_S:
			e.setKeyCode(KeyEvent.VK_DOWN);
			g[0].keyPressed(e);
			break;
		case KeyEvent.VK_D:
			e.setKeyCode(KeyEvent.VK_RIGHT);
			g[0].keyPressed(e);
			break;
		case KeyEvent.VK_SPACE:
			g[0].keyPressed(e);
			break;

		// 将按键事件代理到右边面板
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
			g[1].keyPressed(e);
			break;
			
		case KeyEvent.VK_ENTER:
			e.setKeyCode(KeyEvent.VK_SPACE);
			g[1].keyPressed(e);
			break;
		}
	}

	/**
	 * 此函数不使用
	 */
	public void keyReleased(KeyEvent arg0) {
	}

	/**
	 * 此函数不使用
	 */
	public void keyTyped(KeyEvent arg0) {		
	}
}
