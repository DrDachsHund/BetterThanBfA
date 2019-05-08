trait Event

case class OnEvent() extends Event

case class OffEvent() extends Event

object StateContext1 {
  trait State {
    def handle(e: Event): State
  }
  case class OnState() extends State {
    println("I am On")
    override def handle(e: Event): State = {
      e match {
        case on: OnEvent => OnState()
        case off: OffEvent => OffState()
      }
    }
}
case class OffState() extends State {
  println("I am Off")
  override def handle(e: Event): State = {
    e match {
      case on: OnEvent => OnState()
      case off: OffEvent => OffState()
    }
  }
}
var state: State = OffState()
def handle(e: Event) = state = state.handle(e)
}



object StateContext2 {
  var state = onState
  def handle(e: Event) = {
    e match {
      case on: OnEvent => state = onState
      case off: OffEvent => state = offState
    }
    state
  }

  def onState = println("I am on")
  def offState = println("I am off")
}



StateContext1.handle(new OnEvent)
StateContext1.handle(new OffEvent)


StateContext2.handle(OnEvent())
StateContext2.handle(OffEvent())
