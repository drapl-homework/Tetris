/**
 * GamePanel.java
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
import javax.swing.JPanel;

import javax.sound.midi.*;
import java.io.*;

/**
 * @author 陈宇非<yufei.chen@pku.edu.cn>
 * @since 2016-3-31
 *
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	GameLabel g;
	NextLabel nextlabel;
	JPanel sidepanel;
	JButton startButton;
	JLabel levelLabel;
	JLabel scoreLabel;
	boolean isRunning = false;
	public GamePanel() {
		g = new GameLabel();
		nextlabel = new NextLabel();
		g.setScoreListener(this);
		g.setFailureListener(this);
		g.setNewTetrominoListener(nextlabel);
		g.prepare();
		
		levelLabel = new JLabel("等级：0 ");
		scoreLabel = new JLabel("积分：0  ");
		
		sidepanel = new JPanel();
		JPanel nextPanel = new JPanel();
		startButton = new JButton("开始");
		startButton.addActionListener(this);
		nextPanel.add(nextlabel);
		sidepanel.add(nextPanel); // 很奇怪，直接放入nextlabel，设置为BoxLayout之后会看不见
		sidepanel.add(levelLabel);
		sidepanel.add(scoreLabel);
		sidepanel.add(startButton);
		sidepanel.setLayout(new BoxLayout(sidepanel, 
				BoxLayout.Y_AXIS));
		
		add(g);
		add(sidepanel);
		//setAlignmentX(SwingConstants.CENTER);
		//setFocusable(true);
		addKeyListener(this);
		setFocusable(true);
		requestFocus();
	}

	public void actionPerformed(ActionEvent e) {
		// 开始/暂停按钮
		if(e.getSource() == startButton) {
			if(!isRunning) { //开始
				// Start the music
				Sequencer sequencer;
				try{
					sequencer = MidiSystem.getSequencer();
					sequencer.open();
					
					File BackMusic = new File(this.getClass().getResource("/ppl.mid").getPath());
					//the address above is on my own laptop, TA may need to reset the address to activate the music
					Sequence mySeq = MidiSystem.getSequence(BackMusic);
					sequencer.setSequence(mySeq);
					sequencer.start();
				}catch(Exception e2){
					e2.printStackTrace();
				}
				g.start();
				g.requestFocus();
				startButton.setText("暂停");
				isRunning = true;
			} else { // 暂停
				g.pause();
				requestFocus();
				startButton.setText("开始");
				isRunning = false;
			}
		}
		
		else if(e.getSource() == g) {
			// 等级/积分更新
			if(e.getActionCommand().equals("score")) {
				levelLabel.setText(
						String.format(
								"等级：%d", g.getLevel()));
				scoreLabel.setText(
						String.format(
								"积分：%d", g.getScore()));
			} else if(e.getActionCommand().equals("failure")) { // 游戏失败
				startButton.setText("游戏结束");
				startButton.setEnabled(false);
				isRunning = false;
				requestFocus();
			}
		}
	}

	/**
	 * 按任意键开始
	 */
	public void keyPressed(KeyEvent arg0) {
		startButton.doClick();
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
