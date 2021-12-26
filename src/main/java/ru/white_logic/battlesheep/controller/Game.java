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

package ru.white_logic.battlesheep.controller;

import lombok.extern.slf4j.Slf4j;
import ru.white_logic.battlesheep.model.AutoPlayer;
import ru.white_logic.battlesheep.model.Player;
import ru.white_logic.battlesheep.model.Point;

import java.util.Scanner;


/**
 * @author WhiteLogic
 */
@Slf4j
public class Game {

	Player     player;
	Player     computer;
	AutoPlayer ai = new AutoPlayer(1);
	boolean    onePlayerMode;

	public Game (boolean onePlayerMode) {
		this.onePlayerMode = onePlayerMode;
		startGame();
		do {
			autoPlay();
		//	humanPlay();
		} while (player.isAlive());
		finishGame();
	}

	private void startGame () {
		log.info("Game starts");
		player = new Player();
		if (onePlayerMode) {
			log.info("Your field:");
			player.showPlayerField();
		} else {
			computer = new Player();
		}
	}

	private void humanPlay () {
		Scanner scanner = new Scanner(System.in);
		log.info("Enter X:"); // coordinates are inverted
		int y = scanner.nextInt();
		log.info("Enter Y:");
		int x = scanner.nextInt();
		log.info("Player shoot here: {}", player.shoot(new Point(x, y)));
		player.showPlayerShoots();
	}

	private void autoPlay () {
		Point nextShoot = ai.getNextShootPoint();
		if (nextShoot == null) System.exit(0);
		log.info("Computer shoot here: {}", player.shoot(nextShoot));
		player.showPlayerShoots();
	}

	private void finishGame () {
		log.info("End of the game");
	}
}