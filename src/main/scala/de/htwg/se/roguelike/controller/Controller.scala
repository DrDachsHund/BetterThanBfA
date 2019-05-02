package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.util.Observable

class Controller(var level:Level, var player:Player, var enemies:Vector[Enemy] = Vector()) extends Observable {

  def createRandomLevel: Unit = {
    val (level1,enemies1) = new LevelCreator(10).createRandom(player, 10)
    level = level1
    enemies = enemies1
    notifyObservers
  }

  def createLevel: Unit = {
    level = new LevelCreator(10).createLevel(player, enemies)
    notifyObservers
  }

  def interaction: Boolean = {
    val bool = new Fight().interaction(player,enemies)
    notifyObservers
    bool
  }

  def moveUp: Unit = {
    val (level1,player1) = level.moveUp(player)
    level = level1
    player = player1
    notifyObservers
  }

  def moveDown: Unit = {
    val (level1,player1) = level.moveDown(player)
    level = level1
    player = player1
    notifyObservers
  }

  def moveLeft: Unit = {
    val (level1,player1) = level.moveLeft(player)
    level = level1
    player = player1
    notifyObservers
  }

  def moveRight: Unit = {
    val (level1,player1) = level.moveRight(player)
    level = level1
    player = player1
    notifyObservers
  }

  def levelToString: String = level.toString

}
