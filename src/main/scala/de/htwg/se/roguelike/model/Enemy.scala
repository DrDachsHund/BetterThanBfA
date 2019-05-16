package de.htwg.se.roguelike.model

  case class Enemy(name: String = "Skeleton", health:Int = 100, attack:Int = 10, exp:Int = 0, posX:Int, posY:Int) {
    
    def isAlive:Boolean = health > 0

    override def toString: String =
      "Name: " + name +
        "\nhealth: " + health +
        "\nAttack: " + attack +
        "\nExperience: " + exp +
        "\nposX: " + posX +
        "\nposY: " + posY



    //FactoryMethod maybe
    //und Template methode vll hier bei attack oder fight ist bestimmt VERY nice
  }
