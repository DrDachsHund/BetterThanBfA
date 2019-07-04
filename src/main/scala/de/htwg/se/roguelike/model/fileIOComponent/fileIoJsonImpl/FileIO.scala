package de.htwg.se.roguelike.model.fileIOComponent.fileIoJsonImpl

import com.google.inject.Guice
import de.htwg.se.roguelike.RogueLikeModule
import net.codingwell.scalaguice.InjectorExtensions._
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
        case "Helmet" => armor ++= Helmet(armorName, armorValue, armorUsable, armorArmor, armorType, armorRarity, armorTextureIndex) :: Nil
        case "Chest" => armor ++= Chest(armorName, armorValue, armorUsable, armorArmor, armorType, armorRarity, armorTextureIndex) :: Nil
        case "Pants" => armor ++= Pants(armorName, armorValue, armorUsable, armorArmor, armorType, armorRarity, armorTextureIndex) :: Nil
        case "Boots" => armor ++= Boots(armorName, armorValue, armorUsable, armorArmor, armorType, armorRarity, armorTextureIndex) :: Nil
        case "Gloves" => armor ++= Gloves(armorName, armorValue, armorUsable, armorArmor, armorType, armorRarity, armorTextureIndex) :: Nil
      }
    }

    val HArmorName: String = (json \ "player" \ "HArmorName").toString().replace("\"", "")
    val HArmorValue: Int = (json \ "player" \ "HArmorValue").as[Int]
    val HArmorUsable: Boolean = (json \ "player" \ "HArmorUsable").get.toString().toBoolean
    val HArmorArmor: Int = (json \ "player" \ "HArmorArmor").as[Int]
    val HArmorType: String = (json \ "player" \ "HArmorType").get.toString().replace("\"", "")
    val HArmorRarity: String = (json \ "player" \ "HArmorRarity").get.toString().replace("\"", "")
    val HArmorTextureIndex: Int = (json \ "player" \ "HArmorTextureIndex").as[Int]
    val helmet = Helmet(HArmorName, HArmorValue, HArmorUsable, HArmorArmor,HArmorType, HArmorRarity, HArmorTextureIndex)

    val CrmorName: String = (json \ "player" \ "CrmorName").toString().replace("\"", "")
    val CArmorValue: Int = (json \ "player" \ "CArmorValue").as[Int]
    val CArmorUsable: Boolean = (json \ "player" \ "CArmorUsable").get.toString().toBoolean
    val CArmorArmor: Int = (json \ "player" \ "CArmorArmor").as[Int]
    val CArmorType: String = (json \ "player" \ "CArmorType").get.toString().replace("\"", "")
    val CArmorRarity: String = (json \ "player" \ "CArmorRarity").get.toString().replace("\"", "")
    val CArmorTextureIndex: Int = (json \ "player" \ "CArmorTextureIndex").as[Int]
    val chest = Chest(CrmorName, CArmorValue, CArmorUsable, CArmorArmor, CArmorType, CArmorRarity,CArmorTextureIndex)

    val PArmorName: String = (json \ "player" \ "PArmorName").toString().replace("\"", "")
    val PArmorValue: Int = (json \ "player" \ "PArmorValue").as[Int]
    val PArmorUsable: Boolean = (json \ "player" \ "PArmorUsable").get.toString().toBoolean
    val PArmorArmor: Int = (json \ "player" \ "PArmorArmor").as[Int]
    val PArmorType: String = (json \ "player" \ "PArmorType").get.toString().replace("\"", "")
    val PArmorRarity: String = (json \ "player" \ "PArmorRarity").get.toString().replace("\"", "")
    val PArmorTextureIndex: Int = (json \ "player" \ "PArmorTextureIndex").as[Int]
    val pants = Pants(PArmorName, PArmorValue, PArmorUsable, PArmorArmor,PArmorType, PArmorRarity, PArmorTextureIndex)

    val BArmorName: String = (json \ "player" \ "BArmorName").toString().replace("\"", "")
    val BArmorValue: Int = (json \ "player" \ "BArmorValue").as[Int]
    val BArmorUsable: Boolean = (json \ "player" \ "BArmorUsable").get.toString().toBoolean
    val BArmorArmor: Int = (json \ "player" \ "BArmorArmor").as[Int]
    val BArmorType: String = (json \ "player" \ "BArmorType").get.toString().replace("\"", "")
    val BArmorRarity: String = (json \ "player" \ "PArmorRarity").get.toString().replace("\"", "")
    val BArmorTextureIndex: Int = (json \ "player" \ "BArmorTextureIndex").as[Int]
    val boots = Boots(BArmorName, BArmorValue, BArmorUsable, BArmorArmor,BArmorType, BArmorRarity, BArmorTextureIndex)

    val GArmorName: String = (json \ "player" \ "GArmorName").toString().replace("\"", "")
    val GArmorValue: Int = (json \ "player" \ "GArmorValue").as[Int]
    val GArmorUsable: Boolean = (json \ "player" \ "GArmorUsable").get.toString().toBoolean
    val GArmorArmor: Int = (json \ "player" \ "GArmorArmor").as[Int]
    val GArmorType: String = (json \ "player" \ "GArmorType").get.toString().replace("\"", "")
    val GArmorRarity: String = (json \ "player" \ "GArmorRarity").get.toString().replace("\"", "")
    val GArmorTextureIndex: Int = (json \ "player" \ "GArmorTextureIndex").as[Int]
    val gloves = Gloves(GArmorName, GArmorValue, GArmorUsable, GArmorArmor,GArmorType, GArmorRarity, GArmorTextureIndex)



    val rName: String = (json \ "player" \ "rightHandWeaponDamageWeaponName").as[String].replace("\"", "")
    val rValue: Int = (json \ "player" \ "rightHandWeaponDamageWeaponValue").get.toString().toInt
    val rUsbale: Boolean = (json \ "player" \ "rightHandWeaponDamageWeaponUsable").get.toString().toBoolean
    val rDmg: Int = (json \ "player" \ "rightHandWeaponDamageWeaponDamage").get.toString().toInt
    val rBlock: Int = (json \ "player" \ "rightHandWeaponDamageWeaponBlock").get.toString().toInt
    val rOH: Boolean = (json \ "player" \ "rightHandWeaponDamageWeaponOneHanded").get.toString().toBoolean
    val rRarity: String = (json \ "player" \ "rightHandWeaponDamageWeaponRarity").as[String].replace("\"", "")
    val rLvl: Int = (json \ "player" \ "rightHandWeaponDamageWeaponItemLevel").get.toString().toInt
    val rTID: Int = (json \ "player" \ "rightHandWeaponDamageWeaponTextureIndex").get.toString().toInt
    val rightHand = Sword(rName, rValue, rUsbale, rDmg, rBlock, rOH, rRarity, rLvl, rTID)

    val lName: String = (json \ "player" \ "leftHandWeaponName").as[String].replace("\"", "")
    val lValue: Int = (json \ "player" \ "leftHandWeaponValue").get.toString().toInt
    val lUsbale: Boolean = (json \ "player" \ "leftHandWeaponUsable").get.toString().toBoolean
    val lDmg: Int = (json \ "player" \ "leftHandWeaponDamage").get.toString().toInt
    val lBlock: Int = (json \ "player" \ "leftHandWeaponBlock").get.toString().toInt
    val lOH: Boolean = (json \ "player" \ "leftHandWeaponOneHanded").get.toString().toBoolean
    val lRarity: String = (json \ "player" \ "leftHandWeaponRarity").as[String].replace("\"", "")
    val lLvl: Int = (json \ "player" \ "leftHandWeaponItemLevel").get.toString().toInt
    val lTID: Int = (json \ "player" \ "leftHandWeaponTextureIndex").get.toString().toInt
    val leftHand = Sword(lName, lValue, lUsbale, lDmg, lBlock, lOH, lRarity, lLvl, lTID)


    player = injector.instance[PlayerInterface]


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
      inventory = new Inventory(weapons = weapons, potions = potions, armor = armor),
      rightHand = rightHand,
      leftHand = leftHand,
      helmet = helmet,
      chest = chest,
      pants = pants,
      boots = boots,
      gloves = gloves)

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
        ),


        "rightHandWeaponDamageWeaponName" -> player.rightHand.name,
        "rightHandWeaponDamageWeaponValue" -> player.rightHand.value,
        "rightHandWeaponDamageWeaponUsable" -> player.rightHand.usable,
        "rightHandWeaponDamageWeaponDamage" -> player.rightHand.dmg,
        "rightHandWeaponDamageWeaponBlock" -> player.rightHand.block,
        "rightHandWeaponDamageWeaponOneHanded" -> player.rightHand.oneHanded,
        "rightHandWeaponDamageWeaponRarity" -> player.rightHand.rarity,
        "rightHandWeaponDamageWeaponItemLevel" -> player.rightHand.itemLevel,
        "rightHandWeaponDamageWeaponTextureIndex" -> player.rightHand.textureIndex,


        "leftHandWeaponName" -> player.leftHand.name,
        "leftHandWeaponValue" -> player.leftHand.value,
        "leftHandWeaponUsable" -> player.leftHand.usable,
        "leftHandWeaponDamage" -> player.leftHand.dmg,
        "leftHandWeaponBlock" -> player.leftHand.block,
        "leftHandWeaponOneHanded" -> player.leftHand.oneHanded,
        "leftHandWeaponRarity" -> player.leftHand.rarity,
        "leftHandWeaponItemLevel" -> player.leftHand.itemLevel,
        "leftHandWeaponTextureIndex" -> player.leftHand.textureIndex,


    "HArmorName" -> player.helmet.name,
    "HArmorValue" -> player.helmet.value,
    "HArmorUsable" -> player.helmet.usable,
    "HArmorArmor" -> player.helmet.armor,
    "HArmorType" -> player.helmet.armorType,
    "HArmorRarity" -> player.helmet.rarity,
    "HArmorTextureIndex" -> player.helmet.textureIndex,


    "CrmorName" -> player.chest.name,
    "CArmorValue" -> player.chest.value,
    "CArmorUsable" -> player.chest.usable,
    "CArmorArmor" -> player.chest.armor,
    "CArmorType" -> player.chest.armorType,
    "CArmorRarity" -> player.chest.rarity,
    "CArmorTextureIndex" -> player.chest.textureIndex,


    "PArmorName" -> player.pants.name,
    "PArmorValue" -> player.pants.value,
    "PArmorUsable" -> player.pants.usable,
    "PArmorArmor" -> player.pants.armor,
    "PArmorType" -> player.pants.armorType,
    "PArmorRarity" -> player.pants.rarity,
    "PArmorTextureIndex" -> player.pants.textureIndex,


    "BArmorName" -> player.boots.name,
    "BArmorValue" -> player.boots.value,
    "BArmorUsable" -> player.boots.usable,
    "BArmorArmor" -> player.boots.armor,
    "BArmorType" -> player.boots.armorType,
    "BArmorRarity" -> player.boots.rarity,
    "BArmorTextureIndex" -> player.boots.textureIndex,


    "GArmorName" -> player.gloves.name,
    "GArmorValue" -> player.gloves.value,
    "GArmorUsable" -> player.gloves.usable,
    "GArmorArmor" -> player.gloves.armor,
    "GArmorType" -> player.gloves.armorType,
    "GArmorRarity" -> player.gloves.rarity,
    "GArmorTextureIndex" -> player.gloves.textureIndex


      )
    )
  }
}
