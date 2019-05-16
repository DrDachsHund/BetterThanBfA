package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.util.Observable

class Controller(var level:Level, var player:Player, var enemies:Vector[Enemy] = Vector()) extends Observable {

  val fight = new Fight
  var gameStatus = GameStatus.LEVEL

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

  def interaction: Unit = {
    if (fight.interaction(player,enemies)) {
      gameStatus = GameStatus.FIGHT
      //toStringHandler(gameStatus)
    }
  }

  /*
  def move(val (level1,player1)) {
    level = level1
    player = player1
    interaction
    notifyObservers
    gameStatus
  }
  */
  def moveUp: GameStatus.gameStatus = {
    val (level1,player1) = level.moveUp(player)
    level = level1
    player = player1
    interaction
    notifyObservers
    gameStatus
  }

  def moveDown: GameStatus.gameStatus = {
    val (level1,player1) = level.moveDown(player)
    level = level1
    player = player1
    interaction
    notifyObservers
    gameStatus
  }

  def moveLeft: GameStatus.gameStatus = {
    val (level1,player1) = level.moveLeft(player)
    level = level1
    player = player1
    interaction
    notifyObservers
    gameStatus
  }

  def moveRight: GameStatus.gameStatus = {
    val (level1,player1) = level.moveRight(player)
    level = level1
    player = player1
    interaction
    notifyObservers
    gameStatus
  }

  //Fight----

  def attack():GameStatus.gameStatus = {
    var enemy = fight.getEnemy(player,enemies)
    enemies = enemies.filterNot(_ == enemy)
    enemy = fight.playerAttack(player,enemy)
    player = fight.enemyAttack(player,enemy)

    if (!player.isAlive()) gameStatus = GameStatus.GAMEOVER
    else if (!enemy.isAlive()) {
      gameStatus = GameStatus.LEVEL
      level = level.removeElement(enemy.posY,enemy.posX,5)
    } //LOOT
    else {
      gameStatus = GameStatus.FIGHTSTATUS
      enemies = enemies :+ enemy
    }
    notifyObservers
    gameStatus
  }

  //Fight----

  def updateToString: String = {
    if (gameStatus == GameStatus.LEVEL) level.toString
    else if(gameStatus == GameStatus.FIGHT) fight.toString
    else if (gameStatus == GameStatus.FIGHTSTATUS) fightStatus
    else "FEHLER"
  }


  def fightStatus:String = {
    "Player Health: " + player.health + "\n" +
    "Enemy Health: " + fight.getEnemy(player,enemies).health + "\n"
  }
  /*
  def fightToString = println(fight.toString)
  def levelToString = println(level.toString)

  def toStringHandler(e: GameStatus.gameStatus ) = {
    e match {
      case GameStatus.LEVEL => updateToString = levelToString
      case GameStatus.FIGHT => updateToString = fightToString
    }
  }
  */
}
