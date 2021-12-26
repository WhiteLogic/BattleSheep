/*
 * BattleSheep Copyright (C) 2021 WhiteLogic
 *
 * This file is part of BattleSheep.
 *
 *     BattleSheep is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 *     License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 *     BattleSheep is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *     warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *     details.
 *
 *     You should have received a copy of the GNU General Public License along with BattleSheep.
 *     If not, see <https://www.gnu.org/licenses/>.
 */

package ru.white_logic.battlesheep.model;

import java.util.Random;


/**
 * @author WhiteLogic
 */
public class Sheep {

	private static final Random RAND              = new Random();

	private int length;
	private Point[] coordinates     = null;
	private Point[] sheepBorders    = null;
	private Point[] holesFromShoots = null;
	private boolean horizontalSheep;
	private boolean alive = true;

	public Sheep (int length) {
		this.length = length;
		coordinates = new Point[length];
		generateNewSheepPosition();
//		holesFromShoots = new Point[length];
//		System.out.println(this);
	}

	// Creation methods

	public void generateNewSheepPosition () {
		generateRandomOrientationAndFirstPoint();
		generateOtherPoints();
		if (Field.SHEEP_CANT_STAND_TOGETHER) generateSheepBorders();
	}

	private void generateRandomOrientationAndFirstPoint () {
		horizontalSheep = RAND.nextBoolean();
		int x;
		int y;
		do {
			if (horizontalSheep) {
				x = RAND.nextInt(Field.MAP_SIZES.getX() - length + 1); y = RAND.nextInt(Field.MAP_SIZES.getY() + 1);
			} else {
				x = RAND.nextInt(Field.MAP_SIZES.getX() + 1); y = RAND.nextInt(Field.MAP_SIZES.getY() - length + 1);
			}
		} while (x < 0 && y < 0);
		coordinates[0] = new Point(x, y);
	}

	private void generateOtherPoints () {
		for (int i = 1; i < length; i++) {
			if (horizontalSheep) {
				coordinates[i] = new Point(coordinates[i-1].getX() + 1, coordinates[i-1].getY());
			} else {
				coordinates[i] = new Point(coordinates[i-1].getX(), coordinates[i-1].getY() + 1);
			}
		}
	}

	private void generateSheepBorders () {
		sheepBorders = new Point[(length + 2) * 3];
		int step = 1;
		int x = coordinates[0].getX();
		int y = coordinates[0].getY();
		if (horizontalSheep) {
			for (int i = x - 1; i <= length + x; i++) {
				sheepBorders[step * 3 - 3] = new Point(i, y - 1); sheepBorders[step * 3 - 2] = new Point(i, y);
				sheepBorders[step * 3 - 1] = new Point(i, y + 1); step++;
			}
		}
		else {
			for (int i = y - 1; i <= length + y; i++) {
				sheepBorders[step * 3 - 3] = new Point(x - 1, i); sheepBorders[step * 3 - 2] = new Point(x, i);
				sheepBorders[step * 3 - 1] = new Point(x + 1, i); step++;
			}
		}
	}

	// "Check sheep on map" methods

	public boolean isCrossingOtherSheep (int[][] map) {
		if (Field.SHEEP_CANT_STAND_TOGETHER) {
			if (!checkSheepBorders(map)) {
				return true;
			}
		} else {
			for (Point coord : coordinates) {
				if (map[coord.getX()][coord.getY()] != 0) return true;
			}
		}
		return false;
	}

	private boolean checkSheepBorders (int[][] map) {
		for (Point border : sheepBorders) {
			if (border.getX() >= 0 && border.getX() <= Field.MAP_SIZES.getX() && border.getY() >= 0 && border.getY() <= Field.MAP_SIZES.getY() && map[border.getX()][border.getY()] != 0) {
				return false;
			}
		} return true;
	}

	// Battle methods

	public boolean isDamaged (Point shoot) {
		for (int i = 0; i < length; i++) {
			if (coordinates[i] == null) continue;
			if (coordinates[i].getX() == shoot.getX() && coordinates[i].getY() == shoot.getY()) {
				coordinates[i] = null;
				resetAliveStatus();
				return true;
			}
		}
		return false;
	}

	private void resetAliveStatus () {
		alive = false;
		for (Point coordinate : coordinates) {
			if (coordinate != null) alive = true;
		}
	}

	public boolean isAlive () {return alive;}

	// Sheep state methods

	public Point[] getCoordinates () {return coordinates;}

	public String toString () {
		String ret = "Sheep size: "+length+"; Coordinates: {";
		for (Point c : coordinates) {
			ret += "("+c.getX()+", "+c.getY()+")";
		}
		ret += "}; Orientation: "+ (horizontalSheep ? "horizontal" : "vertical") + ";";
		return ret;
	}

//	public boolean makeHoleFromShoot (Point shoot) {
//		alive = false; boolean result = false; for (int i = 0; i < length; i++) {
//			if (Point.isSame(coordinates[i], shoot)) {
//				holesFromShoots[i] = shoot; result = true;
//			} if (holesFromShoots[i] == null) alive = true;
//		} return result;
//	}
}