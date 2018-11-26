package de.htwg.se.betterthanbfa.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when { "new" should {
    val player = Player("Your Name")
    "have a name"  in {
      player.name should be("Your Name")
      player.health should be(100)
      player.mana should be(100)
      player.level should be(1)
      player.experience should be(0)
    }
    "not changed" in {
      player.isAlive should be(true)
    }
    "health set 0 or below" should {
      "return false" in {
        player.health = 0
        player.isAlive should be(false)
      }
    }
    "have a nice String representation" in {
      player.toString should be(
        "Name: " + "Your Name" +
          "\nLevel: " + "1" +
          "\nHealth: " + "0" +
          "\nStamina: " + "100" +
          "\nExperience: " + "0")
    }
  }}


}
