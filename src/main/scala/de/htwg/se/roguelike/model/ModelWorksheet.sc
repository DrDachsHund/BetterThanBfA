import de.htwg.se.roguelike.model.{Enemy, Entity, Player}


//nicht player enemy abert trait so das man das selbe bei beiden aufrufen lann
//also zb trait (entity1:Entity, entity2:Entity)
//geht nicht zumindest nicht das ich wüsste da entity nicht copyn lann :(
val entity:Player = new Player("Test")
val entity2:Enemy = new Enemy()


entity2.health = entity2.copy(health = entity2.health - entity.attack)