package de.htwg.se.betterthanbfa.model

class Fight() {


  object Choices extends Enumeration {
    val Attack,Block,Items,Run = Value
  }


  def fight(player: Player, enemy: Enemy) = {
    while (player.isAlive || enemy.isAlive) {
      while (!playerTurn()) {}
      println("Test")
    }
    

  }

  def attack():Boolean = {
    println("attack")
    true
  }


  def block():Boolean = {
    println("block")
    true
  }

  def items():Boolean = {
    println("items")
    true
  }

  def flee():Boolean = {
    println("flee")
    true
  }


  def playerTurn():Boolean = {
    println("Player Turn!")
    println("(0)Attack: - (1)Block: - (2)Items: - (3)Flee:")
    var input = readLine()
    input match {
      case "0" => attack()
      case "1" => block()
      case "2" => items()
      case "3" => flee()
      case _ => println("Falsche Eingabe!")
        false
    }
  }








}
