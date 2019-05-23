package de.htwg.se.roguelike.model

case class Sword(name: String,
                 value: Int,
                 usable: Boolean,
                 dmg: Int,
                 block: Int,
                 oneHanded:Boolean) extends Weapon {}
