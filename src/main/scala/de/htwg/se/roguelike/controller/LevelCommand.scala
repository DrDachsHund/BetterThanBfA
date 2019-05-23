package de.htwg.se.roguelike.controller

import de.htwg.se.roguelike.model.{Enemy, Level, Player}
import de.htwg.se.roguelike.util.Command

class LevelCommand(lp: (Level,Player), enemies:Vector[Enemy], controller: Controller) extends Command {

  var memento: (Level,Player) = lp
  var mementoE: Vector[Enemy] = enemies

  override def doStep: Unit = {
    //memento = lp
    controller.level = lp._1
    controller.player = lp._2
    controller.interaction
  }

  override def undoStep: Unit = {
    val new_memento = (controller.level,controller.player)
    val new_mementoE = controller.enemies
    controller.level = memento._1
    controller.player = memento._2
    controller.enemies = mementoE

    controller.interaction

    memento = new_memento
    mementoE = new_mementoE
  }

  override def redoStep: Unit = {
    val new_memento = (controller.level,controller.player)
    val new_mementoE = controller.enemies
    controller.level = memento._1
    controller.player = memento._2
    controller.enemies = mementoE

    controller.interaction

    memento = new_memento
    mementoE = new_mementoE
  }

}
