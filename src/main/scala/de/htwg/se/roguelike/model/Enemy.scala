package de.htwg.se.roguelike.model

  case class Enemy(name: String = "Enemy",
                   health:Int = 100,
                   attack:Int = 10,
                   lvl:Int = 0,
                   exp:Int = 0,
                   posX:Int = 0, posY:Int = 0,
                   inventory:Vector[Item] = Vector(),
                   rightHand:Weapon = new Weapon("RightFist",0,false,5,5),
                   leftHand:Weapon = new Weapon("LeftFist",0,false,5,5)) extends Entity {


    override def getAttack(): Int = super.getAttack() - 2

    override def toString: String =
      "Name: " + name +
        "\nhealth: " + health +
        "\nAttack: " + attack +
        "\nExperience: " + exp +
        "\nposX: " + posX +
        "\nposY: " + posY

  }
