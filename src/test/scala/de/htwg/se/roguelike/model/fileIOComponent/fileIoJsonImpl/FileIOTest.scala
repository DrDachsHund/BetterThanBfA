package de.htwg.se.roguelike.model.fileIOComponent.fileIoJsonImpl

import de.htwg.se.roguelike.controller.ControllerInterface
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.PlayerInterface
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Level, Player}
import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class FileIOTest extends WordSpec with Matchers {

  "FileIOTest" should {
    val fileIO = new FileIO()
    val controller: ControllerInterface = new Controller(level = new Level(9, 16), player = new Player("Test"))
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
