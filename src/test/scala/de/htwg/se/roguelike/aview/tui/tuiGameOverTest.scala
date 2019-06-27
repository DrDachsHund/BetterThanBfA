package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model.{Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiGameOverTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with state tuiLevel" should {
    val player = Player(name = "Player", posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiGameOver(controller, tui)

    "do nothing when state equals startScreen on input 'q'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be(old)
    }
    "do nothing on when state equals startScreen bad input like'abc'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("abc")
      controller.strategy.updateToString should be(old)
    }
    "create a Random Level when state equals startScreen on input 'n'" in {
      val controller2 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui2 = new Tui(controller2)
      tui2.state = new tuiGameOver(controller2,tui2)
      tui2.state.processInputLine("n")
      controller2.enemies.size should be(10)
    }

    "switch to Crate" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      val tui3Test = tui3.state
      tui3.state = new tuiGameOver(controller3,tui3)
      controller3.setGameStatus(GameStatus.CRATE)
      tui3.state should not be(tui3Test)
    }

    "switch to GameOver" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiGameOver(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.GAMEOVER)
      tui3.state should be(tui3Test)
    }

    "should not change to wrong game status" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiGameOver(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.FIGHT)
      tui3.state should be(tui3Test)
    }

  }
}
