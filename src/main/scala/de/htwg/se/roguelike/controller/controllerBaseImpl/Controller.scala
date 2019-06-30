package de.htwg.se.roguelike.controller.controllerBaseImpl

import de.htwg.se.roguelike.controller.GameStatus
import de.htwg.se.roguelike.model.levelComponent._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import de.htwg.se.roguelike.util.UndoManager

import scala.swing.Publisher
import scala.util.Random

class Controller(var level: LevelInterface, var player: PlayerInterface, var enemies: Vector[EnemyInterface] = Vector()) extends Publisher { //with Observer

  val fight:FightInterface = new Fight
  var gameStatus: GameStatus.Value = GameStatus.STARTSCREEN
  private val undoManager = new UndoManager
  var portal = Portal()
  var crate = Crate(inventory = Vector(Sword(name = "Starting Weapon",value = 10, usable = false
    ,dmg = 10, block = 10, oneHanded = true,rarity = "Common")))
  var merchant:MerchantInterface = Merchant()
  var lvlDepth = 0
  var bossfight: Boolean = false

  var SCALE: Int = 3

  var inventoryGameStatus: GameStatus.Value = GameStatus.LEVEL

  //--FIGHT--
  var enemyLoot: Vector[Item] = Vector()
  var currentEnemy: EnemyInterface = Enemy("ControlerFehler")
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
    enemies = Vector()
    for (e <- enemies1) {
      enemies ++= e.setScale(player.lvl + lvlDepth).setLoot() :: Nil
    }
    createMerchant()
    createPortal()
    createCrate()
    undoManager.doStep(new LevelCommand((level, player), (level, player), enemies,merchant,crate, this))
    //notifyObservers()
    publish(new TileChanged)
  }

  def createLevel(): Unit = {
    level = new LevelCreator(9, 16).createLevel(player, enemies)
    undoManager.doStep(new LevelCommand((level, player), (level, player), enemies,merchant,crate, this))
    //notifyObservers()
    publish(new LevelSizeChanged(10))
  }

  def createCrate(): Unit = {
    if ((lvlDepth % 3) == 0) {
      var row: Int = 0
      var col: Int = 0
      do {
        col = Random.nextInt(level.map.sizeX)
        row = Random.nextInt(level.map.sizeY)
      } while (level.map.tile(col, row).isSet)


      level = level.removeElement(col, row, 8)

      if (lvlDepth != 0) {
        crate = Crate()
      }

      crate = crate.copy(posX = row, posY = col)
      crate = crate.fillCrate(lvlDepth,player.lvl)
    } else {
      crate = crate.copy(posX = -1,posY = -1)
    }
  }

  def createPortal(): Unit = {
    var row: Int = 0
    var col: Int = 0
    do {
      col = Random.nextInt(level.map.sizeX)
      row = Random.nextInt(level.map.sizeY)
    } while (level.map.tile(col, row).isSet)

    level = level.removeElement(col, row, 1)
    if ((lvlDepth % 11) == 10) {
      portal = portal.copy(posX = row, posY = col,portalType = 1)
    } else portal = portal.copy(posX = row, posY = col,portalType = 0)
  }

  def createMerchant(): Unit = {
    if ((lvlDepth % 5) == 0 && (lvlDepth != 0)) {
      var row: Int = 0
      var col: Int = 0
      do {
        col = Random.nextInt(level.map.sizeX)
        row = Random.nextInt(level.map.sizeY)
      } while (level.map.tile(col, row).isSet)

      level = level.removeElement(col, row, 4)
      merchant = Merchant()
      merchant = merchant.copy(posX = row, posY = col, gulden = merchant.gulden * lvlDepth)
    } else {
      merchant = merchant.copy(posX = -1, posY = -1)
    }
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
    } else if (player.posX == portal.posX && player.posY == portal.posY) {
      portal = Portal()
      lvlDepth += 1
      if (lvlDepth % 11 == 0) {
        bossfight = true
        currentEnemy = Enemy().createRandomBoss(lvlDepth)
        setGameStatus(GameStatus.FIGHT)
      }
      createRandomLevel()
      //notifyObservers()
      publish(new TileChanged)
    } else if (player.posX == merchant.posX && player.posY == merchant.posY) {
      setGameStatus(GameStatus.MERCHANT)
    } else if (player.posX == crate.posX && player.posY == crate.posY) {
      setGameStatus(GameStatus.CRATE)
    }
  }

  //-----------LEVEL----------------

  //----------New-Game--------------

  def newGame(): Unit = {
    player = Player(name = "Player", posX = 5, posY = 5)
    lvlDepth = 0
    createRandomLevel()
    setGameStatus(GameStatus.LEVEL)
    publish(new TileChanged)
  }

  //----------New-Game--------------

  //-----------MOVE----------------

  def moveUp(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveUp(player), enemies,merchant,crate, this))
    player = player.nextPlayer(direction = 1)
    //notifyObservers()
    publish(new TileChanged)
  }

  def moveDown(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveDown(player), enemies,merchant,crate, this))
    player = player.nextPlayer(direction = 0)
    //notifyObservers()
    publish(new TileChanged)
  }

  def moveLeft(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveLeft(player), enemies,merchant,crate, this))
    player = player.nextPlayer(direction = 2)
    //notifyObservers()
    publish(new TileChanged)
  }

  def moveRight(): Unit = {
    undoManager.doStep(new LevelCommand((level, player), level.moveRight(player), enemies,merchant,crate, this))
    player = player.nextPlayer(direction = 3)
    //notifyObservers()
    publish(new TileChanged)
  }

  //-----------MOVE----------------

  //-----------LVLUP----------------

  def lvlUpHealth(): Unit = {
    player = player.lvlUpHealth
    checkLvlUp()
  }

  def lvlUpMana(): Unit = {
    player = player.lvlUpMana
    checkLvlUp()
  }

  def lvlUpAttack(): Unit = {
    player = player.lvlUpAttack
    checkLvlUp()
  }

  def checkLvlUp(): Unit = {
    if (player.lvl < player.lvlUp(0).lvl) {
      player = player.lvlUp(0)
      setGameStatus(GameStatus.PLAYERLEVELUP)
    }
    else {
      setGameStatus(GameStatus.LOOTENEMY)
    }
    println("lvlUP exp:" + player.exp)
    repaint()
  }

  //-----------LVLUP----------------

  //-----------LOOTING----------------

  def lootAll(): Unit = {
    while (enemyLoot.size != 0) {
      val loot = enemyLoot(0)

      loot match {
        case potion: Potion => player = player.nextPlayer(inventory = player.inventory.nextInventory(potions = (player.inventory.potions :+ potion)))
        case weapon: Weapon => player = player.nextPlayer(inventory = player.inventory.nextInventory(weapons = (player.inventory.weapons :+ weapon)))
        case armor: Armor => player = player.nextPlayer(inventory = player.inventory.nextInventory(armor = (player.inventory.armor :+ armor)))
        case _ => "LOOT FEHLER !!!!"
      }

      var usedItem = enemyLoot.filter(_ == loot)
      usedItem = usedItem.drop(1)
      var newLoot = enemyLoot.filterNot(_ == loot)
      newLoot ++= usedItem
      enemyLoot = newLoot

    }
    setGameStatus(GameStatus.LEVEL)
  }

  def lootingEnemy(index: Int): Unit = {

    if (enemyLoot.size < 1) setGameStatus(GameStatus.LEVEL)
    else if (index > 0 && index <= enemyLoot.size) {
      val loot = enemyLoot(index - 1)

      loot match {
        case potion: Potion => player = player.nextPlayer(inventory = player.inventory.nextInventory(potions = (player.inventory.potions :+ potion)))
        case weapon: Weapon => player = player.nextPlayer(inventory = player.inventory.nextInventory(weapons = (player.inventory.weapons :+ weapon)))
        case armor: Armor => player = player.nextPlayer(inventory = player.inventory.nextInventory(armor = (player.inventory.armor :+ armor)))
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

  def lootAllCrate(): Unit = {
    while (crate.inventory.size != 0) {
      lootingCrate(1)
    }
  }

  def lootingCrate(index: Int): Unit = {
    if (crate.inventory.size < 1) setGameStatus(GameStatus.LEVEL)
    else if (index > 0 && index <= crate.inventory.size) {
      val loot = crate.inventory(index - 1)

      loot match {
        case potion: Potion => player = player.nextPlayer(inventory = player.inventory.nextInventory(potions = (player.inventory.potions :+ potion)))
        case weapon: Weapon => player = player.nextPlayer(inventory = player.inventory.nextInventory(weapons = (player.inventory.weapons :+ weapon)))
        case armor: Armor => player = player.nextPlayer(inventory = player.inventory.nextInventory(armor = (player.inventory.armor :+ armor)))
        case _ => "LOOT FEHLER !!!!"
      }

      var usedItem = crate.inventory.filter(_ == loot)
      usedItem = usedItem.drop(1)
      var newLoot = crate.inventory.filterNot(_ == loot)
      newLoot ++= usedItem
      crate = crate.copy(inventory = newLoot)

      if (crate.inventory.size == 0) {
        crate = crate.copy(posX = -1, posY = -1)
        setGameStatus(GameStatus.LEVEL)
        return
      }
    } else println("CONTROLLER INKOREKTER INDEX => " + index)
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
      player = player.nextPlayer(mana = player.mana - 25)
      enemyThinking("special")
    } else println("Not enough mana")
  }

  def run(): Unit = {
    if (!bossfight) {
      playerLastAction = ""
      enemyLastAction = ""
      val runPlayer = player
      setGameStatus(GameStatus.LEVEL)
      undo()
      player = runPlayer.nextPlayer(posX = player.posX, posY = player.posY)
    }
  }

  def enemyThinking(playerAction: String): Unit = {
    val random = Random
    playerAction match {
      case "attack" =>
        fight.shouldBlock(player, currentEnemy) match {
          case "yes" => enemyTurn(playerAction, "block")
          case "maybe" =>
            random.nextInt(10) + 1 match {
              case x if 1 until 4 contains (x) => enemyTurn(playerAction, "block")
              case x if 4 until 6 contains (x) => {
                if (currentEnemy.inventory.potions.size > 0)
                  enemyTurn(playerAction, "heal")
                else enemyTurn(playerAction, "attack")
              }
              case _ => enemyTurn(playerAction, "attack")
            }
          case "no" => enemyTurn(playerAction, "attack")
        }
      case "block" =>
        if (currentEnemy.inventory.potions.size > 0)
          random.nextInt(6) + 1 match {
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

  var enemyLastAction: String = ""
  var playerLastAction: String = ""

  def enemyTurn(playerAction: String, enemyAction: String): Unit = {
    println("Enemy Thinking => " + enemyAction)

    enemies = enemies.filterNot(_ == currentEnemy)

    if (enemyAction == "block")
      enemyLastAction = currentEnemy.name + ": blocked with " + (currentEnemy.getArmor + currentEnemy.rightHand.block + currentEnemy.leftHand.block * 2) + " armor"
    if (playerAction == "block")
      playerLastAction = player.name + ": blocked with " + (player.getArmor + player.rightHand.block + player.leftHand.block * 2) + " armor"

    if (playerAction == "attack") {
      currentEnemy = fight.playerAttack(player, currentEnemy, enemyAction)
      playerLastAction = player.name + ": attacked for " + player.getAttack
    }
    else if (playerAction == "special") {
      currentEnemy = fight.playerSpecial(player, currentEnemy)
      playerLastAction = player.name + ": special attacked with " + player.getAttack + " damage"
    }

    if (currentEnemy.isAlive && enemyAction != "block") {
      enemyAction match {
        case "attack" => {
          player = fight.enemyAttack(player, currentEnemy, playerAction)
          enemyLastAction = currentEnemy.name + ": attacked for " + currentEnemy.getAttack + " damage"
        }
        case "heal" => {
          currentEnemy = currentEnemy.nextEnemy(health = currentEnemy.health + 25 * currentEnemy.lvl, maxHealth = currentEnemy.maxHealth + 25 * currentEnemy.lvl)
          enemyLastAction = currentEnemy.name + ": healed for " + 25 * currentEnemy.lvl + " hp"
        }
        case "special" =>
          if (currentEnemy.mana >= 50) {
            println("Enemy did Special Attack")
            currentEnemy = currentEnemy.nextEnemy(mana = currentEnemy.mana - 50)
            player = fight.enemySpecial(player, currentEnemy)
            enemyLastAction = currentEnemy.name + ": special attacked with " + currentEnemy.getAttack + " damage"
          } else return enemyThinking(playerAction)
      }
    }

    if (!player.isAlive) {
      playerLastAction = ""
      enemyLastAction = ""
      bossfight = false
      setGameStatus(GameStatus.GAMEOVER)
    }
    else if (!currentEnemy.isAlive) {
      if (enemies.length == 5) {
        createPortal()
      }
      val oldLvl: Int = player.lvl
      level = level.removeElement(currentEnemy.posY, currentEnemy.posX, 5)
      player = player.lvlUp(currentEnemy.exp)
      player = player.nextPlayer(killCounter = player.killCounter + 1, gulden = player.gulden + currentEnemy.gulden)
      enemyLoot = currentEnemy.inventory.weapons //für loot
      enemyLoot = enemyLoot ++ currentEnemy.inventory.potions
      enemyLoot = enemyLoot ++ currentEnemy.inventory.armor

      println(enemyLoot.toString())

      playerLastAction = ""
      enemyLastAction = ""
      bossfight = false

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
    override def updateToString: String = fightStatus + currentEnemy.inventory.toString
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

  class StrategyLootCrate extends Strategy {
    override def updateToString: String = lootCrateString()
  }

  class StrategyLootEnemy extends Strategy {
    override def updateToString: String = lootEnemyString()
  }

  class StrategyMerchant extends Strategy {
    override def updateToString: String = merchantString()
  }

  private def merchantString(): String = {
    var sb = new StringBuilder
    sb ++= "Interacting with Merchant:\n"
    var index = 1
    for (loot <- merchant.inventory) {
      sb ++= ("[" + index + "]" + loot.toString + "\n")
      index += 1
    }
    sb ++= "\n"
    sb.toString
  }

  private def lootCrateString(): String = {
    var sb = new StringBuilder
    sb ++= "Opening Crate:\n"
    var index = 1
    for (loot <- crate.inventory) {
      sb ++= ("[" + index + "]" + loot.toString + "\n")
      index += 1
    }
    sb ++= "\n"
    sb.toString
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
      case GameStatus.CRATE => strategy = new StrategyLootCrate
      case GameStatus.MERCHANT => strategy = new StrategyMerchant
      case _ => println("Fehlender GAMESTATUS!!!!!!!!!!!!")
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
      player = player.nextPlayer(inventory = player.inventory.nextInventory(potions = newPotions))
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


      player = player.nextPlayer(inventory = player.inventory.nextInventory(armor = newArmor))
    } else println("CONTROLLER INKOREKTER INDEX => " + index)
    //notifyObservers()
    publish(new TileChanged)
  }

  private def equipHelmet(newHelmet: Armor): Armor = {
    val oldHelmet = player.helmet
    player = player.nextPlayer(helmet = newHelmet)
    oldHelmet
  }

  private def equipChest(newChest: Armor): Armor = {
    val oldHelmet = player.chest
    player = player.nextPlayer(chest = newChest)
    oldHelmet
  }

  private def equipPants(newPants: Armor): Armor = {
    val oldHelmet = player.pants
    player = player.nextPlayer(pants = newPants)
    oldHelmet
  }

  private def equipBoots(newBoots: Armor): Armor = {
    val oldHelmet = player.boots
    player = player.nextPlayer(boots = newBoots)
    oldHelmet
  }

  private def equipGloves(newGloves: Armor): Armor = {
    val oldHelmet = player.gloves
    player = player.nextPlayer(gloves = newGloves)
    oldHelmet
  }


  def unEquipHelmet(): Unit = {
    if (player.helmet.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.helmet :: Nil
      val newInventory = player.inventory.nextInventory(armor = newArmor)
      player = player.nextPlayer(helmet = Armor("noHelmet"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipChest(): Unit = {
    if (player.chest.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.chest :: Nil
      val newInventory = player.inventory.nextInventory(armor = newArmor)
      player = player.nextPlayer(chest = Armor("noChest"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipPants(): Unit = {
    if (player.pants.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.pants :: Nil
      val newInventory = player.inventory.nextInventory(armor = newArmor)
      player = player.nextPlayer(pants = Armor("noPants"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipBoots(): Unit = {
    if (player.boots.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.boots :: Nil
      val newInventory = player.inventory.nextInventory(armor = newArmor)
      player = player.nextPlayer(boots = Armor("noBoots"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipGloves(): Unit = {
    if (player.gloves.armorType != "nothing") {
      var newArmor = player.inventory.armor
      newArmor ++= player.gloves :: Nil
      val newInventory = player.inventory.nextInventory(armor = newArmor)
      player = player.nextPlayer(gloves = Armor("noGloves"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipRightHand(): Unit = {
    val weapon: Weapon = player.rightHand
    if (weapon.name != "RightFist") {
      var newWeapon = player.inventory.weapons
      newWeapon ++= player.rightHand :: Nil
      val newInventory = player.inventory.nextInventory(weapons = newWeapon)
      player = player.nextPlayer(rightHand = Weapon("rightFist"), inventory = newInventory)
    }
    //notifyObservers()
    publish(new TileChanged)
  }

  def unEquipLeftHand(): Unit = {
    val weapon: Weapon = player.leftHand
    if (weapon.name != "LeftFist") {
      var newWeapon = player.inventory.weapons
      newWeapon ++= player.leftHand :: Nil
      val newInventory = player.inventory.nextInventory(weapons = newWeapon)
      player = player.nextPlayer(leftHand = Weapon("leftFist"), inventory = newInventory)
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
        player = player.nextPlayer(rightHand = playerWeapon, inventory = player.inventory.nextInventory(weapons = newWeapons))
      } else if (hand == 1) {
        if (player.leftHand.name != "LeftFist") {
          newWeapons ++= player.leftHand :: Nil
        }
        player = player.nextPlayer(leftHand = playerWeapon, inventory = player.inventory.nextInventory(weapons = newWeapons))
      }
    } else println("CONTROLLER INKOREKTER INDEX ODER HAND => " + index + "  " + hand)
    //notifyObservers()
    publish(new TileChanged)
  }

  def playerSortInventoryPower() : Unit = {
    player = player.nextPlayer(inventory = player.inventory.invSortPower())
    publish(new TileChanged)
  }

  def playerSortInventoryValue() : Unit = {
    player = player.nextPlayer(inventory = player.inventory.invSortValue())
    publish(new TileChanged)
  }

  //-----------INVENTORY----------------

  def inventoryAsOneVector(): Vector[Item] = {
    var items: Vector[Item] = player.inventory.weapons
    items ++= player.inventory.armor
    items ++= player.inventory.potions
    items
  }

  def sellItem(index: Int): Unit = {
    val items = inventoryAsOneVector()

    if (items.size < 1) {
      println("Keine Items Vorhanden!!!")
    } else if (index <= items.size && index >= 0) {
      val itemToSell = items(index)

      if (merchant.gulden < itemToSell.value) {
        return
      }

      var usedItem = items.filter(_ == itemToSell)
      usedItem = usedItem.drop(1)
      var newItems = items.filterNot(_ == itemToSell)
      newItems ++= usedItem

      var newWeapons: Vector[Weapon] = Vector()
      var newArmor: Vector[Armor] = Vector()
      var newPotion: Vector[Potion] = Vector()

      for (x <- newItems) {
        x match {
          case w: Weapon => newWeapons ++= w :: Nil
          case a: Armor => newArmor ++= a :: Nil
          case p: Potion => newPotion ++= p :: Nil
        }
      }

      var merchantNewInventory = merchant.inventory
      merchantNewInventory ++= itemToSell :: Nil
      merchant = merchant.copy(inventory = merchantNewInventory, gulden = merchant.gulden - itemToSell.value)
      player = player.nextPlayer(inventory = player.inventory.nextInventory(weapons = newWeapons, armor = newArmor, potions = newPotion), gulden = player.gulden + itemToSell.value)
    } else println("CONTROLLER INKOREKTER INDEX => " + index)
    //notifyObservers()
    merchant = merchant.copy(inventory = merchant.sortItems(merchant.inventory))
    publish(new TileChanged)
  }

  def buyItem(index: Int): Unit = {
    val items = merchant.inventory

    if (items.size < 1) {
      println("Keine Items Vorhanden!!!")
    } else if (index <= items.size && index >= 0) {
      val itemToBuy = items(index)
      if (itemToBuy.value > player.gulden) {
        return
      }


      var usedItem = items.filter(_ == itemToBuy)
      usedItem = usedItem.drop(1)
      var newItems = items.filterNot(_ == itemToBuy)
      newItems ++= usedItem

      var newWeapons: Vector[Weapon] = player.inventory.weapons
      var newArmor: Vector[Armor] = player.inventory.armor
      var newPotion: Vector[Potion] = player.inventory.potions

      itemToBuy match {
        case w: Weapon => newWeapons ++= w :: Nil
        case a: Armor => newArmor ++= a :: Nil
        case p: Potion => newPotion ++= p :: Nil
      }

      merchant = merchant.copy(inventory = newItems, gulden = merchant.gulden + itemToBuy.value)
      player = player.nextPlayer(inventory = player.inventory.nextInventory(weapons = newWeapons, armor = newArmor, potions = newPotion), gulden = player.gulden - itemToBuy.value)
    } else println("CONTROLLER INKOREKTER INDEX => " + index)
    //notifyObservers()
    publish(new TileChanged)
  }

  def restock(): Boolean = {
    if (player.gulden < 250)
      false
    else {
      player = player.nextPlayer(gulden = player.gulden - 250)
      merchant = merchant.restock(player.lvl)
      publish(new TileChanged)
      true
    }
  }

}
