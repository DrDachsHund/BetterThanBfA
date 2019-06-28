package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiFightTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with State tuiFight" should {
    val player = Player(name = "Player", posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiFight(controller,tui)


    "do nothing when state equals tuiFight on input 'q'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be(old)
    }
    "do nothing on when state equals tuiFight bad input like'abc'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("abc")
      controller.strategy.updateToString should be(old)
    }

    "do an attack with input '1'" in {
      val old = controller.currentEnemy
      val oldplayer = Player(name = "Player", posX = 5, posY = 5)
      tui.state.processInputLine("1")
      while ((old.health - controller.player.getAttack) != controller.currentEnemy.health) {
        controller.player = oldplayer
        controller.currentEnemy = old
        tui.state.processInputLine("1")
      }
      controller.currentEnemy.health should be (old.health - controller.player.getAttack)
    }

    "do a block with input '2'" in {
      val player1 = Player(name = "Player", posX = 5, posY = 5)
      val enemies1 = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller1 = new Controller(player = player1, enemies = enemies1, level = new Level(10, 10))
      val tui1 = new Tui(controller1)
      tui1.state = new tuiFight(controller1,tui1)

      val old = controller1.player
      val oldplayer = controller1.currentEnemy
      tui1.state.processInputLine("2")
      while (controller1.player.health != 87) {
        controller1.player = old
        controller1.currentEnemy = oldplayer
        tui1.state.processInputLine("2")
      }
      controller1.player.health should be (controller1.fight.enemyAttack(old,controller1.currentEnemy,"block").health)
    }

    "do a special with input '3'" in {
      val player2 = Player(name = "Player", posX = 5, posY = 5)
      val enemies2 = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller2 = new Controller(player = player2, enemies = enemies2, level = new Level(10, 10))
      val tui2 = new Tui(controller2)
      tui2.state = new tuiFight(controller2,tui2)

      val old = controller2.currentEnemy
      tui2.state.processInputLine("3")
      controller2.currentEnemy.health should be (old.health - controller2.player.getAttack)
    }

    "run with input 'r'" in {
      tui.state = new tuiFight(controller,tui)
      val tuitest = tui.state
      tui.state.processInputLine("r")
      tui.state should not equal tuitest
    }

    "open player inventory with input 'i'" in {
      tui.state = new tuiFight(controller,tui)
      val tuitest = tui.state
      tui.state.processInputLine("i")
      tui.state should not equal tuitest
    }

    "switch to GameOver" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiFight(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.GAMEOVER)
      tui3.state should not equal tui3Test
    }

    "switch to PlayerLevelUP" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiFight(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.PLAYERLEVELUP)
      tui3.state should not equal tui3Test
    }

    "switch to LootEnemy" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiFight(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.LOOTENEMY)
      tui3.state should not equal tui3Test
    }

  }

}
