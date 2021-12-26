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

import lombok.extern.slf4j.Slf4j;


/**
 * @author WhiteLogic
 */
@Slf4j
public class AutoPlayer {

	private final Point[] track = new Point[(Field.MAP_SIZES.getX() + 1) * (Field.MAP_SIZES.getY() + 1)];
	private int lastGotPoint = -1;

	public AutoPlayer (int level) {
		if (level == 1) generateTrackLevel1();
	}

	private void generateTrackLevel1 () {
		int i = 0;
		for (int x = 0; x <= Field.MAP_SIZES.getX(); x++) {
			for (int y = 0; y <= Field.MAP_SIZES.getY(); y++) {
				if (i < track.length) {
					track[i] = new Point(x, y);
					i++;
				}
			}
		}
	}

	public Point getNextShootPoint () {
		lastGotPoint++;
		if (lastGotPoint < track.length) {
			log.info("OK");
			return track[lastGotPoint];
		}
		return null;
	}
}