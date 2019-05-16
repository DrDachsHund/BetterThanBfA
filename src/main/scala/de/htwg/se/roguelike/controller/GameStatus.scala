package de.htwg.se.roguelike.controller

object GameStatus extends Enumeration {
type gameStatus = Value
  val LEVEL, FIGHT, INVENTORY = Value
}