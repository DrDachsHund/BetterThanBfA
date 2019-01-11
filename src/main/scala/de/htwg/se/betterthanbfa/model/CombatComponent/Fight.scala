package de.htwg.se.betterthanbfa.model.CombatComponent

import de.htwg.se.betterthanbfa.model.EnemyComponent.Enemy
import de.htwg.se.betterthanbfa.model.ItemComponent.Potion
import de.htwg.se.betterthanbfa.model.playerComponent.Player

class Fight() {

  var isFighting = true

  def fight(player: Player, enemy: Enemy):Unit = {
    isFighting = true
    while (player.isAlive && enemy.isAlive && isFighting) {
      println("Im Fight:")
      println("Player Health: " + player.health + " " + enemy.typ + " Health: " + enemy.health)
      while (!playerTurn(player,enemy)) {}
      if (enemy.isAlive && isFighting) {
        enemyTurn(player,enemy)
      }
    }
  }

  private def enemyTurn(player: Player, enemy: Enemy): Unit = {
    println(enemy.typ + " Turn:")
    val r = new scala.util.Random
    val x = r.nextInt(2)
    x match {
      case 0 => {
        println("Attack")
        if (player.isBlocking) {
          player.health -= enemy.attack - player.shield.block
        } else {
          player.health -= enemy.attack
        }
      }
      case 1 => {
        println("Block")
        enemy.isBlocking = true
      }
      case default => println("FEHLER !!!!!!!!!!!!!!!!!!!!!!!!!!")
    }
  }

  //private
  private def attack(player: Player, enemy: Enemy):Boolean = {
    println("attack")
    if (enemy.isBlocking) {
      enemy.health -= player.attack + player.weapon.attack - enemy.shield.block
    } else {
      enemy.health -= player.attack + player.weapon.attack
    }
    true
  }


  private def block(player: Player, enemy: Enemy):Boolean = {
    println("block")
    player.isBlocking = true
    true
  }

  private def items(player: Player, enemy: Enemy):Boolean = {
    println("items")
    var x = new Potion()
    x.randomPotion(player)
    player.inventory.addPotion(x)

    var heal = 0
    var heavyHeal = 0
    var mana = 0
    var heavyMana = 0
    for (i <- player.inventory.potions.toList) {
      i.typ match {
        case "Heal" => {
          heal += 1
        }
        case "HeavyHeal" => {
          heavyHeal +=1
        }
        case "Mana" => {
          mana += 1
        }
        case "HeavyMana" => {
          heavyMana += 1
        }
      }
    }

    println("Heal(" + heal + "): 0\n")
    println("HeavyHeal(" + heavyHeal + "): 1\n")
    println("Mana(" + mana + "): 2\n")
    println("HeavyMana(" + heavyMana + "): 3\n")


    ///var x = scala.collection.mutable.Map[String, Integer]()

   // x = player.inventory.potions.groupBy(identity).mapValues(_.size)

    true
  }

  private def flee(player: Player, enemy: Enemy):Boolean = {
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


  private def playerTurn(player: Player, enemy: Enemy):Boolean = {
    println("Player Turn!")
    println("(0)Attack: - (1)Block: - (2)Items: - (3)Flee:")

    player.isBlocking = false

    var input = readLine()
    input match {
      case "0" => attack(player, enemy) // ersma weil ka wie sonst
      case "1" => block(player, enemy)
      case "2" => items(player, enemy)
      case "3" => flee(player, enemy)
      case _ => {
        println("Falsche Eingabe!")
        false
      }
    }
    enemy.isBlocking = false
    true
  }



}
