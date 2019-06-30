package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent._

import scala.util.Random

case class Enemy(name: String = "Empty-Name",
                 health: Int = 100,
                 maxHealth: Int = 100,
                 mana: Int = 100,
                 maxMana: Int = 100,
                 attack: Int = 10, //maybe 7 oder 8 attack für bessere Balance
                 lvl: Int = 0,
                 exp: Int = 20,
                 posX: Int = 0, posY: Int = 0,
                 inventory: InventoryInterface = new Inventory(Vector(), Vector(), Vector()),
                 helmet: ArmorInterface = Armor("noHelmet"),
                 chest: ArmorInterface = Armor("noChest"),
                 pants: ArmorInterface = Armor("noPants"),
                 boots: ArmorInterface = Armor("noBoots"),
                 gloves: ArmorInterface = Armor("noGloves"),
                 rightHand: WeaponInterface = Weapon("rightFist"),
                 leftHand: WeaponInterface = Weapon("leftFist"),
                 gulden: Int = 1,
                 enemyType: Int = 0) extends EnemyInterface {

  override def nextEnemy(name: String = this.name,
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
                         enemyType: Int = this.enemyType): EnemyInterface = {
    this.copy(name = name,
      health = health,
      maxHealth = maxHealth,
      mana = mana,
      maxMana = maxMana,
      attack = attack,
      lvl = lvl,
      exp = exp,
      posX = posX, posY = posY,
      inventory = inventory,
      helmet = helmet,
      chest = chest,
      pants = pants,
      boots = boots,
      gloves = gloves,
      rightHand = rightHand,
      leftHand = leftHand,
      gulden = gulden,
      enemyType = enemyType)
  }

  def setName(): String = {
    enemyNames(enemyType)
  }

  val enemyNames = Array("Test", "Blue-Slime", "Red-Slime", "Green-Slime")


  //Template Method
  override def getAttack: Double = super.getAttack - 2

  def setLoot(): Enemy = {
    val random = Random
    val index = random.nextInt(1 + (lvl / 15))
    var enemy = this
    for (_ <- 0 to index) {
      enemy = enemy.loot()
    }
    enemy
  }

  private def loot(): Enemy = {
    val random = Random
    val itemType = random.nextInt(3) + 1
    itemType match {
      case 1 =>
        val randomWeapon = Weapon("random").getScaledWeapon(lvl) //vll noch while weapon rarity hoch nochmal rollen
        this.copy(rightHand = randomWeapon, inventory = inventory.nextInventory(weapons = inventory.weapons :+ randomWeapon))
      case 2 => this.copy(inventory = inventory.nextInventory(potions = inventory.potions :+ Potion("random")))
      case 3 =>
        val armor = Armor("random")
        armor match {
          case helm: Helmet => this.copy(inventory = inventory.nextInventory(armor = inventory.armor :+ armor), helmet = armor) //Evemtuel noch sclae hinzufügen
          case chest: Chest => this.copy(inventory = inventory.nextInventory(armor = inventory.armor :+ armor), chest = armor)
          case pants: Pants => this.copy(inventory = inventory.nextInventory(armor = inventory.armor :+ armor), pants = armor)
          case boots: Boots => this.copy(inventory = inventory.nextInventory(armor = inventory.armor :+ armor), boots = armor)
          case gloves: Gloves => this.copy(inventory = inventory.nextInventory(armor = inventory.armor :+ armor), gloves = armor)
        }
    }
  }

  def setScale(lvl: Int): Enemy = {
    val random = new Random()
    val lvlBuffer = random.nextInt(4) //0-3
    random.nextInt(2) match {
      case 0 => this.copy(lvl = lvl + lvlBuffer, health = 75 + (lvl + lvlBuffer) * 25, maxHealth = 75 + (lvl + lvlBuffer) * 25, exp = exp * (lvl + lvlBuffer), gulden = gulden * (lvl + lvlBuffer), attack = attack + 2 * (lvl + lvlBuffer))
      case 1 =>
        if (lvl - lvlBuffer < 1) {
          return this.copy(lvl = 15, exp = 251, health = 250, maxHealth = 250, mana = 250, maxMana = 250, gulden = 250, name = "Rare-Mob", inventory = new Inventory(Vector(Weapon("random")), Vector(Potion("random")), Vector(Armor("random"))), attack = 25)
        }
        return this.copy(lvl = lvl - lvlBuffer, health = 75 + (lvl - lvlBuffer) * 25, maxHealth = 75 + (lvl - lvlBuffer) * 25, exp = exp * (lvl - lvlBuffer), gulden = gulden * (lvl - lvlBuffer), attack = attack + 2 * (lvl - lvlBuffer))
    }
  }

  def createRandomBoss(lvl: Int): Enemy = {
    var boss = this.copy(health = 50 * lvl, maxHealth = 50 * lvl, mana = 50 * lvl, maxMana = 50 * lvl, attack = 4 * lvl, lvl = lvl)
    boss = boss.setLoot()
    boss
  }


  override def toString: String =
    "Name: " + name +
      "\nhealth: " + health +
      "\nAttack: " + attack +
      "\nExperience: " + exp +
      "\nposX: " + posX +
      "\nposY: " + posY

}
