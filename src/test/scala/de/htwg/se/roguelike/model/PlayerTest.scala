package de.htwg.se.roguelike.model

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerTest extends WordSpec with Matchers {
  "A Player" when {
    "new" should {
    val player = Player("Your Name")
    "have a name"  in {
      player.name should be("Your Name")
    }
    "have a nice String representation" in {
      player.toString should be(
        "Name: " + "Your Name" +
          "\nhealth: " + "100" +
          "\nAttack: " + "10" +
          "\nExperience: " + "0" +
          "\nposX: " + "0" +
          "\nposY: " + "0")
    }
      "when... alive" in {
        player.isAlive() should be(true)
      }
      val player2 = player.copy(health = 0)
      "when not alive" in {
        player2.isAlive() should be(false)
      }
  }}


}
