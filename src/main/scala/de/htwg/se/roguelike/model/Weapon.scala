package de.htwg.se.roguelike.model

import java.io.InputStream

import scala.io.Source
import scala.util.Random

trait Weapon extends Item {
  val name: String
  val value: Int
  val usable: Boolean //vll als wert um die waffe benutzen zu können maybe??????????
  val dmg: Int
  val block: Int
  val oneHanded: Boolean
  val rarity: String
  val itemLevel: Int

  /*
  def getAttack():(Int,Int,Int) = { //normalDmg,magicDmg,critDmg/trueDmg,etc
  }
  def special():(Int,Int,Int) = {
  }
  */

  def getScaledWeapon(lvl: Int): Weapon //vll nur lvl übergeben um nicht zu viel zu übergeben wen später components gibt bei anderen ethoden auch schauen und eventuel refactorn!!!

  override def toString: String = "(" + rarity + ") " + name + " dmg: " + dmg + " block: " + block + " value: " + value
}

object Weapon {
  def apply(kind: String): Weapon = kind match {
    case "rightFist" => Sword(name = "RightFist", value = 0, usable = false, dmg = 5, block = 5, oneHanded = true, rarity = "", textureIndex = 24)
    case "leftFist" => Sword(name = "LeftFist", value = 0, usable = false, dmg = 5, block = 5, oneHanded = true, rarity = "", textureIndex = 24)
    case "Sword" => Sword(name = "Sword", value = 10, usable = false, dmg = 10, block = 5, oneHanded = true, rarity = "Common")
    case "random" =>
      val name: String = RandomWeapon.getWeaponName()
      val rarity: String = RandomWeapon.getRarity()
      val (value, dmg, block) = RandomWeapon.getStats(rarity)

      //hier match case mit allen waffen arten und dann zufällig ein auswählen
      //Sword(name, value, usable = false, dmg, block, oneHanded = true, rarity)
      RandomWeapon.getWeaponType(name, value, dmg, block, rarity) // actualy besser da dann ewentuel 1-2 händer in weapontype gemacht wird
  }
}

private object RandomWeapon {
  //stats noch anpassen maybe je nach waffenart extra stats also extra def getWeaponStats(weaponType:String)
  def getStats(rarity: String): (Int, Int, Int) = { //(value,dmg,block)
    val random = new Random()
    rarity match {
      case "Common" => (random.nextInt(10) + 1, random.nextInt(5) + 1, random.nextInt(10) + 1)
      case "Uncommon" => (random.nextInt(20) + 10, random.nextInt(20) + 10, random.nextInt(10) + 2)
      case "Rare" => (random.nextInt(30) + 20, random.nextInt(30) + 20, random.nextInt(15) + 3)
      case "Epic" => (random.nextInt(40) + 30, random.nextInt(40) + 30, random.nextInt(20) + 3)
      case "Legendary" => (random.nextInt(50) + 50, random.nextInt(50) + 50, random.nextInt(25) + 5)
      case "Golden-Legendary" => (random.nextInt(60) + 100, random.nextInt(60) + 60, random.nextInt(30) + 15)
      case "Seraph" => (random.nextInt(100) + 1000, random.nextInt(100) + 100, random.nextInt(50) + 25)
      case "Pearlescent" => (random.nextInt(1000) + 10000, random.nextInt(200) + 200, random.nextInt(100) + 50)
      case "Unknown" => (random.nextInt(9999999) + 9999999, random.nextInt(999) + 999, random.nextInt(999) + 999)
    }
  }

  def getWeaponName(): String = {
    val fileStream: Option[InputStream] = Option(getClass.getResourceAsStream("resources/Weapons.txt"))

    fileStream match {
      case None => return "Waffen-Name-Fehler-Beim-Laden"

      case Some(s) => Option(Source.fromInputStream(s).getLines) match {
        case None => return "Waffen-Name-Fehler-Beim-Laden"

        case Some(l) => val lines = l
          var nameList = Vector("")
          lines.foreach(line => nameList = nameList :+ line)
          val random = new Random()
          val index = random.nextInt(nameList.size)
          nameList(index)
      }
    }
  }

  def getWeaponType(name: String, value: Int, dmg: Int, block: Int, rarity: String): Weapon = {
    val random = Random //geht auch so zu schreiben lul wierd
    val weaponType = 1 //random.nextInt(0) + 1 // => ersma 1 weil ja nur Sword derzeit gibt => easy expandable
    weaponType match {
      case 1 => Sword(name, value, usable = false, dmg, block, oneHanded = true, rarity, textureIndex = (Random.nextInt(3) + 1)) //texture index erhöhen wenn mehr texturen dazukommen
      //case 2 => Bow(name = "Sword", value = 0, usable = false, dmg = 0, block = 0, oneHanded = true, rarity = "Common")
      //case 3 => Hammer(name = "Sword", value = 0, usable = false, dmg = 0, block = 0, oneHanded = true, rarity = "Common")
      //etc..
    }
  }

  def getRarity(): String = { //FIXEN !!!!!!!!!!
    val random = new Random()
    val rarity = random.nextInt(100) + 1 //zwischen 0 und zahl-1 => (0-99) + 1
    rarity match {
      case x if 1 until 41 contains x => "Common" //White                            40%
      case x if 41 until 61 contains x => "Uncommon" //Green                         20%
      case x if 61 until 76 contains x => "Rare" //Blue                              15%
      case x if 76 until 86 contains x => "Epic" //Purple                            10%
      case x if 86 until 91 contains x => "Legendary" //Orange                       5%
      case x if 91 until 95 contains x => "Golden-Legendary" //Gold                  4%
      case x if 95 until 98 contains x => "Seraph" //Pink                            3%
      case x if 98 until 100 contains x => "Pearlescent" //Cyan                      2%
      case x if x == 100 => "Unknown" //Rainbow                                      1%
    }
  }
}
