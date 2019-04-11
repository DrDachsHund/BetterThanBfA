package de.htwg.se.roguelike.model

import org.scalatest.{Matchers, WordSpec}

class EnemyTest extends WordSpec with Matchers {
  "A Enemy" when {
    "new" should {
    val enemy1 = new Enemy
      val enemy2 = Enemy("Enemy Name")
    "have a name"  in {
      enemy1.name should be("Skeleton")
    }
      "when set with a name" in {
        enemy2.name should be("Enemy Name")
      }
      "have a nice String representation" in {
      enemy2.toString should be("Name: " + "Enemy Name" +
        "\nhealth: " + "100" +
        "\nAttack: " + "10" +
        "\nExperience: " + "0")
    }
  }}
}
