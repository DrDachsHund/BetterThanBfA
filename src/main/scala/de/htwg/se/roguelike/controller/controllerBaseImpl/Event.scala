package de.htwg.se.roguelike.controller.controllerBaseImpl

import scala.swing.event.Event

class TileChanged extends Event
case class LevelSizeChanged(newSize: Int) extends Event
class FightEvent extends Event
class ResolutionEvent extends Event
class RepaintEvent extends Event
