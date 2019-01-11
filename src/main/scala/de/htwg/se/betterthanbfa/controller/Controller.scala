package de.htwg.se.betterthanbfa.controller

import de.htwg.se.betterthanbfa.model.CombatComponent.Fight
import de.htwg.se.betterthanbfa.model.LevelComponent.Level
import de.htwg.se.betterthanbfa.util.Observable

class Controller(level: Level) extends Observable{

  val fight: Fight = new Fight

  def createLevel(size: Int):Unit = {
    level.createLevel(size)
    notifyObservers
  }

  //def createRandomLevel(size: Int):Unit = {
  //  level.createLevel()
  //}

  def move(move: String):Unit = {
    move match {
      case "w" => level.moveUp()
      case "s" => level.moveDown()
      case "a" => level.moveLeft()
      case "d" => level.moveRight()
    }
    isInFight()
    notifyObservers
  }

  private def isInFight(): Unit = {
    for (x <- level.enemys) {
      if (x.isAlive) {
        level.map(x.posY)(x.posX) = 2
        if (x.posX == level.player.posX && x.posY == level.player.posY) {
          println("Krass am fighten der BOI")
          fight.fight(level.player,x)
        }
      }
    }
    level.map(level.player.posY)(level.player.posX) = 1 // ersma am ende so das man sieht wo er steht
  }

  def levelToString: String = level.toString
  def playerToString: String = level.player.toString

}
