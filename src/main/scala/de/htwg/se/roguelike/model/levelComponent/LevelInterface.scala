package de.htwg.se.roguelike.model.levelComponent

import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Land, Tile}


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
  val helmet: ArmorInterface
  val chest: ArmorInterface
  val pants: ArmorInterface
  val boots: ArmorInterface
  val gloves: ArmorInterface
  val rightHand: WeaponInterface
  val leftHand: WeaponInterface
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
                 helmet: ArmorInterface = this.helmet,
                 chest: ArmorInterface = this.chest,
                 pants: ArmorInterface = this.pants,
                 boots: ArmorInterface = this.boots,
                 gloves: ArmorInterface = this.gloves,
                 rightHand: WeaponInterface = this.rightHand,
                 leftHand: WeaponInterface = this.leftHand,
                 gulden: Int = this.gulden,
                 killCounter: Int = this.killCounter,
                 direction: Int = this.direction): PlayerInterface

  def getScore(levelDepth: Int): Int

  def lvlUpAttack: PlayerInterface

  def lvlUpMana: PlayerInterface

  def lvlUpHealth: PlayerInterface

  def lvlUp(collectedExp: Int): PlayerInterface

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
                helmet: ArmorInterface = this.helmet,
                chest: ArmorInterface = this.chest,
                pants: ArmorInterface = this.pants,
                boots: ArmorInterface = this.boots,
                gloves: ArmorInterface = this.gloves,
                rightHand: WeaponInterface = this.rightHand,
                leftHand: WeaponInterface = this.leftHand,
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

  val weapons: Vector[WeaponInterface]
  val potions: Vector[PotionInterface]
  val armor: Vector[ArmorInterface]

  def nextInventory(weapons: Vector[WeaponInterface] = this.weapons,
                    potions: Vector[PotionInterface] = this.potions,
                    armor: Vector[ArmorInterface] = this.armor): InventoryInterface

  def invSortPower(): InventoryInterface

  def invSortValue(): InventoryInterface

  def getPotion(index: Int): PotionInterface

  def getArmor(index: Int): ArmorInterface

  def getWeapon(index: Int): WeaponInterface

  def potionsToString(): String

  def weaponsToString(): String

  def armorToString(): String
}

trait ArmorInterface extends ItemInterface {
  val name: String
  val value: Int
  val usable: Boolean
  val armor: Int
  val armorType: String
  val rarity: String
  //val rarity boosts wahrscheinlichkeit gute specails und vll wie viele

  //def randomItem

  //var curse zeigs z.b pro schlag 10 hp verlieren 10%attack miss

  //var special = specaial random!!!!
  //
  //def specals1 ... usw

  override def toString: String = armorType + ": (" + rarity + ") " + name + " armor: " + armor + " value: " + value
}

trait WeaponInterface extends ItemInterface {
  val name: String
  val value: Int
  val usable: Boolean //vll als wert um die waffe benutzen zu können maybe??????????
  val dmg: Int
  val block: Int
  val oneHanded: Boolean
  val rarity: String
  val itemLevel: Int

  /*
  def getAttack():(Int,Int,Int) = { //normalDmg,magicDmg,critDmg/trueDmg,etc
  }
  def special():(Int,Int,Int) = {
  }
  */

  def getScaledWeapon(lvl: Int): WeaponInterface //vll nur lvl übergeben um nicht zu viel zu übergeben wen später components gibt bei anderen ethoden auch schauen und eventuel refactorn!!!

  override def toString: String = "(" + rarity + ") " + name + " dmg: " + dmg + " block: " + block + " value: " + value
}

trait PotionInterface extends ItemInterface {
  val name: String
  val value: Int
  val usable: Boolean
  val power: Int

  def usePotion(player: PlayerInterface): PlayerInterface = {
    if (player.health > player.maxHealth) {
      return player.nextPlayer(health = player.maxHealth)
    }
    if (player.mana > player.maxMana) {
      return player.nextPlayer(mana = player.maxMana)
    }
    player
  }

  override def toString: String = name + " (" + rarity + ") PW:" + power + " VAlue: " + value
}


trait MerchantInterface {
  val posX: Int
  val posY: Int
  val inventory: Vector[ItemInterface]
  val gulden: Int

  def nextMerchant(posX:Int = this.posX, posY:Int = this.posY,
                   inventory:Vector[ItemInterface] = this.inventory, gulden:Int = this.gulden) : MerchantInterface

  def restock(lvl: Int): MerchantInterface

  def sortItems(inventory: Vector[ItemInterface]): Vector[ItemInterface]

}

trait CrateInterface {
  val posX:Int
  val posY:Int
  val inventory: Vector[ItemInterface]

  def nextCrate(posX:Int = this.posX,posY:Int  = this.posY, inventory:Vector[ItemInterface] = this.inventory) : CrateInterface

  def fillCrate(depth: Int, playerlvl: Int): CrateInterface

  def randomCrate(crate: CrateInterface, items: Int, playerlvl: Int): CrateInterface
}

trait PortalInterface {
  val posX:Int
  val posY:Int
  val portalType: Int

  def nextPortal(posX:Int = this.posX,posY:Int = this.posY,portalType: Int = this.portalType) : PortalInterface
}

