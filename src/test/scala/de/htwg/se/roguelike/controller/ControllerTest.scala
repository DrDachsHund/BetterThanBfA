package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model.{Enemy, Level, Player, Tile}
import de.htwg.se.roguelike.util.Observer
import org.scalatest.{Matchers, WordSpec}

class ControllerTest extends WordSpec with Matchers {

  "A Controller" when {
    "observed by an Observer" should {
      val smallLevel = new Level(10)
      val player1 = new Player("New Player",posX = 5,posY = 5)
      val enemies1 = Vector(new Enemy("TestEnemy",posX = 5,posY = 6), new Enemy("TestEnemy2",posX = 7,posY = 8))
      val controller = new Controller(smallLevel,player1,enemies1)
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update: Unit = {updated = true; updated}
      }
      controller.add(observer)

      "notify its Observer after creating Level" in {
        controller.createLevel
        observer.updated should be (true)
        controller.level.map.size should be (10)
      }

      "notify its Observer after not interacting" in {
        controller.interaction
        observer.updated should be (true)
        controller.moveDown
        controller.interaction should be (true)
      }

      "notify its Observer after creating a random Level" in {
        controller.createRandomLevel
        observer.updated should be (true)
        controller.level.map.size should be (10)
        controller.enemies.size should be (10)
      }

      "notify its Observer after moving Up" in {
        val old = controller.player.posY
        controller.moveUp
        observer.updated should be (true)
        controller.player.posX should be (5)
        controller.player.posY should be (old-1)
      }


      "notify its Observer after moving Down" in {
        val old = controller.player.posY
        controller.moveDown
        observer.updated should be (true)
        controller.player.posX should be (5)
        controller.player.posY should be (old+1)
      }

      "notify its Observer after moving Right" in {
        val old = controller.player.posX
        controller.moveRight
        observer.updated should be (true)
        controller.player.posX should be (old+1)
        controller.player.posY should be (6)
      }

      "notify its Observer after moving Left" in {
        val old = controller.player.posX
        controller.moveLeft
        observer.updated should be (true)
        controller.player.posX should be (old-1)
        controller.player.posY should be (6)
      }




    }
  }
}
