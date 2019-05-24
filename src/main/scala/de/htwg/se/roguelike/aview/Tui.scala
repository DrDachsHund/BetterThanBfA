package de.htwg.se.roguelike.aview

import de.htwg.se.roguelike.controller.{Controller, GameStatus}
import de.htwg.se.roguelike.util.Observer

class Tui(controller: Controller) extends Observer {

  /*
  trait State {
    def processInputLine(input:String)
    def handle()
  }
*/
  //undo manager noch fehler zwei mal z um zurück zu kommen beim ersten mal

  controller.add(this)
  //State Pattern
  var state:State = new tuiMain(controller,this)
  var inventoryGameStatus: GameStatus.Value = GameStatus.LEVEL
/*
  class tuiMain extends State {
    def processInputLine(input: String): Unit = {
      input match {
        case "q" =>
        case "r" => controller.createRandomLevel
        case "n" => controller.createLevel
        case "w" => controller.moveUp
        case "a" => controller.moveLeft
        case "s" => controller.moveDown
        case "d" => controller.moveRight
        case "z" => controller.undo
        case "y" => controller.redo
        case "i" => {
          controller.setGameStatus(GameStatus.INVENTORY)
          inventoryGameStatus = GameStatus.LEVEL
        }
        case _ => {
          print("Wrong Input!!!")
        }
      }
      handle()
    }

    override def handle(): Unit = {
      val e = controller.gameStatus
      e match {
        case GameStatus.LEVEL => state = this
        case GameStatus.FIGHT => state = new tuiFight
        case GameStatus.INVENTORY => state = new tuiInventoryMain
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiFight extends State {
    def processInputLine(input: String): Unit = {
      input match {
        case "q" =>
        case "1" => controller.attack
        case "i" => {
          controller.setGameStatus(GameStatus.INVENTORY)
          inventoryGameStatus = GameStatus.FIGHT
        }
        case _ => {
          print("Wrong Input!!!")
        }
      }
      handle()
    }

    override def handle(): Unit = {
      val e = controller.gameStatus
      e match {
        case GameStatus.LEVEL => state = new tuiMain
        case GameStatus.FIGHT => state = this
        case GameStatus.FIGHTSTATUS => state = this
        case GameStatus.INVENTORY => state = new tuiInventoryMain
        case GameStatus.GAMEOVER => println("IS VORBEI MA DUDE")
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiInventoryMain extends State {
    override def processInputLine(input: String): Unit = {
      input match {
        case "1" => controller.setGameStatus(GameStatus.INVENTORYPOTION)
        case "2" => controller.setGameStatus(GameStatus.INVENTORYWEAPON)
        case "3" => controller.setGameStatus(GameStatus.INVENTORYARMOR)

        case "H" => controller.unEquipHelmet()
        case "C" => controller.unEquipChest()
        case "P" => controller.unEquipPants()
        case "B" => controller.unEquipBoots()
        case "G" => controller.unEquipGloves()

        case "R" => controller.unEquipRightHand()
        case "L" => controller.unEquipLeftHand()


        case "x" => controller.setGameStatus(inventoryGameStatus)
        case "q" =>
        case _ => {
          print("Wrong Input!!!")
        }

      }
      handle()
    }

    override def handle(): Unit = {
      val e = controller.gameStatus
      e match {
        case GameStatus.INVENTORY => state = this
        case GameStatus.LEVEL => state = new tuiMain
        case GameStatus.FIGHT => state = new tuiFight
        case GameStatus.INVENTORYPOTION => state = new tuiInventoryPotion
        case GameStatus.INVENTORYARMOR => state = new tuiInventoryArmor
        case GameStatus.INVENTORYWEAPON => state = new tuiInventoryWeapon
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiInventoryPotion extends State {
    override def processInputLine(input: String): Unit = {
      input match {
        case "x" => controller.setGameStatus(GameStatus.INVENTORY)
        case "q" =>
        case _ => {
          input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
            case index :: Nil => controller.usePotion(index)
            case _ =>
          }
        }
      }
      handle()
    }
    override def handle(): Unit = {
      val e = controller.gameStatus
      e match {
        case GameStatus.INVENTORYPOTION => state = this
        case GameStatus.INVENTORY => state = new tuiInventoryMain
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiInventoryArmor extends State {
    override def processInputLine(input: String): Unit = {
      input match {
        case "x" => controller.setGameStatus(GameStatus.INVENTORY)
        case "H" => controller.unEquipHelmet()
        case "C" => controller.unEquipChest()
        case "P" => controller.unEquipPants()
        case "B" => controller.unEquipBoots()
        case "G" => controller.unEquipGloves()
        case "q" =>
        case _ => {
          input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
            case index :: Nil => controller.equipArmor(index)
            case _ =>
          }
        }
      }
      handle()
    }
    override def handle(): Unit = {
      val e = controller.gameStatus
      e match {
        case GameStatus.INVENTORYARMOR => state = this
        case GameStatus.INVENTORY => state = new tuiInventoryMain
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }

  class tuiInventoryWeapon extends State {
    override def processInputLine(input: String): Unit = {
      input match {
        case "x" => controller.setGameStatus(GameStatus.INVENTORY)
        case "R" => controller.unEquipRightHand()
        case "L" => controller.unEquipLeftHand()
        case "q" =>
        case _ => {
          input.toList.filter(c => c != ' ').filter(_.isDigit).map(c => c.toString.toInt) match {
            case hand :: index :: Nil => controller.equipWeapon(hand,index)
            case _ =>
          }
        }
      }
      handle()
    }
    override def handle(): Unit = {
      val e = controller.gameStatus
      e match {
        case GameStatus.INVENTORYWEAPON => state = this
        case GameStatus.INVENTORY => state = new tuiInventoryMain
        case _ => {
          print("Wrong GameStatus!!!")
        }
      }
    }
  }
*/

  //GameOver Tui fürs REstarten des Games (Fehler bei Attacken nachdem man Stirbt)

  override def update(): Unit = println(">> \n" + controller.strategy.updateToString + "<<\n")
}