/**
 * ITetromino.java
 *
 * The author disclaims copyright to this source code.  In place of
 * a legal notice, here is a blessing:
 *
 *    May you do good and not evil.
 *    May you find forgiveness for yourself and forgive others.
 *    May you share freely, never taking more than you give.
 *
 */
package tetromino;

import java.awt.Color;

import main.Coordinate;
import main.GameLabel;

/**
 * @author QIAN SHEN
 * @since 2016-4-2
 * I形骨牌
 */
public class ITetromino extends Tetromino {
	Color color = new Color(0xE6D933); // 含羞草黄
	static final Coordinate[][] block =
		{
			{
				new Coordinate(0, 0),
				new Coordinate(0, 1),
				new Coordinate(0, -2),
				new Coordinate(0, -1),
			},
			{
				new Coordinate(0, 0),
				new Coordinate(1, 0),
				new Coordinate(-1, 0),
				new Coordinate(-2, 0),
			},
			{
				new Coordinate(0, 0),
				new Coordinate(0, 1),
				new Coordinate(0, -2),
				new Coordinate(0, -1),
			},
			{
				new Coordinate(0, 0),
				new Coordinate(1, 0),
				new Coordinate(-1, 0),
				new Coordinate(-2, 0),
			},
		};
	
	public ITetromino(GameLabel _g) {
		super(_g);
	}

	public Color getColor() {
		return color;
	}
	
	public Coordinate[] getBlock() {
		return block[rotationState];
	}
	
	Coordinate[] getRotatedBlock() {
		return block[(rotationState + 1) % 4];
	}

	boolean isRotationForbidden() {
		return false;
	}
}