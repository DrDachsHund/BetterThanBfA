package de.htwg.se.roguelike.model.fileIOComponent.fileIoJsonImpl

import com.google.inject.Guice
import de.htwg.se.roguelike.RogueLikeModule
import de.htwg.se.roguelike.model.fileIOComponent.FileIOInterface
import de.htwg.se.roguelike.model.levelComponent.PlayerInterface
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.Player
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}
import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: PlayerInterface = {
    var player: PlayerInterface = null
    val sourceFile = Source.fromFile("./player.json")
    val source : String = sourceFile.getLines.mkString
    val json: JsValue = Json.parse(source)
    val injector = Guice.createInjector(new RogueLikeModule)

    println(sourceFile)

    println(source)

    val name = (json \\ "name").toString()
    val health = (json \ "player" \ "health").get.toString().toInt
    val maxHealth = (json \ "player" \ "maxHealth").get.toString().toInt
    val mana = (json \ "player" \ "mana").get.toString().toInt
    val maxMana = (json \ "player" \ "maxMana").get.toString().toInt
    val attack = (json \ "player" \ "attack").get.toString().toInt
    val lvl = (json \ "player" \ "lvl").get.toString().toInt
    val exp = (json \ "player" \ "exp").get.toString().toInt
    val maxExp = (json \ "player" \ "maxExp").get.toString().toInt
    val posX = (json \ "player" \ "posX").get.toString().toInt
    val posY = (json \ "player" \ "posY").get.toString().toInt
    val gulden = (json \ "player" \ "gulden").get.toString().toInt
    val killCounter = (json \ "player" \ "killCounter").get.toString().toInt
    val direction = (json \ "player" \ "direction").get.toString().toInt

    player = Player(name,health,maxHealth,mana,maxMana,attack,lvl,exp,maxExp,posX,posY,gulden = gulden,killCounter = killCounter,direction = direction)

    player
  }

  override def save(player: PlayerInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("./player.json"))
    pw.write(Json.prettyPrint(playerToJson(player)))
    pw.close()
  }


  def playerToJson(player: PlayerInterface) = {
    Json.obj(
      "player" -> Json.obj(
        "Name" -> JsString(player.name),
        "health" -> JsNumber(player.health),
        "maxHealth" -> JsNumber(player.maxHealth),
        "mana" -> JsNumber(player.mana),
        "maxMana" -> JsNumber(player.maxMana),
        "attack" -> JsNumber(player.attack),
        "lvl" -> JsNumber(player.lvl),
        "exp" -> JsNumber(player.exp),
        "maxExp" -> JsNumber(player.maxExp),
        "posX" -> JsNumber(player.posX),
        "posY" -> JsNumber(player.posY),
        "gulden" -> JsNumber(player.gulden),
        "killCounter" -> JsNumber(player.killCounter),
        "direction" -> JsNumber(player.direction),
        /*"Potions" -> Json.toJson(
          for {
            x <- player.inventory.potions.size
          } yield {
              Json.obj(
                "PotionName" -> player.inventory.potions(x).name,
                "PotionValue" -> player.inventory.potions(x).value,
                "PotionUsable" -> player.inventory.potions(x).usable,
                "PotionPower" -> player.inventory.potions(x).power,
                "PotionRarity" -> player.inventory.potions(x).rarity,
                "PotiontextureIndex" -> player.inventory.potions(x).textureIndex,
                "Potion" -> Json.toJson(player.inventory.potions(x))
            )
          }
        )
*/
      )
    )
  }
}
