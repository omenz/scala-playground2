package advanced.lectures.part5typeSystem

object SelfTypes extends App {

  // requiring a type to be mixed in

  trait Instrumentalist {
    def play(): Unit
  }

  // whoever implements Singer to implement Instrumentalist, "self" can be named whatever
  trait Singer { self: Instrumentalist =>

    // rest of the implementation or API
    def sing(): Unit
  }

  class LeadSinger extends Singer with Instrumentalist {
    override def sing(): Unit = ???
    override def play(): Unit = ???
  }

  //not valid
//  class Vocalist extends Singer {
//    override def sing(): Unit = ???
//  }

  val jamesHetfield = new Singer with Instrumentalist {
    override def sing(): Unit = ???
    override def play(): Unit = ???
  }

  class Guitarist extends Instrumentalist {
    override def play(): Unit = println("(guitar solo)")
  }

  val ericClapton = new Guitarist with Singer {
    override def sing(): Unit = ???
  }

  // vs inheritance
  class A
  class B extends A // B IS AN A

  trait T
  trait S { self: T => } // S REQUIRES a T

  // CAKE PATTERN => "dependency injection"

  // classical Dependency Injection
  class Component {
    // API
  }
  class ComponentA extends Component
  class ComponentB extends Component
  class DependentComponent(val component: Component)


  // CAKE PATTERN
  trait ScalaComponent {
    // API
    def action(x: Int): String
  }
  trait ScalaDependentComponent { whatever: ScalaComponent =>
    def dependentAction(x: Int): String = action(x) + " this rocks!"
  }
  trait ScalaApplication { self: ScalaDependentComponent => }

  // layer 1 - small components
  trait Picture extends ScalaComponent
  trait Stats extends ScalaComponent

  // layer 2 - compose
  trait Profile extends ScalaDependentComponent with Picture
  trait Analytics extends ScalaDependentComponent with Stats

  // layer 3 - app
  trait AnalyticsApp extends ScalaApplication with Analytics

  // cyclical dependencies, can't compile
//  class X extends Y
//  class Y extends X

  // workaround
  trait X { self: Y => }
  trait Y { self: X => }

}
