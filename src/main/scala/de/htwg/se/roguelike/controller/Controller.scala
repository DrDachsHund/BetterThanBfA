package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.util.UndoManager

import scala.swing.Publisher
import scala.util.Random

class Controller(var level: Level, var player: Player, var enemies: Vector[Enemy] = Vector()) extends Publisher { //with Observer

  val fight = new Fight
  var gameStatus: GameStatus.Value = GameStatus.LEVEL
  private val undoManager = new UndoManager
  var portal = Portal()
  var merchant = Merchant(inventory = new Inventory(Vector(), Vector(), Vector()))
  var lvlDepth = 0

  var SCALE: Int = 3

  //--FIGHT--
  var enemyLoot: Vector[Item] = Vector()
  var currentEnemy: Enemy = Enemy("ControlerFehler")
  //var currentAction: String = "nothing"
  //--FIGHT--

  //--SCALE-for-GUI
  def setSCALE(newSCALE: Int): Unit = {
    SCALE = newSCALE
    publish(new ResolutionEvent)
  }

  def repaint(): Unit = {
    publish(new RepaintEvent)
  }

  //--SCALE-for-GUI

  //-----------LEVEL----------------

  def createRandomLevel(): Unit = {
    val (level1, enemies1) = new LevelCreator(9, 16).createRandom(player, 10)
    level = level1
    enemies = enemies1
    createMerchant()
    createPortal()
    undoManager.doStep(new LevelCommand((level, player), (level, player), enemies, this))
    //notifyObservers()
    publish(new TileChanged)
  }

  def createLevel(): Unit = {
    level = new LevelCreator(9, 16).createLevel(player, enemies)
    undoManager.doStep(new LevelCommand((level, player), (level, player), enemies, this))
    //notifyObservers()
    publish(new LevelSizeChanged(10))
  }

  def createPortal(): Unit = {
    var row: Int = 0
    var col: Int = 0
    do {
      col = Random.nextInt(level.map.sizeX)
      row = Random.nextInt(level.map.sizeY)
    } while (level.map.tile(col, row).isSet)

    level = level.removeElement(col, row, 1)
    portal = portal.copy(posX = row)
    portal = portal.copy(posY = col)
  }

  def createMerchant(): Unit = {
    var row: Int = 0
    var col: Int = 0
    do {
      col = Random.nextInt(level.map.sizeX)
      row = Random.nextInt(level.map.sizeY)
    } while (level.map.tile(col, row).isSet)

    level = level.removeElement(col, row, 4)
    merchant = merchant.copy(posX = row)
    merchant = merchant.copy(posY = col)
  }

  def interaction(): Unit = {
    if (fight.interaction(player, enemies)) {
      for (enemyTest <- enemies) {
        if (player.posX == enemyTest.posX && player.posY == enemyTest.posY) {
          currentEnemy = enemyTest
        }
      }
      gameStatus = GameStatus.FIGHT
      strategy = new StrategyFight
      //setGameStatus(GameStatus.FIGHT) //schreibt sonst 2 mal fight
      //publish(new FightEvent)
    }
    if (player.posX == portal.posX && player.posY == portal.posY) {
      portal = Portal()
      createRandomLevel()
      lvlDepth += 1
      //notifyObservers()
      publish(new TileChanged)
    }
    if (player.posX == merchant.posX && player.posY == merchant.posY) {
      println("DXDXXDXDDXDXDX")
    }
  }

  //-----------LEVEL----------------

  //----------New-Game--------------

  def newGame(): Unit = {
    player = Player(name = "Player", posX = 5, posY = 5)
    createRandomLevel()
    setGameStatus(GameStatus.LEVEL)
    publish(new TileChanged)
  }

  //----------New-Game--------------

  //-----------MOVE----------------

  def moveUp(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveUp(player), enemies, this))
    //notifyObservers()
    publish(new TileChanged)
  }

  def moveDown(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveDown(player), enemies, this))
    //notifyObservers()
    publish(new TileChanged)
  }

  def moveLeft(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveLeft(player), enemies, this))
    //notifyObservers()
    publish(new TileChanged)
  }

  def moveRight(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveRight(player), enemies, this))
    //notifyObservers()
    publish(new TileChanged)
  }

  //-----------MOVE----------------

  //-----------LVLUP----------------

  def lvlUpHealth(): Unit = {
    player = player.lvlUpHealth
    setGameStatus(GameStatus.LOOTENEMY)
  }

  def lvlUpMana(): Unit = {
    player = player.lvlUpMana
    setGameStatus(GameStatus.LOOTENEMY)
  }

  def lvlUpAttack(): Unit = {
    player = player.lvlUpAttack
    setGameStatus(GameStatus.LOOTENEMY)
  }

  //-----------LVLUP----------------

  //-----------LOOTING----------------

  def lootingEnemy(index: Int): Unit = {

    if (enemyLoot.size < 1) setGameStatus(GameStatus.LEVEL)
    else if (index > 0 && index <= enemyLoot.size) {
      val loot = enemyLoot(index - 1)

      loot match {
        case potion: Potion => player = player.copy(inventory = player.inventory.copy(potions = (player.inventory.potions :+ potion)))
        case weapon: Weapon => player = player.copy(inventory = player.inventory.copy(weapons = (player.inventory.weapons :+ weapon)))
        case armor: Armor => player = player.copy(inventory = player.inventory.copy(armor = (player.inventory.armor :+ armor)))
        case _ => "LOOT FEHLER !!!!"
      }

      var usedItem = enemyLoot.filter(_ == loot)
      usedItem = usedItem.drop(1)
      var newLoot = enemyLoot.filterNot(_ == loot)
      newLoot ++= usedItem
      enemyLoot = newLoot

      if (enemyLoot.size == 0) {
        setGameStatus(GameStatus.LEVEL)
        return
      }

    } else println("CONTROLLER INKOREKTER INDEX => " + index)
    //notifyObservers()
    publish(new TileChanged)
  }

  //-----------LOOTING----------------

  //-----------FIGHT----------------

  def attack(): Unit = {
    println("PlayerAttack: " + player.getAttack)
    enemyThinking("attack")
  }

  def block(): Unit = {
    println("PlayerBlock: " + (player.getArmor + player.rightHand.block + player.leftHand.block * 2))
    enemyThinking("block")
  }

  def special(): Unit = {
    if (player.mana >= 25) {
      player = player.copy(mana = player.mana - 25)
      enemyThinking("special")
    } else println("Not enough mana")
  }

  def run(): Unit = {
    val runPlayer = player
    setGameStatus(GameStatus.LEVEL)
    undo()
    player = runPlayer.copy(posX = player.posX, posY = player.posY)
  }

  def enemyThinking(playerAction: String): Unit = {
    val random = Random
    playerAction match {
      case "attack" =>
        fight.shouldBlock(player, currentEnemy) match {
          case "yes" => enemyTurn(playerAction, "block")
          case "maybe" =>
            if (random.nextInt(2) == 0)
              enemyTurn(playerAction, "block")
            else
              enemyTurn(playerAction, "attack")
          case "no" => enemyTurn(playerAction, "attack")
        }
      case "block" =>
        if (currentEnemy.inventory.potions.size > 0)
          random.nextInt(10) + 1 match {
            case 1 => enemyTurn(playerAction, "special")
            case 2 => enemyTurn(playerAction, "heal")
            case 3 => enemyTurn(playerAction, "block")
            case _ => enemyTurn(playerAction, "attack")
          }
        else {
          if (random.nextInt(2) == 0)
            enemyTurn(playerAction, "attack")
          else
            enemyTurn(playerAction, "special")
        }
      case "special" =>
        random.nextInt(10) + 1 match {
          case 1 => enemyTurn(playerAction, "special")
          case x if 2 until 5 contains x => enemyTurn(playerAction, "attack")
          case _ => enemyTurn(playerAction, "block")
        }
    }
  }

  def enemyTurn(playerAction: String, enemyAction: String): Unit = {
    println("Enemy Thinking => " + enemyAction)
    enemies = enemies.filterNot(_ == currentEnemy)

    if (playerAction == "attack") {
      currentEnemy = fight.playerAttack(player, currentEnemy, enemyAction)
    }
    else if (playerAction == "special") currentEnemy = fight.playerSpecial(player, currentEnemy)

    if (currentEnemy.isAlive && enemyAction != "block") {
      enemyAction match {
        case "attack" => player = fight.enemyAttack(player, currentEnemy, playerAction)
        case "heal" => currentEnemy = currentEnemy.copy(health = currentEnemy.health + 25 * currentEnemy.lvl)
        case "special" =>
          if (currentEnemy.mana >= 50) {
            println("Enemy did Special Attack")
            currentEnemy = currentEnemy.copy(mana = currentEnemy.mana - 50)
            player = fight.enemySpecial(player, currentEnemy)
          } else return enemyThinking(playerAction)
      }
    }

    if (!player.isAlive) setGameStatus(GameStatus.GAMEOVER)
    else if (!currentEnemy.isAlive) {
      if (enemies.length == 5) {
        createPortal()
      }
      val oldLvl: Int = player.lvl
      level = level.removeElement(currentEnemy.posY, currentEnemy.posX, 5)
      player = player.lvlUp(currentEnemy.exp)
      player = player.copy(killCounter = player.killCounter + 1)
      enemyLoot = currentEnemy.inventory.weapons //für loot
      enemyLoot = enemyLoot ++ currentEnemy.inventory.potions
      enemyLoot = enemyLoot ++ currentEnemy.inventory.armor

      println(enemyLoot.toString())

      if (oldLvl < player.lvl) setGameStatus(GameStatus.PLAYERLEVELUP)
      else setGameStatus(GameStatus.LOOTENEMY)
    } else {
      enemies = enemies :+ currentEnemy
      setGameStatus(GameStatus.FIGHTSTATUS)
      setGameStatus(GameStatus.FIGHT)
    }

  }

  //-----------FIGHT----------------

  //-----------STRATEGY PATTERN TO STRING----------------

  var strategy: Strategy = new StrategyLevel

  trait Strategy {
    def updateToString: String
  }

  class StrategyLevel extends Strategy {
    override def updateToString: String = level.toString
  }

  class StrategyFight extends Strategy {
    override def updateToString: String = fight.toString + "[i]Inventory\n[r]:Run\n"
  }

  class StrategyFightStatus extends Strategy {
    override def updateToString: String = fightStatus
  }

  def fightStatus: String = {
    var sb = new StringBuilder
    sb ++= ("Player Health: <" + player.health + "/" + player.maxHealth + "> " +
      "Mana: <" + player.mana + "/" + player.maxMana + ">\n")
    sb ++= "Enemy Health: " + currentEnemy.health + " Mana: " + currentEnemy.mana
    sb ++= "\n"
    sb.toString
  }

  class StrategyInventory extends Strategy {
    override def updateToString: String =
      "L or R to remove Weapon\n" +
        player.helmet.name + ": " + player.helmet.armor + "\n" +
        player.chest.name + ": " + player.chest.armor + "\n" +
        player.pants.name + ": " + player.pants.armor + "\n" +
        player.boots.name + ": " + player.boots.armor + "\n" +
        player.gloves.name + ": " + player.gloves.armor + "\n" +
        player.rightHand.name + ": " + player.rightHand.dmg + "\n" +
        player.leftHand.name + ": " + player.leftHand.dmg + "\n" +
        "[1]Potions\n" +
        "[2]Weapons\n" +
        "[3]Armor\n" +
        "[x]Back\n"
  }

  class StrategyPotions extends Strategy {
    override def updateToString: String =
      "Player Health: <" + player.health + "/" + player.maxHealth + ">\n" +
        "Player Mana: <" + player.mana + "/" + player.maxMana + ">\n" +
        player.inventory.potionsToString + "[x]Back\n"
  }

  class StrategyWeapons extends Strategy {
    override def updateToString: String =
      "[Hand(0,1)][WeaponIndex]\n" +
        player.rightHand.toString + "\n" +
        player.leftHand.toString + "\n" +
        player.inventory.weaponsToString + "[x]Back\n"
  }

  class StrategyArmor extends Strategy {
    override def updateToString: String =
      player.helmet.name + ": " + player.helmet.armor + "\n" +
        player.chest.name + ": " + player.chest.armor + "\n" +
        player.pants.name + ": " + player.pants.armor + "\n" +
        player.boots.name + ": " + player.boots.armor + "\n" +
        player.gloves.name + ": " + player.gloves.armor + "\n" +
        player.inventory.armorToString + "[x]Back\n"
  }

  class StrategyGameOver extends Strategy {
    override def updateToString = "GAME OVER" + player.getScore(lvlDepth) + "\n[n]New Game\n[q]Quit\n"
  }

  class StrategyPlayerLevelUp extends Strategy {
    override def updateToString: String = "Level-Up:\n[1]Health\n[2]Mana\n[3]Attack\n"
  }

  class StrategyLootEnemy extends Strategy {
    override def updateToString: String = lootEnemyString()
  }

  private def lootEnemyString(): String = {
    var sb = new StringBuilder
    sb ++= "Looting slain Enemy:\n"
    var index = 1
    for (loot <- enemyLoot) {
      sb ++= ("[" + index + "]" + loot.toString + "\n")
      index += 1
    }
    sb ++= "\n"
    sb.toString
  }

  def setGameStatus(gameStatus: GameStatus.Value): Unit = {
    this.gameStatus = gameStatus
    gameStatus match {
      case GameStatus.LEVEL => strategy = new StrategyLevel
      case GameStatus.FIGHT => strategy = new StrategyFight
      case GameStatus.FIGHTSTATUS => strategy = new StrategyFightStatus
      case GameStatus.GAMEOVER => strategy = new StrategyGameOver
      case GameStatus.INVENTORY => strategy = new StrategyInventory
      case GameStatus.INVENTORYPOTION => strategy = new StrategyPotions
      case GameStatus.INVENTORYWEAPON => strategy = new StrategyWeapons
      case GameStatus.INVENTORYARMOR => strategy = new StrategyArmor
      case GameStatus.PLAYERLEVELUP => strategy = new StrategyPlayerLevelUp
      case GameStatus.LOOTENEMY => strategy = new StrategyLootEnemy
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  //-----------STRATEGY PATTERN TO STRING----------------

  //-----------UNDO MANAGER----------------

  def undo(): Unit = {
    undoManager.undoStep()
    //notifyObservers()
    publish(new TileChanged)
  }

  def redo(): Unit = {
    undoManager.redoStep()
    //notifyObservers()
    publish(new TileChanged)
  }

  //-----------UNDO MANAGER----------------

  //-----------INVENTORY----------------

  def usePotion(index: Int): Unit = {
    if (player.inventory.potions.size < 1) {
      println("Keine Potion Vorhanden!!!")
    } else if (index <= player.inventory.potions.size && index > 0) {
      val potion = player.inventory.getPotion(index)
      player = potion.usePotion(player)
      var usedPotion = player.inventory.potions.filter(_ == potion)
      usedPotion = usedPotion.drop(1)
      var newPotions = player.inventory.potions.filterNot(_ == potion)
      newPotions ++= usedPotion
      player = player.copy(inventory = player.inventory.copy(potions = newPotions))
    } else println("CONTROLLER INKOREKTER INDEX => " + index)
    //notifyObservers()
    publish(new TileChanged)
  }

  def equipArmor(index: Int): Unit = {
    if (player.inventory.armor.size < 1) {
      println("Keine Armor Vorhanden!!!")
    } else if (index <= player.inventory.armor.size && index > 0) {
      val playerArmor = player.inventory.getArmor(index)

      var usedArmor = player.inventory.armor.filter(_ == playerArmor)
      usedArmor = usedArmor.drop(1)

      var oldArmor: Armor = Armor("noHelmet")
      playerArmor.armorType match {
        case "Helmet" => oldArmor = equipHelmet(playerArmor)
        case "Chest" => oldArmor = equipChest(playerArmor)
        case "Pants" => oldArmor = equipPants(playerArmor)
        case "Boots" => oldArmor = equipBoots(playerArmor)
        case "Gloves" => oldArmor = equipGloves(playerArmor)
      }

      var newArmor = player.inventory.armor.filterNot(_ == playerArmor)
      newArmor ++= usedArmor
      if (oldArmor.armorType != "nothing") {
        newArmor ++= oldArmor :: Nil
      }


      player = player.copy(inventory = player.inventory.copy(armor = newArmor))
    } else println("CONTROLLER INKOREKTER INDEX => " + index)
    //notifyObservers()
    publish(new TileChanged)
  }

  private def equipHelmet(newHelmet: Armor): Armor = {
    val oldHelmet = player.helmet
    player = player.copy(helmet = newHelmet)
    oldHelmet
  }

  private def equipChest(newChest: Armor): Armor = {
    val oldHelmet = player.chest
    player = player.copy(chest = newChest)
    oldHelmet
  }

  private def equipPants(newPants: Armor): Armor = {
    val oldHelmet = player.pants
    player = player.copy(pants = newPants)
    oldHelmet
  }

  private def equipBoots(newBoots: Armor): Armor = {
    val oldHelmet = player.boots
    player = player.copy(boots = newBoots)
    oldHelmet
  }

  private def equipGloves(newGloves: Armor): Armor = {
    val oldHelmet = player.gloves
    player = player.copy(gloves = newGloves)
    oldHelmet
  }


  def unEquipHelmet(): Unit = {
    if (player.helmet.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.helmet :: Nil
      val newInventory = player.inventory.copy(armor = newArmor)
      player = player.copy(helmet = Armor("noHelmet"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipChest(): Unit = {
    if (player.chest.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.chest :: Nil
      val newInventory = player.inventory.copy(armor = newArmor)
      player = player.copy(chest = Armor("noChest"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipPants(): Unit = {
    if (player.pants.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.pants :: Nil
      val newInventory = player.inventory.copy(armor = newArmor)
      player = player.copy(pants = Armor("noPants"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipBoots(): Unit = {
    if (player.boots.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.boots :: Nil
      val newInventory = player.inventory.copy(armor = newArmor)
      player = player.copy(boots = Armor("noBoots"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipGloves(): Unit = {
    if (player.gloves.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.gloves :: Nil
      val newInventory = player.inventory.copy(armor = newArmor)
      player = player.copy(gloves = Armor("noGloves"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipRightHand(): Unit = {
    val weapon: Weapon = player.rightHand
    if (weapon.name != "RightFist") {
      var newWeapon = player.inventory.weapons
      newWeapon ++= player.rightHand :: Nil
      val newInventory = player.inventory.copy(weapons = newWeapon)
      player = player.copy(rightHand = Weapon("rightFist"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipLeftHand(): Unit = {
    val weapon: Weapon = player.leftHand
    if (weapon.name != "LeftFist") {
      var newWeapon = player.inventory.weapons
      newWeapon ++= player.leftHand :: Nil
      val newInventory = player.inventory.copy(weapons = newWeapon)
      player = player.copy(leftHand = Weapon("leftFist"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def equipWeapon(hand: Int, index: Int): Unit = {
    if (player.inventory.weapons.size < 1 && hand <= 1 && hand >= 0) {
      println("Keine Weapon Vorhanden!!!")
    } else if (index <= player.inventory.weapons.size && index > 0) {
      val playerWeapon = player.inventory.getWeapon(index)

      var usedWeapon = player.inventory.weapons.filter(_ == playerWeapon)
      usedWeapon = usedWeapon.drop(1)

      var newWeapons = player.inventory.weapons.filterNot(_ == playerWeapon)
      newWeapons ++= usedWeapon

      if (hand == 0) {
        if (player.rightHand.name != "RightFist") {
          newWeapons ++= player.rightHand :: Nil
        }
        player = player.copy(rightHand = playerWeapon, inventory = player.inventory.copy(weapons = newWeapons))
      } else if (hand == 1) {
        if (player.leftHand.name != "LeftFist") {
          newWeapons ++= player.leftHand :: Nil
        }
        player = player.copy(leftHand = playerWeapon, inventory = player.inventory.copy(weapons = newWeapons))
      }
    } else println("CONTROLLER INKOREKTER INDEX ODER HAND => " + index + "  " + hand)
    //notifyObservers()
    publish(new TileChanged)
  }

  //-----------INVENTORY----------------

}
