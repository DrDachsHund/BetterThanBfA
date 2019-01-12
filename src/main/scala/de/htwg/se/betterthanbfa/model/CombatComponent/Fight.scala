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
      println("Player Health: " + player.health + "/" + player.maxHealth + "\n{" +
        enemy.typ + " Health: " + enemy.health + "/" + enemy.maxHealth + "\n" + enemy.weapon.toString + "\n" + enemy.shield.toString  + " }")
      while (!playerTurn(player,enemy)) {}
      if (enemy.isAlive && isFighting) {
        enemy.isBlocking = false
        enemyTurn(player,enemy)
      }
    }
    if (!enemy.isAlive && player.isAlive) {
      if (player.collectExperience(enemy.experience)) {
        println("Level Up: " + player.level)
      }
      println("PlayerEXP:" + player.experience + " u need " + (player.maxExperience - player.experience) + " for the next level")

      println("Loot: \n[0]" + enemy.weapon.toString + "\n[1]" + enemy.shield.toString + "\n[2] Take all \n[x} Leave")
      var input = readLine()
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
  }

  private def enemyTurn(player: Player, enemy: Enemy): Unit = {
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

  //private
  private def attack(player: Player, enemy: Enemy):Boolean = {
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
    var y = new Potion()
    y.randomPotion(player)
    player.inventory.addPotion(y)
    var z = new Potion()
    z.randomPotion(player)
    player.inventory.addPotion(z)

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

    var test = true
    var help = ""
    var input = ""
    while(test) {

      println("[0]Heal(" + heal + ")\n")
      println("[1]HeavyHeal(" + heavyHeal + ")\n")
      println("[2]Mana(" + mana + ")\n")
      println("[3]HeavyMana(" + heavyMana + ")\n")
      println("[x]Belibige Taste zum zurueck gehen")

      input = readLine()
      input match {
        case "0" => help = "Heal"
          if (heal > 0) test = false
        case "1" => help = "HeavyHeal"
          if (heavyHeal > 0) test = false
        case "2" => help = "Mana"
          if (mana > 0) test = false
        case "3" => help = "HeavyMana"
          if (heavyMana > 0) test = false
        case _ => {
          println("Falsche Eingabe!")
           return false
        }
      }
    }

    for (i <- player.inventory.potions.toList) {
      if (help == i.typ) {
        if (input == "0" || input == "1") {
          player.heal(i.power)
      } else {
          player.refresh(i.power)
        }
        player.inventory.removePotion(i)
        return true
      }
    }

    false
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
      case "2" => if(!items(player, enemy)) return false
      case "3" => flee(player, enemy)
      case _ => {
        println("Falsche Eingabe!")
        return false
      }
    }
    true
  }



}
