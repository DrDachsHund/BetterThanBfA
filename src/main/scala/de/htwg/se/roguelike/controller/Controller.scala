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
      //setGameStatus(GameStatus.FIGHT) //schreibt sonst 2 mal fight
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
      var enemy:Enemy = new Enemy()
      for (enemyTest <- enemies) {
        if (player.posX == enemyTest.posX && player.posY == enemyTest.posY)
          enemy = enemyTest
      }

      enemies = enemies.filterNot(_ == enemy)

      enemy = fight.playerAttack(player, enemy)
      player = fight.enemyAttack(player, enemy)

      if (!player.isAlive()) gameStatus = GameStatus.GAMEOVER
      else if (!enemy.isAlive()) {
        //gameStatus = GameStatus.LEVEL
        //strategy = new StrategyLevel
        setGameStatus(GameStatus.LEVEL)
        level = level.removeElement(enemy.posY, enemy.posX, 5)
      } //LOOT
      else {
        //gameStatus = GameStatus.FIGHTSTATUS
        //strategy = new StrategyFightStatus
        enemies = enemies :+ enemy
        setGameStatus(GameStatus.FIGHTSTATUS)
      }
    setGameStatus(GameStatus.FIGHT)
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
    override def updateToString = fight.toString + "[i]Inventory\n"
  }
  class StrategyFightStatus extends Strategy {
    override def updateToString = fightStatus
  }
  class StrategyInventory extends Strategy {
    override def updateToString =
        "[1]Potions\n" +
        "[2]Weapons\n" +
        "[3]Armor\n" +
        "[x]Back\n"
  }
  class StrategyPotions extends Strategy {
    override def updateToString =
      "Player Health: <" + player.health + ">\n" +
      "Player Mana: <" + player.mana + ">\n" +
      player.inventory.potionsToString + "[x}Back\n"
  }
  class StrategyWeapons extends Strategy {
    override def updateToString = player.inventory.weaponsToString + "[x}Back\n"
  }
  class StrategyArmor extends Strategy {
    override def updateToString = player.inventory.armorToString + "[x}Back\n"
  }
  class StrategyGameOver extends Strategy {
    override def updateToString = "GAME OVER DUDE"
  }
  def fightStatus:String = {
    var sb = new StringBuilder
    sb ++= ("Player Health: <" + player.health + ">\n")
    sb ++= "Enemy Health: "
    for (enemyTest <- enemies) {
      if (player.posX == enemyTest.posX && player.posY == enemyTest.posY)
        sb ++= ("<" + enemyTest.health + ">")
    }
    sb ++= "\n"
    sb.toString
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

  //Inventory---
  def usePotion(index:Int): Unit = {
    if (player.inventory.potions.size < 1) {
      println("Keine Potion Vorhanden!!!")
    } else if (index <= player.inventory.potions.size && index > 0) {
      val potion = player.inventory.getPotion(index)
      player = potion.usePotion(player)
      player = player.copy(inventory = player.inventory.copy(potions = player.inventory.potions.filterNot(_ == potion)))
    } else println("CONTROLLER FALSCHER ODER INKOREKTER INDEX => " + index)
    notifyObservers
  }
  //Inventory---

  def setGameStatus(gameStatus: GameStatus.Value): Unit = {
    this.gameStatus = gameStatus
    gameStatus match {
      case GameStatus.LEVEL => strategy = new StrategyLevel
      case GameStatus.FIGHT => strategy = new StrategyFight
      case GameStatus.FIGHTSTATUS => strategy = new StrategyFightStatus
      case GameStatus.GAMEOVER => strategy = new StrategyGameOver
      case GameStatus.INVENTORY => strategy = new StrategyInventory
      case GameStatus.INVENTORYPOTION => strategy = new StrategyPotions
      case GameStatus.INVENTORYWEAPON => strategy = new StrategyArmor
      case GameStatus.INVENTORYARMOR => strategy = new StrategyArmor
    }
    notifyObservers
  }

}
