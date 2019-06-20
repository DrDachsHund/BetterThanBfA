package de.htwg.se.roguelike.model

import java.io.InputStream

import scala.io.Source
import scala.util.Random

trait Armor extends Item {
  val name: String
  val value: Int
  val usable: Boolean
  val armor: Int
  val armorType: String
  val rarity: String
  //val rarity boosts wahrscheinlichkeit gute specails und vll wie viele

  //def randomItem

  //var curse zeigs z.b pro schlag 10 hp verlieren 10%attack miss

  //var special = specaial random!!!!
  //
  //def specals1 ... usw

  override def toString: String = armorType + ": (" + rarity + ") " + name + " armor: " + armor + " value: " + value
}

//FactoryMethod
//noch schöner machen name = etc ...
object Armor {
  def apply(kind: String): Armor = kind match {
    case "Helmet" => Helmet(name = "Helmet", value = 10, usable = false, armor = 10, rarity = "Common")
    case "Chest" => Chest(name = "Chest", value = 10, usable = false, armor = 10, rarity = "Common")
    case "Pants" => Pants(name = "Pants", value = 10, usable = false, armor = 10, rarity = "Common")
    case "Boots" => Boots(name = "Boots", value = 10, usable = false, armor = 10, rarity = "Common")
    case "Gloves" => Gloves(name = "Gloves", value = 10, usable = false, armor = 10, rarity = "Common")
    case "noHelmet" => Helmet(name = "Head", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common", textureIndex = 24)
    case "noChest" => Chest(name = "Body", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common", textureIndex = 24)
    case "noPants" => Pants(name = "Legs", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common", textureIndex = 24)
    case "noBoots" => Boots(name = "Feet", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common", textureIndex = 24)
    case "noGloves" => Gloves(name = "Hands", value = 0, usable = false, armor = 0, armorType = "nothing", rarity = "Common", textureIndex = 24)
    case "random" =>
      val rarity: String = RandomArmor.getRarity()
      val (value, armor) = RandomArmor.getStats(rarity)

      RandomArmor.getArmorType(value, armor, rarity)
  }
}

private object RandomArmor {
  //stats noch anpassen maybe je nach waffenart extra stats also extra def getWeaponStats(weaponType:String)
  def getStats(rarity: String): (Int, Int) = { //(value,armor)
    val random = new Random()
    rarity match {
      case "Common" => (random.nextInt(10) + 1, random.nextInt(5) + 1)
      case "Uncommon" => (random.nextInt(20) + 10, random.nextInt(20) + 10)
      case "Rare" => (random.nextInt(30) + 20, random.nextInt(30) + 20)
      case "Epic" => (random.nextInt(40) + 30, random.nextInt(40) + 30)
      case "Legendary" => (random.nextInt(50) + 50, random.nextInt(50) + 50)
      case "Golden-Legendary" => (random.nextInt(60) + 100, random.nextInt(60) + 60)
      case "Seraph" => (random.nextInt(100) + 1000, random.nextInt(100) + 100)
      case "Pearlescent" => (random.nextInt(1000) + 10000, random.nextInt(200) + 200)
      case "Unknown" => (random.nextInt(9999999) + 9999999, random.nextInt(999) + 999)
    }
  }

  def getArmorName(fileName: String): String = {
    val fileStream: Option[InputStream] = Option(getClass.getResourceAsStream(fileName))

    fileStream match {
      case None => return "Error-Loading-Armor-Name"

      case Some(s) => Option(Source.fromInputStream(s).getLines) match {
        case None => return "Error-Loading-Armor-Name"

        case Some(l) => val lines = l
          var nameList = Vector("")
          lines.foreach(line => nameList = nameList :+ line)
          val random = new Random()
          val index = random.nextInt(nameList.size)
          nameList(index)
      }
    }
  }

  //einzeln get name aufrufen
  def getArmorType(value: Int, armor: Int, rarity: String): Armor = {
    val random = Random //geht auch so zu schreiben lul wierd
    val armorType = random.nextInt(5) + 1
    armorType match {
      case 1 => Helmet(name = getArmorName("./Helmet.txt"), value, usable = false, armor, rarity = rarity, textureIndex = Random.nextInt(2) + 1) //maybe 1-5 und e nach arrity noch +5 ??!?!??!?!?!
      case 2 => Chest(name = getArmorName("./Chest.txt"), value, usable = false, armor, rarity = rarity, textureIndex = Random.nextInt(1) + 1)
      case 3 => Pants(name = getArmorName("./Pants.txt"), value, usable = false, armor, rarity = rarity, textureIndex = Random.nextInt(1) + 1)
      case 4 => Boots(name = getArmorName("./Boots.txt"), value, usable = false, armor, rarity = rarity, textureIndex = Random.nextInt(1) + 1)
      case 5 => Gloves(name = getArmorName("./Gloves.txt"), value, usable = false, armor, rarity = rarity, textureIndex = Random.nextInt(9) + 1)
    }
  }

  def getRarity(): String = {
    val random = new Random()
    val rarity = random.nextInt(1000) + 1 //zwischen 0 und zahl-1 => (0-99) + 1
    rarity match {
      case x if 1 until 501 contains x => "Common" //White                              50%
      case x if 501 until 751 contains x => "Uncommon" //Green                          25%
      case x if 751 until 851 contains x => "Rare" //Blue                               10%
      case x if 851 until 901 contains x => "Epic" //Purple                             5%
      case x if 901 until 941 contains x => "Legendary" //Orange                        4%
      case x if 941 until 971 contains x => "Golden-Legendary" //Gold (E-tech Magenta)  3%
      case x if 971 until 991 contains x => "Seraph" //Pink                             2%
      case x if 991 until 1000 contains x => "Pearlescent" //Cyan                       0.9%
      case x if x == 1000 => "Unknown" //Rainbow                                        0.1%
    }
    //FÜR ANDRE HIER WAHR FEHLER WEIL UNTIL EXCLUSIVE IST ALSO 999 WAHR NICHT DRIN MUSS 1000 SEIN
  }
}