package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiStartScreenTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with state startScreen" should {
    val player = Player(name = "Player", posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, level = new Level(10, 10))
    controller.enemies = enemies
    val tui = new Tui(controller)
    "do nothing when state equals startScreen on input 'q'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be(old)
    }
    "do nothing on when state equals startScreen bad input like'99999'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("99999")
      controller.strategy.updateToString should be(old)
    }
    "create a dev Level when state equals startScreen on input 'd'" in {
      tui.state.processInputLine("d")
      controller.enemies.size should be(3)
    }
    val controller2 = new Controller(player = player, level = new Level(10, 10))
    controller2.enemies = enemies
    val tui2 = new Tui(controller2)
    "create a Random Level when state equals startScreen on input 'n'" in {
      tui2.state.processInputLine("n")
      controller2.enemies.size should be(10)
    }
    val controller3 = new Controller(player = player, level = new Level(10, 10))
    controller3.enemies = enemies
    val tui3 = new Tui(controller3)
    "should not change to wrong game status" in {
      controller3.setGameStatus(GameStatus.FIGHT)
      val tui3Test = tui3.state
      tui3.state.handle()
      tui3.state should be(tui3Test)
    }

  }

}
