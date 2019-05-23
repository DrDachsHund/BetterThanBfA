package de.htwg.se.roguelike.aview

trait State {
  def processInputLine(input:String)
  def handle()
}
