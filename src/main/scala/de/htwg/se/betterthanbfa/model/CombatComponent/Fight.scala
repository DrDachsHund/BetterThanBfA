package de.htwg.se.betterthanbfa.model.CombatComponent

import de.htwg.se.betterthanbfa.model.EnemyComponent.Enemy
import de.htwg.se.betterthanbfa.model.playerComponent.Player

class Fight() {

  def fight(player: Player, enemy: Enemy) = {
    while (player.isAlive && enemy.isAlive) {
      while (!playerTurn(player,enemy)) {}
      println("Test")
    }
  }


  //private
  private def attack(player: Player, enemy: Enemy):Boolean = {
    println("attack")
    enemy.health -= player.attack
    println("Player Health: " + player.health + "Enemy Health: " + enemy.health)
    true
  }


  private def block():Boolean = {
    println("block")
    true
  }

  private def items():Boolean = {
    println("items")
    true
  }

  private def flee():Boolean = {
    println("flee")
    true
  }


  private def playerTurn(player: Player, enemy: Enemy):Boolean = {
    println("Player Turn!")
    println("(0)Attack: - (1)Block: - (2)Items: - (3)Flee:")
    var input = readLine()
    input match {
      case "0" => attack(player: Player, enemy: Enemy) // ersma weil ka wie sonst
      case "1" => block()
      case "2" => items()
      case "3" => flee()
      case _ => println("Falsche Eingabe!")
        false
    }
  }








}
