package de.htwg.se.roguelike

import com.google.inject.AbstractModule
import de.htwg.se.roguelike.controller.ControllerInterface
import de.htwg.se.roguelike.controller.controllerBaseImpl.Controller
import de.htwg.se.roguelike.model.levelComponent.{EnemyInterface, LevelInterface, PlayerInterface}
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Enemy, Level, LevelCreator, Player}
import net.codingwell.scalaguice.ScalaModule

class RogueLikeModule extends AbstractModule with ScalaModule {

  override def configure() = {
    //bind[PlayerInterface].toInstance(new PlayerDI(name = "Player", posX = 5, posY = 5))

    bind[ControllerInterface].to[Controller]
    bind[PlayerInterface].to[Player]
    bind[Player].toInstance(new Player(name = "Player", posX = 5, posY = 5))
    bind[LevelInterface].to[Level]
    bind[Level].toInstance(new Level(9,16))
  }

}