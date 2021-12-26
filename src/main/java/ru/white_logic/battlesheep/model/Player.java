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

import ru.white_logic.battlesheep.view.MapPrinter;


/**
 * @author WhiteLogic
 */
public class Player {

	private Field    field    = null;
	private char[][] shootMap;

	public Player () {
		try {
			field = new Field();
		}
		catch (Exception e) {
			e.getMessage();
			e.printStackTrace();
		}
		shootMap = Field.generateNewShootMap();
	}

	public String shoot (Point shoot) {
		if (shoot.getX() < 0 || shoot.getX() > Field.MAP_SIZES.getX() ||
		    shoot.getY() < 0 || shoot.getY() > Field.MAP_SIZES.getY()) {
			return "Coordinates are out of field (Х: 0.." + Field.MAP_SIZES.getX() + "; Y: 0.." + Field.MAP_SIZES.getY() + ";). Retry...";
		}
		if (shootMap[shoot.getX()][shoot.getY()] != Field.EMPTY) return "Already shot...";
		char shootResult = field.tryToDamage(shoot);
		shootMap[shoot.getX()][shoot.getY()] = shootResult;
		switch (shootResult) {
			case Field.FIRED:  return "Damaged!!!";
			case Field.KILLED: return "Killed!!!!!";
			case Field.MISSED: return "Missed...";
			default: return "+++++++ ERROR +++++++";
		}
	}

	public boolean isAlive () {
		return field.isSheepAlive();
	}

	public void showPlayerField () {
		MapPrinter.doublePrint(field.getMap(), shootMap);
	}

	public void showPlayerShoots () {
		MapPrinter.print(shootMap);
	}
}