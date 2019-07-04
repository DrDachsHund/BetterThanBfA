package de.htwg.se.roguelike.model.fileIOComponent.fileIoJsonImpl

import com.google.inject.Guice
import de.htwg.se.roguelike.RogueLikeModule
import de.htwg.se.roguelike.model.fileIOComponent.FileIOInterface
import de.htwg.se.roguelike.model.levelComponent.{ArmorInterface, PlayerInterface, PotionInterface, WeaponInterface}
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import play.api.libs.json.{JsNumber, JsString, JsValue, Json}

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: PlayerInterface = {
    var player: PlayerInterface = null
    val sourceFile = Source.fromFile("./player.json")
    val source: String = sourceFile.getLines.mkString
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

    val potionSize = (json \ "player" \ "potionSize").get.toString.toInt
    var potions: Vector[PotionInterface] = Vector()
    for (index <- 0 until potionSize) {
      val pName: String = (json \\ "PotionName") (index).toString()
      val pNameTest: String = pName.replace("\"", "")
      println(pNameTest)
      potions ++= Potion(pNameTest) :: Nil
    }

    val weaponSize = (json \ "player" \ "weaponSize").get.toString.toInt
    var weapons: Vector[WeaponInterface] = Vector()
    for (index <- 0 until weaponSize) {
      val wName: String = (json \\ "WeaponName") (index).toString().replace("\"", "")
      val wValue: Int = (json \\ "WeaponValue") (index).as[Int]
      val wUsbale: Boolean = (json \\ "WeaponUsable") (index).as[Boolean]
      val wDmg: Int = (json \\ "WeaponDamage") (index).as[Int]
      val wBlock: Int = (json \\ "WeaponBlock") (index).as[Int]
      val wOH: Boolean = (json \\ "WeaponOneHanded") (index).as[Boolean]
      val wRarity: String = (json \\ "WeaponRarity") (index).toString().replace("\"", "")
      val wLvl: Int = (json \\ "WeaponItemLevel") (index).as[Int]
      val wTID: Int = (json \\ "WeaponTextureIndex") (index).as[Int]
      weapons ++= Sword(wName, wValue, wUsbale, wDmg, wBlock, wOH, wRarity, wLvl, wTID) :: Nil
    }

    val armorSize = (json \ "player" \ "armorSize").get.toString.toInt
    var armor: Vector[ArmorInterface] = Vector()
    for (index <- 0 until weaponSize) {
      val armorType: String = (json \\ "ArmorType") (index).toString().replace("\"", "")

      val armorName: String = (json \\ "ArmorName") (index).toString().replace("\"", "")
      val armorValue: Int = (json \\ "ArmorValue") (index).as[Int]
      val armorUsable: Boolean = (json \\ "ArmorUsable") (index).as[Boolean]
      val armorArmor: Int = (json \\ "ArmorArmor") (index).as[Int]
      val armorRarity: String = (json \\ "ArmorRarity") (index).toString().replace("\"", "")
      val armorTextureIndex: Int = (json \\ "ArmorTextureIndex") (index).as[Int]


      armorType match {
        case "Helmet" => Helmet()
        case "Chest" =>
        case "Pants" =>
        case "Boots" =>
        case "Gloves" =>
      }
    }


    player = Player(name,
      health,
      maxHealth,
      mana,
      maxMana,
      attack,
      lvl,
      exp,
      maxExp,
      posX,
      posY,
      gulden = gulden,
      killCounter = killCounter,
      direction = direction,
      inventory = new Inventory(weapons = weapons, potions = potions, armor = Vector()))

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

        "potionSize" -> player.inventory.potions.size,
        "weaponSize" -> player.inventory.weapons.size,
        "armorSize" -> player.inventory.armor.size,

        "Inventory" -> Json.obj(
          "potions" -> Json.toJson(
            for {
              x <- 0 until player.inventory.potions.size
            } yield {
              Json.obj("PotionName" -> JsString((player.inventory.potions(x).name).toString))
            }
          ),

          "weapons" -> Json.toJson(
            for {
              x <- 0 until player.inventory.weapons.size
            } yield {
              Json.obj(
                "WeaponName" -> player.inventory.weapons(x).name,
                "WeaponValue" -> player.inventory.weapons(x).value,
                "WeaponUsable" -> player.inventory.weapons(x).usable,
                "WeaponDamage" -> player.inventory.weapons(x).dmg,
                "WeaponBlock" -> player.inventory.weapons(x).block,
                "WeaponOneHanded" -> player.inventory.weapons(x).oneHanded,
                "WeaponRarity" -> player.inventory.weapons(x).rarity,
                "WeaponItemLevel" -> player.inventory.weapons(x).itemLevel,
                "WeaponTextureIndex" -> player.inventory.weapons(x).textureIndex
              )
            }
          ),

          "armor" -> Json.toJson(
            for {
              x <- 0 until player.inventory.armor.size
            } yield {
              Json.obj(
                "ArmorName" -> player.inventory.armor(x).name,
                "ArmorValue" -> player.inventory.armor(x).value,
                "ArmorUsable" -> player.inventory.armor(x).usable,
                "ArmorArmor" -> player.inventory.armor(x).armor,
                "ArmorType" -> player.inventory.armor(x).armorType,
                "ArmorRarity" -> player.inventory.armor(x).rarity,
                "ArmorTextureIndex" -> player.inventory.armor(x).textureIndex
              )
            }
          )

        )


      )
    )
  }
}
