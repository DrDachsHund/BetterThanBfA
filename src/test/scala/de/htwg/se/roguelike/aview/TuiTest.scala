package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.aview.tui.Tui
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model.{Enemy, Level, LevelCreator, Player}
import org.scalatest.{Matchers, WordSpec}

class TuiTest extends WordSpec with Matchers{

  "A Rogue-Like Tui" should {
    val player = Player(name = "Player",posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2",posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies ,level = new Level(10,10))
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
    val controller2 = new Controller(player = player, enemies = enemies ,level = new Level(10,10))
    val tui2 = new Tui(controller2)
    "create a Random Level when state equals startScreen on input 'n'" in {
      tui2.state.processInputLine("n")
      controller2.enemies.size should be(10)
    }

    "move up with input 'w'" in {
      val old = controller.player.posY
      tui.state.processInputLine("w")
      controller.player.posY should be(old-1)
    }
    "move left with input 'a'" in {
      val old = controller.player.posX
      tui.state.processInputLine("a")
      controller.player.posX should be(old-1)
    }
    "move down with input 's'" in {
      val old = controller.player.posY
      tui.state.processInputLine("s")
      controller.player.posY should be(old+1)
    }
    "move right with input 'd'" in {
      val old = controller.player.posX
      tui.state.processInputLine("d")
      controller.player.posX should be(old+1)
    }


    "create a random Level on input 'r'" in {
      tui.state.processInputLine("r")
     // controller.level.map.sizeY should be(9) noch schauen was abgeht
     // controller.level.map.sizeX should be(16)
      controller.enemies.size should be(10)
    }
  }
/*
  "A Rogue-Like TuiFight" should {
    val player = new Player(name = "Player",posX = 0, posY = 0)
    val enemies = Vector(new Enemy(name = "TestE1",posX = 0, posY = 0), new Enemy(name = "TestE2",posX = 1, posY = 0), new Enemy(name = "TestE3",posX = 0, posY = 1))
    val controller = new Controller(player = player, enemies = enemies ,level = new Level(10))
    controller.gameStatus = GameStatus.FIGHT
    val tui = new Tui(controller)
    tui.state = new tui.tuiFight

        "do an attack on input '1'" in {
          val old = controller.strategy.updateToString
          tui.state.processInputLine("1")
          controller.player.health should be(85)

        }

  }
*/
}


