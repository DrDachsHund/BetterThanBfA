package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.aview.tui
import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiLevelTest extends WordSpec with Matchers{

  "A Rogue-Like Tui with state tuiLevel" should {
    val player = Player(name = "Player", posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, level = new Level(10, 10))
    controller.enemies = enemies
    val tui = new Tui(controller)
    tui.state = new tuiLevel(controller, tui)

    "move up with input 'w'" in {
      val old = controller.player.posY
      tui.state.processInputLine("w")
      controller.player.posY should be(old - 1)
    }
    "create z random Level on input 'z'" in {
      val old = controller.player.posY
      tui.state.processInputLine("z")
      controller.player.posY should be(old + 1)
    }
    "create y random Level on input 'y'" in {
      val old = controller.player.posY
      tui.state.processInputLine("y")
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


    val oldY = controller.player.posY
    val oldX = controller.player.posY
    "save file with 'f'" in {
      tui.state.processInputLine("f")
      controller.player.posY should be(oldY)
      controller.player.posX should be(oldX)
    }
    "load file with 'l'" in {
      controller.player = controller.player.nextPlayer(posX = 20,posY = 20)
      tui.state.processInputLine("l")
      controller.player.posY should be(oldY)
      controller.player.posX should be(oldX)
    }

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


    "create a Red Portal at lvlDepth % 11" in {
      controller.setGameStatus(GameStatus.LEVEL)
      val old = controller.strategy.updateToString
      controller.lvlDepth = 10
      controller.createPortal()
      controller.strategy.updateToString should not be(old)
    }

    "create a Merchant every 5 Levels" in {
      controller.setGameStatus(GameStatus.LEVEL)
      val old = controller.strategy.updateToString
      controller.lvlDepth = 5
      controller.createMerchant()
      controller.strategy.updateToString should not be(old)
    }

    "switch to state Inventory when pressing 'i'" in {
      val tuiTest = tui.state
      tui.state.processInputLine("i")
      tui.state should not equal(tuiTest)
    }


    "switch to levelstate" in {
      val controller3 = new Controller(player = player, level = new Level(10, 10))
      controller3.enemies = enemies
      val tui3 = new Tui(controller3)
      tui3.state = new tuiLevel(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.LEVEL)
      tui3.state should be(tui3Test)
    }

    "switch to fight" in {
      val controller3 = new Controller(player = player, level = new Level(10, 10))
      controller3.enemies = enemies
      val tui3 = new Tui(controller3)
      val tui3Test = tui3.state
      tui3.state = new tuiLevel(controller3,tui3)
      controller3.setGameStatus(GameStatus.FIGHT)
      tui3.state should not be(tui3Test)
    }

    "switch to merchant" in {
      val controller3 = new Controller(player = player, level = new Level(10, 10))
      controller3.enemies = enemies
      val tui3 = new Tui(controller3)
      val tui3Test = tui3.state
      tui3.state = new tuiLevel(controller3,tui3)
      controller3.setGameStatus(GameStatus.MERCHANT)
      tui3.state should not be(tui3Test)
    }

    "switch to Crate" in {
      val controller3 = new Controller(player = player, level = new Level(10, 10))
      controller3.enemies = enemies
      val tui3 = new Tui(controller3)
      val tui3Test = tui3.state
      tui3.state = new tuiLevel(controller3,tui3)
      controller3.setGameStatus(GameStatus.CRATE)
      tui3.state should not be(tui3Test)
    }


    "should not change to wrong game status" in {
      val controller3 = new Controller(player = player, level = new Level(10, 10))
      controller3.enemies = enemies
      val tui3 = new Tui(controller3)
      tui3.state = new tuiLevel(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.GAMEOVER)
      tui3.state should be(tui3Test)
    }

    "switch to BossFight" in {
      val controller3 = new Controller(player = player, level = new Level(10, 10))
      controller3.enemies = enemies
      val tui3 = new Tui(controller3)
      tui3.state = new tuiLevel(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.BOSSFIGHT)
      tui3.state should not equal (tui3Test)
    }
  }

}
