package de.htwg.se.roguelike.model

case class Player(name: String, health:Int = 100, attack:Int = 10, exp:Int = 0, posX:Int = 0, posY:Int = 0) {

   def isAlive():Boolean = health > 0

   override def toString: String =
      "Name: " + name +
        "\nhealth: " + health +
        "\nAttack: " + attack +
        "\nExperience: " + exp +
        "\nposX: " + posX +
        "\nposY: " + posY
}

