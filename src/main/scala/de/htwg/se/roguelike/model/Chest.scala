package de.htwg.se.roguelike.model

case class Chest(name:String,
                  value: Int,
                  usable: Boolean,
                  armor:Int) extends Armor {}

