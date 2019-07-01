package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Crate, Enemy, Level, Player}
import org.scalatest.{Matchers, WordSpec}

class tuiCrateTest extends WordSpec with Matchers {

  "A Tui-Crate" should {
    val crate = Crate()
    val player = Player(name = "Player",posX = 5, posY = 5)
    val enemies = Vector(Enemy(name = "TestE1"), Enemy(name = "TestE2",posX = 1), Enemy(name = "TestE3", posY = 1))
    val controller = new Controller(player = player, enemies = enemies ,level = new Level(10,10))
    val tui = new Tui(controller)
    tui.state = new tuiCrate(controller,tui)
    "do nothing when state equals Crate on input'q'" in {
      val old = controller.strategy.updateToString
      tui.state.processInputLine("q")
      controller.strategy.updateToString should be (old)
    }
    "Choose Items out of the Crate with 1" in {
      val testsize = controller.crate.inventory.size
      tui.state.processInputLine("1")
      controller.crate.inventory.size should be (testsize - 1)
    }
    "go out of that State with 'x'" in {
      tui.state = new tuiCrate(controller,tui)
      val tuitest = tui.state
      tui.state.processInputLine("x")
      tui.state should not equal tuitest
    }
    "do nothing when state equals crate bad input like 'a'" in {
      tui.state = new tuiCrate(controller,tui)
      val old = controller.strategy.updateToString
      tui.state.processInputLine("a")
      controller.strategy.updateToString should be (old)
    }

    "do not create a Create % 3 Level" in {
      tui.state = new tuiCrate(controller,tui)
      val old = controller.strategy.updateToString
      controller.lvlDepth = 1
      controller.createCrate()
      controller.strategy.updateToString should be (old)
    }

    "do create a Create % 3 Level" in {
      tui.state = new tuiCrate(controller,tui)
      val old = controller.strategy.updateToString
      controller.lvlDepth = 3
      controller.createCrate()
      controller.strategy.updateToString should not be (old)
    }

    "switch to levelstate" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiCrate(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.LEVEL)
      tui3.state should not equal (tui3Test)
    }

    "switch to Crate" in {
      val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
      val tui3 = new Tui(controller3)
      tui3.state = new tuiCrate(controller3,tui3)
      val tui3Test = tui3.state
      controller3.setGameStatus(GameStatus.CRATE)
      tui3.state should be (tui3Test)
    }

    val controller3 = new Controller(player = player, enemies = enemies, level = new Level(10, 10))
    val tui3 = new Tui(controller3)
    "should not change to wrong game status" in {
      controller3.setGameStatus(GameStatus.CRATE)
      tui3.state.handle()
      val tui3Test = tui3.state
      tui3.state.handle()
      tui3.state should be(tui3Test)
    }



  }

}
