package de.htwg.se.betterthanbfa.model.CombatComponent

import de.htwg.se.betterthanbfa.model.EnemyComponent.Enemy
import de.htwg.se.betterthanbfa.model.ItemComponent.Potion
import de.htwg.se.betterthanbfa.model.playerComponent.Player

class Fight() {

  var isFighting = true

  def fight(player: Player, enemy: Enemy):Boolean = {
    isFighting = true

    if (player.isAlive && enemy.isAlive && isFighting) {
      return true
    }
    false
  }

  def lootEnemy(player: Player,enemy: Enemy, input: String): Unit = {
      input match {
        case "0" =>
          player.inventory.addWeapon(enemy.weapon)
          println("U took the Weapon")
        case "1" =>
          player.inventory.addShields(enemy.shield)
          println("U took the Shield")
        case "2" =>
          player.inventory.addWeapon(enemy.weapon)
          player.inventory.addShields(enemy.shield)
          println("U took both")
        case _ => println("U took nozhing")
      }
  }

  def collectEXP(player: Player, enemy: Enemy): Unit = {
    if (player.collectExperience(enemy.experience)) {
      println("Level Up: " + player.level)
    }
    println("PlayerEXP:" + player.experience + " u need " + (player.maxExperience - player.experience) + " for the next level")
  }

  def enemyTurn(player: Player, enemy: Enemy): Unit = {
    println(enemy.typ + " Turn:")
    val r = new scala.util.Random
    val x = r.nextInt(2)
    x match {
      case 0 => {
        println("Enemy Attack: ")
        if (player.isBlocking) {
          println("You are trying to block the Attack")
          var x = enemy.attack + enemy.weapon.attack - player.shield.block
          if (x > 0) {
            println("The enemy strikes through ur block with " + x + " dmg")
            player.health -= x
          }
        } else {
          var x = enemy.attack + enemy.weapon.attack
          println("The Enemy hits u with " + x + " dmg")
          player.health -= x
        }
      }
      case 1 => {
        println("Block")
        enemy.isBlocking = true
      }
      case default => println("FEHLER !!!!!!!!!!!!!!!!!!!!!!!!!!")
    }
  }

  def attack(player: Player, enemy: Enemy):Boolean = {
    println("attack")
    if (enemy.isBlocking) {
      println("The Enemy is blocking ur attack")
      var x = player.attack + player.weapon.attack - enemy.shield.block
      if (x > 0) {
        println("You attack for " + x)
        enemy.health -= x
      }
    } else {
      var x = player.attack + player.weapon.attack
      println("You attack for "  + x)
      enemy.health -= x
    }
    true
  }


  def block(player: Player, enemy: Enemy):Boolean = {
    println("block")
    player.isBlocking = true
    true
  }



  def flee(player: Player, enemy: Enemy):Boolean = {
    println("Try to flee")
    val r = new scala.util.Random

    if (r.nextInt(2) == 1) {
      isFighting = false
      player.posX -= 1
      if (player.posX < 0) {
        player.posX = 0
        player.posY -= 1
        if (player.posY < 0) {
          player.posY = 0
        }
      }

        println("You ran like a coward")

    } else {
      println("PLACEHOLDER")
    }

    true
  }


  def playerTurnToString(player: Player, enemy: Enemy): String = {
    var s: String = "Player Turn! \n"
    s += "Player Health: " + player.health + "/" + player.maxHealth + "\n" +
      enemy.typ + " Health: " + enemy.health + "/" + enemy.maxHealth + "\n"
    s += "(0)Attack: - (1)Block: - (2)Items: - (3)Flee:"
    s
  }

  def enemyTurnToString(player: Player, enemy: Enemy): String = {
    "OBSERVERENEMYPLACEHOLDER"
  }

  def fightToString(player: Player,enemy: Enemy): String = {
    var s: String = "Im Fight:\n"
    s += enemy.typ + enemy.weapon.toString + "\n" + enemy.shield.toString
    s
  }

  def lootEnemyToString(enemy: Enemy): String = {
    "Loot: \n[0]" + enemy.weapon.toString + "\n[1]" + enemy.shield.toString + "\n[2] Take all \n[x} Leave"
  }

}
