package de.htwg.se.roguelike.model.levelComponent

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._

trait LevelInterface {
  val map: Land[Tile]

  def moveUp(player: PlayerInterface): (LevelInterface, PlayerInterface)

  def moveDown(player: PlayerInterface): (LevelInterface, PlayerInterface)

  def moveLeft(player: PlayerInterface): (LevelInterface, PlayerInterface)

  def moveRight(player: PlayerInterface): (LevelInterface, PlayerInterface)

  def removeElement(col: Int, row: Int, value: Int): LevelInterface
}

trait LevelCreatorInterface {
  val sizeY: Int
  val sizeX: Int

  def createLevel(player: PlayerInterface, enemies: Vector[EnemyInterface]): LevelInterface

  def createRandom(player: PlayerInterface, enemyCount: Int): (LevelInterface, Vector[EnemyInterface])

  def spawnRandomTile(level: LevelInterface, tile: Tile, numberOfTiles: Int): LevelInterface
}

trait FightInterface {

  def interaction(player: PlayerInterface, enemies: Vector[EnemyInterface]): Boolean

  def playerAttack(player: PlayerInterface, enemy: EnemyInterface, enemyAction: String): EnemyInterface

  def enemyAttack(player: PlayerInterface, enemy: EnemyInterface, playerAction: String): PlayerInterface

  def shouldBlock(player: PlayerInterface, enemy: EnemyInterface): String

  def enemySpecial(player: PlayerInterface, currentEnemy: EnemyInterface): PlayerInterface

  def playerSpecial(player: PlayerInterface, currentEnemy: EnemyInterface): EnemyInterface
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
  val inventory: InventoryInterface
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
                 inventory: InventoryInterface = this.inventory,
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
                inventory: InventoryInterface = this.inventory,
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

trait ItemInterface {
  val name: String
  val value: Int
  val usable: Boolean
  val rarity: String
  val textureIndex: Int

  def isUseable: Boolean = usable
}

trait InventoryInterface {

  val weapons: Vector[Weapon]
  val potions: Vector[Potion]
  val armor: Vector[Armor]

  def nextInventory(weapons: Vector[Weapon] = this.weapons,
                    potions: Vector[Potion] = this.potions,
                    armor: Vector[Armor] = this.armor): InventoryInterface

  def invSortPower(): InventoryInterface

  def invSortValue(): InventoryInterface

  def getPotion(index: Int): Potion

  def getArmor(index: Int): Armor

  def getWeapon(index: Int): Weapon

  def potionsToString(): String

  def weaponsToString(): String

  def armorToString(): String
}

trait MerchantInterface {
  val posX: Int
  val posY: Int
  val inventory: Vector[ItemInterface]
  val gulden: Int

  def restock(lvl: Int): MerchantInterface

  def sortItems(inventory: Vector[ItemInterface]): Vector[ItemInterface]

}

trait CrateInterface {
  val posX:Int
  val posX:Int
  val inventory: Vector[ItemInterface]

  def fillCrate(depth: Int, playerlvl: Int): CrateInterface

  def randomCrate(crate: CrateInterface, items: Int, playerlvl: Int): CrateInterface
}

trait PortalInterface {
  val posX:Int
  val posY:Int
  val portalType: Int
}

