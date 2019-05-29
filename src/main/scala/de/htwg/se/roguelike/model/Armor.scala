package de.htwg.se.roguelike.model

import scala.io.Source
import scala.util.Random

trait Armor extends Item {
  val name:String
  val value: Int
  val usable: Boolean
  val armor:Int
  val armorType:String
  val rarity: String
  //val rarity boosts wahrscheinlichkeit gute specails und vll wie viele

  //def randomItem

  //var curse zeigs z.b pro schlag 10 hp verlieren 10%attack miss

  //var special = specaial random!!!!
  //
  //def specals1 ... usw
}

//FactoryMethod
//noch schöner machen name = etc ...
object Armor {
  def apply(kind: String):Armor = kind match {
    case "Helmet" => Helmet(name = "Helmet", value = 10,usable = false, armor = 10,rarity = "Common")
    case "Chest" => Chest(name = "Chest", value = 10, usable = false, armor = 10,rarity = "Common")
    case "Pants" => Pants(name = "Pants", value = 10, usable = false, armor = 10,rarity = "Common")
    case "Boots" => Boots(name = "Boots", value =10, usable = false, armor = 10,rarity = "Common")
    case "Gloves" => Gloves(name = "Gloves", value = 10, usable = false, armor = 10,rarity = "Common")
    case "noHelmet" => Helmet(name = "Head", value = 0, usable = false, armor = 0,armorType = "nothing",rarity = "Common")
    case "noChest" => Chest(name = "Body", value = 0, usable = false, armor = 0,armorType = "nothing",rarity = "Common")
    case "noPants" => Pants(name = "Legs", value = 0,usable =  false, armor = 0,armorType = "nothing",rarity = "Common")
    case "noBoots" => Boots(name = "Feet", value = 0,usable =  false, armor = 0,armorType = "nothing",rarity = "Common")
    case "noGloves" => Gloves(name = "Hands", value = 0,usable =  false,armor = 0,armorType = "nothing",rarity = "Common")
    case "random" =>
      val rarity:String = RandomArmor.getRarity()
      val (value,armor) = RandomArmor.getStats(rarity)

      RandomArmor.getArmorType(value,armor,rarity)
  }
}

private object RandomArmor {
  //stats noch anpassen maybe je nach waffenart extra stats also extra def getWeaponStats(weaponType:String)
  def getStats(rarity: String): (Int,Int) = { //(value,dmg,block)
    val random = new Random()
    rarity match {
      case "Common" =>            (random.nextInt(10) + 1, random.nextInt(5) + 1)
      case "Uncommon" =>          (random.nextInt(20) + 10, random.nextInt(20) + 10)
      case "Rare" =>              (random.nextInt(30) + 20, random.nextInt(30) + 20)
      case "Epic" =>              (random.nextInt(40) + 30, random.nextInt(40) + 30)
      case "Legendary" =>         (random.nextInt(50) + 50, random.nextInt(50) + 50)
      case "Golden-Legendary" =>  (random.nextInt(60) + 100,random.nextInt(60) + 60)
      case "Seraph" =>            (random.nextInt(100) + 1000, random.nextInt(100) + 100)
      case "Pearlescent" =>       (random.nextInt(1000) + 10000, random.nextInt(200) + 200)
      case "Unknown" =>           (random.nextInt(9999999) + 9999999, random.nextInt(999) + 999)
      case _ => (0,0)
    }
  }

  def getHelmetName(): String = {
    val fileStream = getClass.getResourceAsStream("Helmet.txt")
    val lines = Source.fromInputStream(fileStream).getLines
    var nameList = Vector("")
    lines.foreach(line => nameList = nameList :+ line)
    val random = new Random()
    val index = random.nextInt(nameList.size)
    nameList(index)
  }

  def getChestName(): String = {
    val fileStream = getClass.getResourceAsStream("Chest.txt")
    val lines = Source.fromInputStream(fileStream).getLines
    var nameList = Vector("")
    lines.foreach(line => nameList = nameList :+ line)
    val random = new Random()
    val index = random.nextInt(nameList.size)
    nameList(index)
  }

  def getPantsName(): String = {
    val fileStream = getClass.getResourceAsStream("Pants.txt")
    val lines = Source.fromInputStream(fileStream).getLines
    var nameList = Vector("")
    lines.foreach(line => nameList = nameList :+ line)
    val random = new Random()
    val index = random.nextInt(nameList.size)
    nameList(index)
  }
  def getBootsName(): String = {
    val fileStream = getClass.getResourceAsStream("Boots.txt")
    val lines = Source.fromInputStream(fileStream).getLines
    var nameList = Vector("")
    lines.foreach(line => nameList = nameList :+ line)
    val random = new Random()
    val index = random.nextInt(nameList.size)
    nameList(index)
  }

  def getGlovesName(): String = {
    val fileStream = getClass.getResourceAsStream("Gloves.txt")
    val lines = Source.fromInputStream(fileStream).getLines
    var nameList = Vector("")
    lines.foreach(line => nameList = nameList :+ line)
    val random = new Random()
    val index = random.nextInt(nameList.size)
    nameList(index)
  }

  //einzeln get name aufrufen
  def getArmorType(value:Int,armor:Int,rarity:String): Armor = {
    val random = Random //geht auch so zu schreiben lul wierd
    val armorType = random.nextInt(5) + 1
    armorType match {
      case 1 => Helmet(name = getHelmetName(), value,usable = false, armor,rarity = rarity)
      case 2 => Chest(name = getChestName(), value, usable = false, armor,rarity = rarity)
      case 3 => Pants(name = getPantsName(), value, usable = false, armor,rarity = rarity)
      case 4 => Boots(name = getBootsName(), value, usable = false, armor,rarity = rarity)
      case 5 => Gloves(name = getGlovesName(), value, usable = false, armor,rarity = rarity)
    }
  }

  def getRarity(): String = {
    val random = new Random()
    val rarity = random.nextInt(100) + 1 //zwischen 0 und zahl-1 => (0-99) + 1
    rarity match {
      case x if 1  until 41 contains x => "Common" //White                            40%
      case x if 41 until 61 contains x => "Uncommon" //Green                          20%
      case x if 61 until 76 contains x => "Rare" //Blue                               15%
      case x if 76 until 86 contains x => "Epic" //Purple                             10%
      case x if 86 until 91 contains x => "Legendary" //Orange                        5%
      case x if 91 until 95 contains x => "Golden-Legendary" //Gold (E-tech Magenta)  4%
      case x if 95 until 98 contains x => "Seraph" //Pink                             3%
      case x if 98 until 100 contains x =>"Pearlescent" //Cyan                        2%
      case x if x == 100 =>               "Unknown" //Rainbow                         1%
      case _ => println(rarity)
        "FEHLER"
    }
  }
}