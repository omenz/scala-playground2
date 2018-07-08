package lectures.part3fp

/**
  * Created by Alexander Krasovsky on 08.07.2018.
  */
object WhatsAFunction extends App {

  // DREAM: use functions as first class elements
  // problem: oop

  val doubler = new MyFucntion[Int, Int] {
    override def apply(element: Int): Int = element * 2
  }

  println(doubler(2))

  // function types = Function[A, B]
  val stringToIntConverter = new Function1[String, Int] {
    override def apply(string: String): Int = string.toInt
  }

  println(stringToIntConverter("3" + 4))

  val adder: (Int, Int) => Int = new Function2[Int, Int, Int] {
    override def apply(a: Int, b: Int): Int = a + b
  }

  //ALL SCALA FUNCTIONS ARE OBJECTS


  val stringConcatFn: (String, String) => String = (a, b) => a + b


  val intFunctionFn: Int => Int => Int = a => b => a + b + 3


  println(intFunctionFn(5)(1))
}


trait MyFucntion[A, B] {
  def apply(element: A): B
}