package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model._
import de.htwg.se.roguelike.util.Command

class LevelCommand(lpOld: (Level, Player), lpNew: (Level, Player), enemies: Vector[Enemy],merchant: Merchant,crate: Crate, controller: Controller) extends Command {

  var memento: (Level, Player) = lpOld
  var mementoE: Vector[Enemy] = enemies
  var mementoM: Merchant = merchant
  var mementoC: Crate = crate

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
