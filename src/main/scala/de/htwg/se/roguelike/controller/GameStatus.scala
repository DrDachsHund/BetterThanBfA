package de.htwg.se.roguelike.controller

object GameStatus extends Enumeration {
type gameStatus = Value
  val LEVEL, FIGHT, FIGHTSTATUS, GAMEOVER, INVENTORY, INVENTORYPOTION, INVENTORYWEAPON, INVENTORYARMOR = Value
}