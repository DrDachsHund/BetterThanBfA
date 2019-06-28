package de.htwg.se.roguelike.model

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Enemy, Fight, Player}
import org.scalatest.WordSpec
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class FightTest extends WordSpec with Matchers{

  "The Class Fight" when {
    val player = Player(name = "Test")
    val enemy = Enemy()
    "new" should {
      val fight = new Fight
      "when interaction" in {
        fight.interaction(player,Vector(enemy)) should be(true)
      }
      "when no interaction" in {
        val enemies = Vector(Enemy(posX = 1))
        fight.interaction(player,enemies) should be(false)
      }
      "when playerAttack" in {
        val enemy2 = fight.playerAttack(player,enemy,"")
        enemy2.health should be(83)
      }
      "when playerAttack and enemy block" in {
        val enemy2 = fight.playerAttack(player,enemy,"block")
        enemy2.health should be(86)
      }
      "when enemyAttack" in {
        val player2 = fight.enemyAttack(player,enemy,"")
        player2.health should be(85)
      }
      "when enemyAttack and player block" in {
        val player2 = fight.enemyAttack(player,enemy,"block")
        player2.health should be(87)
      }

      "knows when enemy should block" in {
        var enemy2 = new Enemy(health = 1)
        var block = fight.shouldBlock(player,enemy2)
        block should be("yes")

        enemy2 = new Enemy(health = 1000)
        block = fight.shouldBlock(player,enemy2)
        block should be("no")

        enemy2 = new Enemy(health = 20)
        block = fight.shouldBlock(player,enemy2)
        block should be("maybe")
      }

      "when enemySpecial" in {
        val player2 = fight.enemySpecial(player,enemy)
        player2.health should be(85)
      }

      "when playerSpecial" in {
        val enemy2 = fight.playerSpecial(player,enemy)
        enemy2.health should be(83)
      }

      "have a nice String representation" in {
        fight.toString should be("Fight:\n[1]Attack\n[2]:Block\n[3]:Special\n")
      }
    }}


}
