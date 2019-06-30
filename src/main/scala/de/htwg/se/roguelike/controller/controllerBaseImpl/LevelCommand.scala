package de.htwg.se.roguelike.controller.controllerBaseImpl

import de.htwg.se.roguelike.model.levelComponent._
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl._
import de.htwg.se.roguelike.util.Command

class LevelCommand(lpOld: (LevelInterface, PlayerInterface), lpNew: (LevelInterface, PlayerInterface), enemies: Vector[EnemyInterface], merchant: MerchantInterface, crate: CrateInterface , controller: Controller) extends Command {

  var memento: (LevelInterface, PlayerInterface) = lpOld
  var mementoE: Vector[EnemyInterface] = enemies
  var mementoM: MerchantInterface = merchant
  var mementoC: CrateInterface = crate

  override def doStep(): Unit = {
    memento = lpOld
    mementoE = enemies
    mementoM = merchant
    mementoC = crate
    controller.level = lpNew._1
    controller.player = lpNew._2
    controller.interaction()
  }

  override def undoStep(): Unit = {
    val new_memento = (controller.level, controller.player)
    val new_mementoE = controller.enemies
    val new_mementoM = controller.merchant
    val new_mementoC = controller.crate

    controller.level = memento._1
    controller.player = memento._2
    controller.enemies = mementoE
    controller.merchant = mementoM
    controller.crate = mementoC

    controller.interaction()

    memento = new_memento
    mementoE = new_mementoE
    mementoM = new_mementoM
    mementoC = new_mementoC
  }

  override def redoStep(): Unit = {
    val new_memento = (controller.level, controller.player)
    val new_mementoE = controller.enemies
    val new_mementoM = controller.merchant
    val new_mementoC = controller.crate

    controller.level = memento._1
    controller.player = memento._2
    controller.enemies = mementoE
    controller.merchant = mementoM
    controller.crate = mementoC

    controller.interaction()

    memento = new_memento
    mementoE = new_mementoE
    mementoM = new_mementoM
    mementoC = new_mementoC
  }

}
