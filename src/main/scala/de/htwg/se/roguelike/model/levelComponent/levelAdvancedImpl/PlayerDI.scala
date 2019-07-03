package de.htwg.se.roguelike.model.levelComponent.levelAdvancedImpl

import de.htwg.se.roguelike.model.levelComponent._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Armor, Inventory, Weapon}

case class PlayerDI (name: String,
                  health: Int = 1000,
                  maxHealth: Int = 1000,
                  mana: Int = 1000,
                  maxMana: Int = 1000,
                  attack: Int = 100,
                  lvl: Int = 10,
                  exp: Int = 0,
                  maxExp: Int = 1000,
                  posX: Int = 0, posY: Int = 0,
                  inventory: InventoryInterface = new Inventory,
                  helmet: ArmorInterface = Armor("noHelmet"),
                  chest: ArmorInterface = Armor("noChest"),
                  pants: ArmorInterface = Armor("noPants"),
                  boots: ArmorInterface = Armor("noBoots"),
                  gloves: ArmorInterface = Armor("noGloves"),
                  rightHand: WeaponInterface = Weapon("rightFist"),
                  leftHand: WeaponInterface = Weapon("leftFist"),
                  gulden: Int = 0,
                  killCounter: Int = 0,
                  direction: Int = 0,
                  //necromancer vll companion:Entity2 oder so
                  //merchant vll gold:Int direkt auf 500
                  //mage/archer etc ...
                 ) extends PlayerInterface {

  //muss dan in controller zu Gamestatus levelup wechseln um dann auszuwählen was geändert werden will
  def lvlUp(collectedExp: Int): PlayerDI = {
    var newExp = exp + collectedExp
    var newMaxExp = 0
    var lvlUp = false
    if (newExp >= maxExp) {
      newExp = newExp - maxExp
      newMaxExp = maxExp + (10 * lvl)
      lvlUp = true
    }
    if (lvlUp) return this.copy(exp = newExp, lvl = (lvl + 1), maxExp = newMaxExp, mana = maxMana, health = maxHealth)
    this.copy(exp = newExp)
  }

  def lvlUpHealth: PlayerDI = this.copy(maxHealth = maxHealth + 10, health = maxHealth + 10)

  def lvlUpMana: PlayerDI = this.copy(maxMana = maxMana + 10, mana = maxMana + 10)

  def lvlUpAttack: PlayerDI = this.copy(attack = attack + 10)

  def getScore(levelDepth: Int): Int = {
    println("Um zu schauen wo minus herkommt!!!!!!!!!!!!!!")
    println(gulden + " * " + killCounter + " + " + lvl + " * " + levelDepth)
    gulden * killCounter + lvl * levelDepth
  }

  override def nextPlayer(name: String =  this.name,
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
                          direction: Int = this.direction): PlayerInterface = {
    this.copy(name = name,
      health = health,
      maxHealth = maxHealth,
      mana = mana,
      maxMana = maxMana,
      attack = attack,
      lvl = lvl,
      exp = exp,
      maxExp = maxExp,
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
      killCounter = killCounter,
      direction = direction)
  }

  override def toString: String =
    "Name: " + name +
      "\nhealth: " + health +
      "\nAttack: " + attack +
      "\nExperience: " + exp +
      "\nposX: " + posX +
      "\nposY: " + posY
}
