package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}

class tuiInventoryMainTest extends WordSpec with Matchers {


  "A Rogue-Like Tui with state tuiLevel" should {
    val player = Player(name = "Player", posX = 5, posY = 5,
      helmet = Armor("Helmet"),
      chest = Armor("Chest"),
      pants = Armor("Pants"),
      boots = Armor("Boots"),
      gloves = Armor("Gloves"),
      rightHand = Weapon("Sword"),
      leftHand = Weapon("Sword"))
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui = new Tui(controller)
    tui.state = new tuiInventoryMain(controller, tui)

    "should sort inventory by Power" in {
      val old = controller.strategy.updateToString
      controller.playerSortInventoryPower()
      controller.strategy.updateToString should be(old)
    }

    "should sort inventory by Value" in {
      val old = controller.strategy.updateToString
      controller.playerSortInventoryValue()
      controller.strategy.updateToString should be(old)
    }

    "do nothing on when state equals startScreen bad input like'abc'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("abc")
      controller.strategy.updateToString should be(old)
    }

    "do nothing when state equals startScreen on input 'q'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be(old)
    }

    "on input H" in {
      tui.state.processInputLine("H")
      controller.player.helmet should be(Armor("noHelmet"))
      controller.player.inventory.armor.size should be(1)
    }

    "on input C" in {
      tui.state.processInputLine("C")
      controller.player.chest should be(Armor("noChest"))
      controller.player.inventory.armor.size should be(2)
    }

    "on input P" in {
      tui.state.processInputLine("P")
      controller.player.pants should be(Armor("noPants"))
      controller.player.inventory.armor.size should be(3)
    }

    "on input B" in {
      tui.state.processInputLine("B")
      controller.player.boots should be(Armor("noBoots"))
      controller.player.inventory.armor.size should be(4)
    }

    "on input G" in {
      tui.state.processInputLine("G")
      controller.player.gloves should be(Armor("noGloves"))
      controller.player.inventory.armor.size should be(5)
    }

    "on input R" in {
      tui.state.processInputLine("R")
      controller.player.rightHand should be(Weapon("rightFist"))
      controller.player.inventory.weapons.size should be(1)
    }

    "on input L" in {
      tui.state.processInputLine("L")
      controller.player.leftHand should be(Weapon("leftFist"))
      controller.player.inventory.weapons.size should be(2)
    }

    "switch to Potions on 1" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("1")
      tui.state should not be(tuitest)
    }
    "switch to Weapon on 2" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("2")
      tui.state should not be(tuitest)
    }
    "switch to Armor on 3" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("3")
      tui.state should not be(tuitest)
    }

    "switch to fight" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.FIGHT)
      tui.state should not be(tuitest)
    }

    "switch to Level" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.LEVEL)
      tui.state should not be(tuitest)
    }

    "switch to inventoryMain" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      controller.setGameStatus(GameStatus.INVENTORY)
      tui.state should be(tuitest)
    }

    "switch to controllerInvenmtoryGameStatus on x" in {
      val player = Player(name = "Player", posX = 5, posY = 5)
      val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2", posX = 1), Enemy(name = "TestE3", posY = 1))
      val controller = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui = new Tui(controller)
      tui.state = new tuiInventoryMain(controller, tui)
      val tuitest = tui.state
      tui.state.processInputLine("x")
      tui.state should not be(tuitest)
    }

  }

}
