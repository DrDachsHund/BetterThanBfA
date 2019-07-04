package de.htwg.se.roguelike.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.roguelike.model.levelComponent.PlayerInterface
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}


class FileIOTest extends WordSpec with Matchers {

  "FileIOTest" should {
    val fileIO = new FileIO()
    var player:PlayerInterface = new Player("Test",posX = 5,posY = 5,inventory = new Inventory(Vector(Weapon("random")),Vector(Potion("random")),Vector(Armor("Helmet"))))
    "save and load the Player with Helmet" in {
      var testX = player.posX
      var testY = player.posY
      fileIO.save(player)
      player = player.nextPlayer(posX = 20,posY = 20)
      player = fileIO.load
      player.posX should be (testX)
      player.posY should be (testY)
    }


    "save and load the Player with Chest" in {
      var player:PlayerInterface = new Player("Test",posX = 5,posY = 5,inventory = new Inventory(Vector(Weapon("random")),Vector(Potion("random")),Vector(Armor("Chest"))))
      var testX = player.posX
      var testY = player.posY
      fileIO.save(player)
      player = player.nextPlayer(posX = 20,posY = 20)
      player = fileIO.load
      player.posX should be (testX)
      player.posY should be (testY)
    }
    "save and load the Player with Pants" in {
      var player:PlayerInterface = new Player("Test",posX = 5,posY = 5,inventory = new Inventory(Vector(Weapon("random")),Vector(Potion("random")),Vector(Armor("Pants"))))
      var testX = player.posX
      var testY = player.posY
      fileIO.save(player)
      player = player.nextPlayer(posX = 20,posY = 20)
      player = fileIO.load
      player.posX should be (testX)
      player.posY should be (testY)
    }
    "save and load the Player with Boots" in {
      var player:PlayerInterface = new Player("Test",posX = 5,posY = 5,inventory = new Inventory(Vector(Weapon("random")),Vector(Potion("random")),Vector(Armor("Boots"))))
      var testX = player.posX
      var testY = player.posY
      fileIO.save(player)
      player = player.nextPlayer(posX = 20,posY = 20)
      player = fileIO.load
      player.posX should be (testX)
      player.posY should be (testY)
    }
    "save and load the Player with Gloves" in {
      var player:PlayerInterface = new Player("Test",posX = 5,posY = 5,inventory = new Inventory(Vector(Weapon("random")),Vector(Potion("random")),Vector(Armor("Gloves"))))
      var testX = player.posX
      var testY = player.posY
      fileIO.save(player)
      player = player.nextPlayer(posX = 20,posY = 20)
      player = fileIO.load
      player.posX should be (testX)
      player.posY should be (testY)
    }
  }
}
