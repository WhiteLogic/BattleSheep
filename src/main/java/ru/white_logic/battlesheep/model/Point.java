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
public class Point {

	private int x;
	private int y;

	public int getX () {
		return x;
	}

	public void setX (int x) {
		this.x = x;
	}

	public int getY () {
		return y;
	}

	public void setY (int y) {
		this.y = y;
	}

	public Point (int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static boolean isSame(Point a, Point b) {
		return (a.getX() == b.getX() && a.getY() == b.getY());
	}
}