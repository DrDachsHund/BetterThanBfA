package de.htwg.se.roguelike.model

  case class Enemy(name: String = "Skeleton",
                   health:Int = 100,
                   attack:Int = 10,
                   lvl:Int = 0,
                   exp:Int = 100,
                   posX:Int = 0, posY:Int = 0,
                   inventory:Inventory = new Inventory,
                   rightHand:Weapon = Weapon("rightFist"),
                   leftHand:Weapon = Weapon("leftFist"),
                   gulden:Int = 10) extends Entity {


    //Template Method
    override def getAttack: Double = super.getAttack - 2


    //def setLoot():Unit = {}

    override def toString: String =
      "Name: " + name +
        "\nhealth: " + health +
        "\nAttack: " + attack +
        "\nExperience: " + exp +
        "\nposX: " + posX +
        "\nposY: " + posY

  }
