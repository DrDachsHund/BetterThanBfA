package de.htwg.se.roguelike

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.roguelike.controller.ControllerInterface
import de.htwg.se.roguelike.model.levelComponent.{EnemyInterface, LevelInterface, PlayerInterface}
import de.htwg.se.roguelike.model.levelComponent.levelBaseImpl.{Enemy, Level, LevelCreator, Player}
import net.codingwell.scalaguice.ScalaModule

class RogueLikeModule extends AbstractModule with ScalaModule {

  val defaultSize:Int = 10

  override def configure(): Unit = {
    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bind[LevelInterface].to[Level]
    bind[PlayerInterface].to[Player]
    bind[EnemyInterface].to[Enemy]
    bind[ControllerInterface].to[controller.controllerBaseImpl.Controller]
    bind[LevelInterface].annotatedWithName("random").toInstance(new Level(9,16))
  }

}
