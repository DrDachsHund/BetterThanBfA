package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.model._
import org.scalatest.{Matchers, WordSpec}

class tuiPlayerLevelUpTest extends WordSpec with Matchers {

  "A Rogue-Like Tui with state tuiPlayerLevelUp" should {

    "do nothing when input 'q'" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be(old)
    }

    "do nothing on wrong input like'abc'" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val old = controller.strategy.updateToString
      tui.state.processInputLine("abc")
      controller.strategy.updateToString should be(old)
    }

    "lvl up health on 1" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("1")
      controller.player.health should be(110)
      controller.player.maxHealth should be(110)
      tui.state should not be(tuitest)
    }

    "lvl up health on 2" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("2")
      controller.player.mana should be(110)
      controller.player.maxMana should be(110)
      tui.state should not be(tuitest)
    }

    "lvl up health on 3" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("3")
      controller.player.attack should be(20)
      tui.state should not be(tuitest)
    }

    "when wrong game status" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.FIGHT)
      tui.state should be(tuitest)
    }

    "when same game status" in {
      val player = Player(health = 75, name = "Player", posX = 5, posY = 5, inventory = new Inventory(Vector(), Vector(Potion("SmallHeal")), Vector()))
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiPlayerLevelUp(controller, tui)
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.PLAYERLEVELUP)
      tui.state should be(tuitest)
    }

  }

}
