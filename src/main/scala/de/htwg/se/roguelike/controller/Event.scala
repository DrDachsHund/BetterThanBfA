package de.htwg.se.roguelike.controller

import scala.swing.event.Event

class TileChanged extends Event
case class LevelSizeChanged(newSize: Int) extends Event
class FightEvent extends Event

