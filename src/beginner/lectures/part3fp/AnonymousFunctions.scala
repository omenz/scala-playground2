package beginner.lectures.part3fp

/**
  * Created by Alexander Krasovsky on 08.07.2018.
  */
object AnonymousFunctions extends App {

  //ANONYMOUS function, lambda
  val doubler = (a: Int) => a * 2

  val doubler2: Int => Int = a => a * 2

  //multiple parameters
  val adder: (Int, Int) => Int = (a: Int, b: Int) => a + b

  //no params
  val noParams: () => Int = () => 3

  println(noParams)
  println(noParams())

  //curly braces with lambdas
  val strToInt = {
    str: String => str.toInt
  }

  //MOAR syntactic sugar
  val niceIncrementer: Int => Int = _ + 1 //equivalent to x => x + 1
  val niceAdder: (Int, Int) => Int = _ + _ //equivalent to (a, b) = a + b

}
