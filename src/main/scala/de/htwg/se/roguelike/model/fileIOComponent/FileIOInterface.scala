package de.htwg.se.roguelike.model.fileIOComponent

import de.htwg.se.roguelike.model.levelComponent.{LevelInterface, PlayerInterface}

trait FileIOInterface {

  def load: PlayerInterface
  def save(player: PlayerInterface): Unit

}