package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model.levelComponent._

import scala.swing.Publisher

trait ControllerInterface extends Publisher {

  var level: LevelInterface
  var player: PlayerInterface
  var enemies: Vector[EnemyInterface]

  val fight:FightInterface
  var gameStatus: GameStatus.Value
  var portal:PortalInterface
  var crate:CrateInterface
  var merchant:MerchantInterface
  var lvlDepth: Int
  var bossfight: Boolean
  var SCALE: Int
  var inventoryGameStatus: GameStatus.Value
  var enemyLoot: Vector[ItemInterface]
  var currentEnemy: EnemyInterface
  var enemyLastAction: String
  var playerLastAction: String
  var strategy: Strategy
  trait Strategy {
    def updateToString: String
  }

  def setSCALE(newSCALE: Int): Unit
  def repaint(): Unit
  def createRandomLevel(): Unit
  def createLevel(): Unit
  def createCrate(): Unit
  def createPortal(): Unit
  def createMerchant(): Unit
  def interaction(): Unit
  def newGame(): Unit
  def moveUp(): Unit
  def moveDown(): Unit
  def moveLeft(): Unit
  def moveRight(): Unit
  def lvlUpHealth(): Unit
  def lvlUpMana(): Unit
  def lvlUpAttack(): Unit
  def checkLvlUp(): Unit
  def lootAll(): Unit
  def lootingEnemy(index: Int): Unit
  def lootAllCrate(): Unit
  def lootingCrate(index: Int): Unit
  def attack(): Unit
  def block(): Unit
  def special(): Unit
  def run(): Unit
  def enemyThinking(playerAction: String): String
  def enemyTurn(playerAction: String, enemyAction: String): Unit
  def setGameStatus(gameStatus: GameStatus.Value): Unit
  def redo(): Unit
  def undo(): Unit
  def usePotion(index: Int): Unit
  def equipArmor(index: Int): Unit
  def unEquipHelmet(): Unit
  def unEquipChest(): Unit
  def unEquipPants(): Unit
  def unEquipBoots(): Unit
  def unEquipGloves(): Unit
  def unEquipRightHand(): Unit
  def unEquipLeftHand(): Unit
  def equipWeapon(hand: Int, index: Int): Unit
  def playerSortInventoryPower() : Unit
  def playerSortInventoryValue() : Unit
  def inventoryAsOneVector(): Vector[ItemInterface]
  def sellItem(index: Int): Unit
  def buyItem(index: Int): Unit
  def restock(): Boolean
  def load(): Unit
  def save(): Unit
}


import scala.swing.event.Event

class TileChanged extends Event
case class LevelSizeChanged(newSize: Int) extends Event
class FightEvent extends Event
class ResolutionEvent extends Event
class RepaintEvent extends Event


