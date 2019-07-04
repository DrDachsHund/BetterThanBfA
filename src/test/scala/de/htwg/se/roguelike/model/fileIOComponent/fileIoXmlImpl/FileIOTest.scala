package de.htwg.se.roguelike.model.fileIOComponent.fileIoXmlImpl

import de.htwg.se.roguelike.model.levelComponent.PlayerInterface
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}

class FileIOTest extends WordSpec with Matchers {
  "FileIOTest" should {
    val fileIO = new FileIO()
    var player: PlayerInterface = new Player("Test", posX = 5, posY = 5, inventory = new Inventory(Vector(Weapon("random")), Vector(Potion("random")), Vector(Armor("Helmet"), Armor("Chest"), Armor("Pants"), Armor("Boots"), Armor("Gloves"))))
    "save and load the Player" in {
      var testX = player.posX
      var testY = player.posY
      fileIO.saveXML(player)
      fileIO.save(player)
      player = player.nextPlayer(posX = 20, posY = 20)
      player = fileIO.load
      player.posX should be(testX)
      player.posY should be(testY)
    }
  }
}
