package de.htwg.se.roguelike.aview.tui

import de.htwg.se.roguelike.controller.Controller
import de.htwg.se.roguelike.model._
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
  }

}
