package de.htwg.se.roguelike.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.roguelike.controller.ControllerInterface
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.PlayerInterface
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class FileIOTest extends WordSpec with Matchers {

  "FileIOTest" should {
    val fileIO = new FileIO()
    val controller: ControllerInterface = new Controller(level = new Level(9, 16),
      player = new Player("Test",inventory = new Inventory(Vector(Weapon("random")),Vector(Potion("random")),Vector(Armor("Helmet"),Armor("Chest"),Armor("Pants"),Armor("Boots"),Armor("Gloves")))))
    "save and load the Player" in {
      var testX = controller.player.posX
      var testY = controller.player.posY
      controller.save()
      controller.player = controller.player.nextPlayer(posX = 20)
      controller.player = controller.player.nextPlayer(posY = 20)
      controller.load()
      controller.player.posX should be (testX)
      controller.player.posY should be (testY)
    }
  }
}
