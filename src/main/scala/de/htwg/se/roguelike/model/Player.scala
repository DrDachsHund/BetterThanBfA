package de.htwg.se.roguelike.model

case class Player(name: String,
                  health: Int = 100,
                  maxHealth: Int = 100,
                  mana: Int = 100,
                  maxMana: Int = 100,
                  attack: Int = 10,
                  lvl: Int = 1,
                  exp: Int = 90,
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
                  killCounter: Int = 0
                  //necromancer vll companion:Entity oder so
                  //merchant vll gold:Int direkt auf 500
                  //mage/archer etc ...
                 ) extends Entity {

  //muss dan in controller zu Gamestatus levelup wechseln um dann auszuwählen was geändert werden will
  def lvlUp(collectedExp: Int): Player = {
    var newExp = exp + collectedExp
    var newMaxExp = 0
    var lvlUp = false
    while (newExp >= maxExp) {
      newExp = newExp - maxExp
      newMaxExp = (maxExp * 1.5).toInt
      lvlUp = true
      //println("LEVELUP YAAAAAAAAAAAAAAAS QUEEEEN SLAY")
    }
    if (lvlUp) return this.copy(exp = newExp, lvl = (lvl + 1), maxExp = newMaxExp, mana = maxMana, health = maxHealth)
    this.copy(exp = newExp)
  }

  def lvlUpHealth: Player = this.copy(maxHealth = maxHealth + 10, health = maxHealth + 10)

  def lvlUpMana: Player = this.copy(maxMana = maxMana + 10, mana = maxMana + 10)

  def lvlUpAttack: Player = this.copy(attack = attack + 10)

  def getScore(levelDepth: Int): Int = killCounter + lvl * levelDepth

  override def toString: String =
    "Name: " + name +
      "\nhealth: " + health +
      "\nAttack: " + attack +
      "\nExperience: " + exp +
      "\nposX: " + posX +
      "\nposY: " + posY
}

