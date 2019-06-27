package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.aview.tui
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model.{Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiLevelTest extends WordSpec with Matchers{

  "A Rogue-Like Tui with state tuiLevel" should {
    val player = Player(name = "Player", posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiLevel(controller,tui)

    "move up with input 'w'" in {
      val old = controller.player.posY
      tui.state.processInputLine("w")
      controller.player.posY should be(old - 1)
    }
    "move left with input 'a'" in {
      val old = controller.player.posX
      tui.state.processInputLine("a")
      controller.player.posX should be(old - 1)
    }
    "move down with input 's'" in {
      val old = controller.player.posY
      tui.state.processInputLine("s")
      controller.player.posY should be(old + 1)
    }
    "move right with input 'd'" in {
      val old = controller.player.posX
      tui.state.processInputLine("d")
      controller.player.posX should be(old + 1)
    }
    "create a random Level on input 'r'" in {
      tui.state.processInputLine("r")
      controller.enemies.size should be(10)
    }

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

    "switch to state Inventory when pressing 'i'" in {
      val tuiTest = tui.state
      tui.state.processInputLine("i")
      tui.state should not equal(tuiTest)
    }

    val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui3 = new Tui(controller3)
    "should not change to wrong game status" in {
      controller3.setGameStatus(GameStatus.GAMEOVER)
      val tui3Test = tui3.state
      tui3.state.handle()
      tui3.state should be(tui3Test)
    }
  }

}
