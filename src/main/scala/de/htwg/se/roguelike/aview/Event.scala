package de.htwg.se.roguelike.aview

trait Event
  case class EventMain() extends Event
  case class EventFight() extends Event


