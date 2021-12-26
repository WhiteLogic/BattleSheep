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


/**
 * @author WhiteLogic
 */
public class Field {

	public static final char EMPTY = '.';
	public static final char MISSED= 'o';
	public static final char FIRED = 'x';
	public static final char KILLED= 'X';
	public static final char ALIVE = '#';

	private static final int[] RULES_OF_SHEEP_TYPES = {4, 3, 3, 2, 2, 2, 1, 1, 1};
	private static final int   CONSTRUCTION_LIMIT   = 20;

	public static final boolean SHEEP_CANT_STAND_TOGETHER = true;
	public static final Point   MAP_SIZES                 = new Point(9, 9);

	public static int[][] generateNewMap () {
		int[][] m = new int[MAP_SIZES.getX() + 1][MAP_SIZES.getY() + 1];
		for (int i = 0; i <= MAP_SIZES.getX(); i++) {
			for (int j = 0; j <= MAP_SIZES.getY(); j++) {
				m[i][j] = 0;
			}
		}
		return m;
	}
	public static char[][] generateNewShootMap () {
		char[][] sm = new char[MAP_SIZES.getX() + 1][MAP_SIZES.getY() + 1];
		for (int i = 0; i <= MAP_SIZES.getX(); i++) {
			for (int j = 0; j <= MAP_SIZES.getY(); j++) {
				sm[i][j] = EMPTY;
			}
		}
		return sm;
	}

	private int[][] map;
	private Sheep[] sheeps = new Sheep[RULES_OF_SHEEP_TYPES.length];


	public Field () throws Exception {
		int numberOfTry = 0;
		while (!sheepGeneratingIsDone()) {
			numberOfTry++;
			if (numberOfTry >= CONSTRUCTION_LIMIT) throw new Exception("Map construction limit is out");
		}
	}

	// sheep creation methods

	private boolean sheepGeneratingIsDone () {
		map = generateNewMap();
		int sheepID = 0;
		for (int length : RULES_OF_SHEEP_TYPES) {
			sheeps[sheepID] = new Sheep(length);
			int numberOfTry = 0;
			while (sheeps[sheepID].isCrossingOtherSheep(map)) {
				numberOfTry++;
				sheeps[sheepID].generateNewSheepPosition();
				if (numberOfTry >= CONSTRUCTION_LIMIT) return false;
			}
			addSheepOnMap(sheeps[sheepID], sheepID);
			sheepID++;
		}
		return true;
	}

	private void addSheepOnMap (Sheep sheep, int sheepID) {
		for (Point point : sheep.getCoordinates()) {
			if (map[point.getX()][point.getY()] == 0) map[point.getX()][point.getY()] = sheepID + 1;
		}
	}

	// battle methods

	public char tryToDamage (Point shoot) {
		for (int i = 0; i < sheeps.length; i++) {
			if (sheeps[i] == null) continue;
			if (sheeps[i].isDamaged(shoot)) {
				if (sheeps[i].isAlive()) {
					return FIRED;
				} else {
					sheeps[i] = null;
					return KILLED;
				}
			}
		}
		return MISSED;
	}

	// state methods

	public int[][] getMap () {return map;}

	public boolean isSheepAlive () {
		for (int i = 0; i < RULES_OF_SHEEP_TYPES.length; i++) {
			if (sheeps[i] != null) return true;
		}
		return false;
	}
}