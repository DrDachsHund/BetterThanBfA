package de.htwg.se.roguelike.model

case class Weapon(name:String, value: Int, usable: Boolean, dmg:Int, block:Int) extends Item {}
