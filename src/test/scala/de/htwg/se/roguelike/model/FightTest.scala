package de.htwg.se.roguelike.model

import org.scalatest.WordSpec

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FightTest extends WordSpec with Matchers{
  "The Class Fight" when {
    "new" should {
      val fight = new Fight
      "when interaction" in {
        fight.interaction(new Player(name = "Test",posX = 0,posY = 0),Vector(new Enemy(posX = 0,posY = 0))) should be(true)
      }
      "when no interaction" in {
        fight.interaction(new Player(name = "Test",posX = 1,posY = 0),Vector(new Enemy(posX = 0,posY = 0))) should be(false)
      }
    }}


}
