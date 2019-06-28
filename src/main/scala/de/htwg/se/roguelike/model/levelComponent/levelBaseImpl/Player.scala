package de.htwg.se.roguelike.model.levelComponent.levelBaseImpl

import de.htwg.se.roguelike.model.levelComponent.{EntityInterface, PlayerInterface}

case class Player(name: String,
                  health: Int = 100,
                  maxHealth: Int = 100,
                  mana: Int = 100,
                  maxMana: Int = 100,
                  attack: Int = 10,
                  lvl: Int = 1,
                  exp: Int = 0,
                  maxExp: Int = 100,
                  posX: Int = 0, posY: Int = 0,
                  inventory: Inventory = new Inventory,
                  helmet: Armor = Armor("noHelmet"),
                  chest: Armor = Armor("noChest"),
                  pants: Armor = Armor("noPants"),
                  boots: Armor = Armor("noBoots"),
                  gloves: Armor = Armor("noGloves"),
                  rightHand: Weapon = Weapon("rightFist"),
                  leftHand: Weapon = Weapon("leftFist"),
                  gulden: Int = 0,
                  killCounter: Int = 0,
                  direction: Int = 0,
                  //necromancer vll companion:Entity oder so
                  //merchant vll gold:Int direkt auf 500
                  //mage/archer etc ...
                 ) extends PlayerInterface {

  //muss dan in controller zu Gamestatus levelup wechseln um dann auszuwählen was geändert werden will
  def lvlUp(collectedExp: Int): Player = {
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

  def lvlUpHealth: Player = this.copy(maxHealth = maxHealth + 10, health = maxHealth + 10)

  def lvlUpMana: Player = this.copy(maxMana = maxMana + 10, mana = maxMana + 10)

  def lvlUpAttack: Player = this.copy(attack = attack + 10)

  def getScore(levelDepth: Int): Int = {
    println("Um zu schauen wo minus herkommt!!!!!!!!!!!!!!")
    println(gulden + " * " + killCounter + " + " + lvl + " * " + levelDepth)
    gulden * killCounter + lvl * levelDepth
  }

  override def nextPlayer(name: String =  "DER NEGER GEBÜHRT NACH AFRIKA",
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
