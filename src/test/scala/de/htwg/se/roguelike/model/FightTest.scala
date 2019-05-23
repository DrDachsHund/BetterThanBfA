package de.htwg.se.roguelike.model

import org.scalatest.WordSpec

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FightTest extends WordSpec with Matchers{
  "The Class Fight" when {
    val player = new Player(name = "Test",posX = 0,posY = 0)
    val enemy = new Enemy(posX = 0,posY = 0)
    "new" should {
      val fight = new Fight
      "when interaction" in {
        fight.interaction(player,Vector(enemy)) should be(true)
      }
      "when no interaction" in {
        val enemies = Vector(new Enemy(posX = 1,posY = 0))
        fight.interaction(player,enemies) should be(false)
      }
      "when playerAttack" in {
        val enemy2 = fight.playerAttack(player,enemy)
        enemy2.health should be(83)
      }
      "when enemyAttack" in {
        val player2 = fight.enemyAttack(player,enemy)
        player2.health should be(85)
      }
      "have a nice String representation" in {
        fight.toString should be("Fight:\n[1]Attack\n[2]:Block\n")
      }
    }}


}
