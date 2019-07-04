package de.htwg.se.roguelike.model.fileIOComponent.fileIoXmlImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import de.htwg.se.roguelike.RogueLikeModule
import de.htwg.se.roguelike.model.fileIOComponent.FileIOInterface
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.Player
import de.htwg.se.roguelike.model.levelComponent.{LevelInterface, PlayerInterface}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.xml.{NodeSeq, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def load: PlayerInterface = {
    var player: PlayerInterface = null
    val file = scala.xml.XML.loadFile("player.xml")
    val injector = Guice.createInjector(new RogueLikeModule)


    val playerPos = (file \\ "player")
    val posX: Int = (playerPos \ "@posX").text.replace("\"", "").toInt
    val posY: Int = (playerPos \ "@posY").text.replace("\"", "").toInt

    player = injector.instance[PlayerInterface]

    player = Player(name = "Player",posX = posX,posY = posY)
    player
  }

  def save(player: PlayerInterface): Unit = saveString(player)

  def saveXML(player: PlayerInterface): Unit = {
    scala.xml.XML.save("player.xml", playerToXml(player))
  }

  def saveString(player: PlayerInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("player.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(playerToXml(player))
    pw.write(xml)
    pw.close
  }
  def playerToXml(player: PlayerInterface) = {
    <player posX={ player.posX.toString}
            posY= {player.posY.toString}>
    </player>
  }


}