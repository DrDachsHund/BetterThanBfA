package de.htwg.se.roguelike.model.levelComponent

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Armor, Weapon, _}

trait levelInterface {
  val map: Land[Tile]

  def moveUp(player: PlayerInterface): (levelInterface, PlayerInterface)

  def moveDown(player: PlayerInterface): (levelInterface, PlayerInterface)

  def moveLeft(player: PlayerInterface): (levelInterface, PlayerInterface)

  def moveRight(player: PlayerInterface): (levelInterface, PlayerInterface)

  def removeElement(col: Int, row: Int, value: Int): levelInterface
}

trait EntityInterface {
  val name: String
  val health: Int
  val maxHealth: Int
  val mana: Int
  val maxMana: Int
  val attack: Int
  val lvl: Int
  val exp: Int
  val posX: Int
  val posY: Int
  val inventory: Inventory
  val helmet: Armor
  val chest: Armor
  val pants: Armor
  val boots: Armor
  val gloves: Armor
  val rightHand: Weapon
  val leftHand: Weapon
  val gulden: Int

  def isAlive: Boolean = health > 0

  def getArmor: Int = helmet.armor + chest.armor + pants.armor + boots.armor + gloves.armor

  //Template Method
  def getAttack: Double = attack + rightHand.dmg + (leftHand.dmg / 2)
}

trait PlayerInterface extends EntityInterface {

  val maxExp: Int
  val killCounter: Int
  val direction: Int

  def nextPlayer(name: String = this.name,
                 health: Int = this.health,
                 maxHealth: Int = this.maxHealth,
                 mana: Int = this.mana,
                 maxMana: Int = this.maxMana,
                 attack: Int = this.attack,
                 lvl: Int = this.lvl,
                 exp: Int = this.exp,
                 maxExp: Int = this.maxExp,
                 posX: Int = this.posX,
                 posY: Int = this.posY,
                 inventory: Inventory = this.inventory,
                 helmet: Armor = this.helmet,
                 chest: Armor = this.chest,
                 pants: Armor = this.pants,
                 boots: Armor = this.boots,
                 gloves: Armor = this.gloves,
                 rightHand: Weapon = this.rightHand,
                 leftHand: Weapon = this.leftHand,
                 gulden: Int = this.gulden,
                 killCounter: Int = this.killCounter,
                 direction: Int = this.direction): PlayerInterface

  def getScore(levelDepth: Int): Int

  def lvlUpAttack: Player

  def lvlUpMana: Player

  def lvlUpHealth: Player

  def lvlUp(collectedExp: Int): Player

}

trait EnemyInterface extends EntityInterface {

  val enemyType: Int

  def nextEnemy(name: String = this.name,
                health: Int = this.health,
                maxHealth: Int = this.maxHealth,
                mana: Int = this.mana,
                maxMana: Int = this.maxMana,
                attack: Int = this.attack,
                lvl: Int = this.lvl,
                exp: Int = this.exp,
                posX: Int = this.posX,
                posY: Int = this.posY,
                inventory: Inventory = this.inventory,
                helmet: Armor = this.helmet,
                chest: Armor = this.chest,
                pants: Armor = this.pants,
                boots: Armor = this.boots,
                gloves: Armor = this.gloves,
                rightHand: Weapon = this.rightHand,
                leftHand: Weapon = this.leftHand,
                gulden: Int = this.gulden,
                enemyType: Int = this.enemyType): EnemyInterface

  def setName(): String

  val enemyNames: Array[String]

  def setLoot(): EnemyInterface

  def setScale(lvl: Int): EnemyInterface

  def createRandomBoss(lvl: Int): EnemyInterface
}

