package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.aview.tui.Tui
import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model.{Enemy, Level, LevelCreator, Player}
import org.scalatest.{Matchers, WordSpec}

class TuiTest extends WordSpec with Matchers{
/*
  "A Rogue-Like TuiMain" should {
    val player = Player(name = "Player",posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2",posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies ,level = new Level(10,10))
    val tui = new Tui(controller)
    "do nothing  on input 'q'" in {
      tui.state.processInputLine("q")
    }
    "create a Level on input 'n'" in {
      tui.state.processInputLine("n")
      //controller.level should be(new LevelCreator(9,16).createLevel(player,enemies))
      controller.enemies.size should be(10)
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

    "do nothing on bad input like'99999'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("99999")
      controller.strategy.updateToString should be(old)
    }
    "create a random Level on input 'r'" in {
      tui.state.processInputLine("r")
     // controller.level.map.sizeY should be(9) noch schauen was abgeht
     // controller.level.map.sizeX should be(16)
      controller.enemies.size should be(10)
    }
  }
  */
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


