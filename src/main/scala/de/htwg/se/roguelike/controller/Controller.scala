package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.util.{Observable, UndoManager}

class Controller(var level:Level, var player:Player, var enemies:Vector[Enemy] = Vector()) extends Observable {

  val fight = new Fight
  var gameStatus = GameStatus.LEVEL
  private val undoManager = new UndoManager

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
      strategy = new StrategyFight
    }
  }

  def moveUp: Unit = {
    undoManager.doStep(new MoveCommand(level.moveUp(player),enemies,this))
    notifyObservers
  }

  def moveDown: Unit = {
    undoManager.doStep(new MoveCommand(level.moveDown(player),enemies,this))
    notifyObservers
  }

  def moveLeft: Unit = {
    undoManager.doStep(new MoveCommand(level.moveLeft(player),enemies,this))
    notifyObservers
  }

  def moveRight: Unit = {
    undoManager.doStep(new MoveCommand(level.moveRight(player),enemies,this))
    notifyObservers
  }

  //Fight----

  def attack():Unit = {
    var enemy = fight.getEnemy(player,enemies)
    enemies = enemies.filterNot(_ == enemy)

    enemy = fight.playerAttack(player,enemy)
    player = fight.enemyAttack(player,enemy)

    if (!player.isAlive()) gameStatus = GameStatus.GAMEOVER
    else if (!enemy.isAlive()) {
      gameStatus = GameStatus.LEVEL
      strategy = new StrategyLevel
      level = level.removeElement(enemy.posY,enemy.posX,5)
    } //LOOT
    else {
      gameStatus = GameStatus.FIGHTSTATUS
      strategy = new StrategyFightStatus
      enemies = enemies :+ enemy
    }
    notifyObservers
  }

  //Fight----

  //Strategy Pattern toString---
  var strategy: Strategy = new StrategyLevel

  trait Strategy {
    def updateToString:String
  }
  class StrategyLevel extends Strategy {
    override def updateToString = level.toString
  }
  class StrategyFight extends Strategy {
    override def updateToString = fight.toString
  }
  class StrategyFightStatus extends Strategy {
    override def updateToString = fightStatus
  }
  def fightStatus:String = {
    "Player Health: " + player.health + "\n" +
      "Enemy Health: " + fight.getEnemy(player,enemies).health + "\n"
  }
  //Strategy Pattern toString---

  //UndoManager---
  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }
  //UndoManager---




}
