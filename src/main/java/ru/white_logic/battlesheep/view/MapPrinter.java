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

package ru.white_logic.battlesheep.view;

import lombok.extern.slf4j.Slf4j;
import ru.white_logic.battlesheep.model.Field;


/**
 * @author WhiteLogic
 */
@Slf4j
public class MapPrinter {

	public static void print (int[][] map) {
		print(convert(map));
	}

	public static void print (char[][] map) {
		for (char[] chars : map) {
			StringBuilder stringBuilder = new StringBuilder();
			for (char c : chars) {
				stringBuilder.append(c).append(' ');
			}
			log.info(stringBuilder.toString());
		}
	}

	public static void doublePrint (char[][] a, char[][] b) { // convert to logger output
		for (int x = 0; x < a.length; x++) {
			for (int y = 0; y < a[0].length; y++) {
				System.out.print(a[x][y] + " ");
			}
			System.out.print("  |   ");
			for (int y = 0; y < a[0].length; y++) {
				System.out.print(b[x][y] + " ");
			}
			System.out.println();
		}
	}

	public static void doublePrint (int[][] i, char[][] b) {
		doublePrint(convert(i), b);
	}

	private static char[][] convert (int[][] map) {
		int lengthX = map.length;
		int lengthY = map[0].length;
		char[][] c = new char[lengthX][lengthY];
		for (int x = 0; x < lengthX; x++) {
			for (int y = 0; y < lengthY; y++) {
				switch (map[x][y]) {
					case 0:
						c[x][y] = Field.EMPTY;
						break;
					default:
						c[x][y] = Field.ALIVE;
				}
			}
		}
		return c;
	}
}
