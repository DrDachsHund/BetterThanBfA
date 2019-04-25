package de.htwg.se.roguelike.model

//import org.scalatest.{Matchers, WordSpec}
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class EnemyTest extends WordSpec with Matchers {
  "A Enemy" when {
    "new" should {
    val enemy1 = new Enemy(posX = 0,posY = 0)
    "have a name and stats"  in {
      enemy1.name should be("Skeleton")
      enemy1.health should be(100)
      enemy1.attack should be(10)
      enemy1.exp should be(0)
    }
      val enemy2 = Enemy("Enemy Name",500,100,100,0,0)
      "when set with a name and stats" in {
        enemy2.name should be("Enemy Name")
        enemy2.health should be(500)
        enemy2.attack should be(100)
        enemy2.exp should be(100)
      }
      "when alive" in {
        enemy1.isAlive() should be(true)
      }
      val enemy3 = enemy2.copy(health = 0)
      "when not alive" in {
        enemy3.isAlive() should be(false)
      }
      "have a nice String representation when not set" in {
      enemy1.toString should be("Name: " + "Skeleton" +
        "\nhealth: " + "100" +
        "\nAttack: " + "10" +
        "\nExperience: " + "0" +
        "\nposX: " + "0" +
        "\nposY: " + "0")
    }
      "have a nice String representation when set" in {
        enemy2.toString should be("Name: " + "Enemy Name" +
          "\nhealth: " + "500" +
          "\nAttack: " + "100" +
          "\nExperience: " + "100" +
          "\nposX: " + "0" +
          "\nposY: " + "0")
      }
  }}
}
