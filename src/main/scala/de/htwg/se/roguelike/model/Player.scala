package de.htwg.se.roguelike.model

case class Player(name: String,
                  health:Int = 100,
                  maxHealth:Int = 100,
                  mana:Int = 100,
                  maxMana:Int = 100,
                  attack:Int = 10,
                  lvl:Int = 1,
                  exp:Int = 0,
                  posX:Int = 0, posY:Int = 0,
                  inventory:Inventory = new Inventory,
                  helmet:Armor = Armor("noHelmet"),
                  chest:Armor = Armor("noChest"),
                  pants:Armor = Armor("noPants"),
                  boots:Armor = Armor("noBoots"),
                  gloves:Armor = Armor("noGloves"),
                  rightHand:Weapon = Weapon("rightFist"),
                  leftHand:Weapon = Weapon("leftFist")
                 //helmet:Armor = new Helmet
                 ) extends Entity {

  def getArmor:Int = {
    var armor:Int = 0
    armor += helmet.armor
    armor += chest.armor
    armor += pants.armor
    armor += boots.armor
    armor += gloves.armor
    armor
  }

   override def toString: String =
      "Name: " + name +
        "\nhealth: " + health +
        "\nAttack: " + attack +
        "\nExperience: " + exp +
        "\nposX: " + posX +
        "\nposY: " + posY
}

