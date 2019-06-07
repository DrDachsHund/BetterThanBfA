package de.htwg.se.roguelike.aview.gui

import de.htwg.se.roguelike.aview.State

import scala.swing.Panel

trait StateGui extends State {
  def drawPanel(Scale:Int): Panel
}
