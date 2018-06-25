package lectures.part1basics

/**
  * Created by Alexander Krasovsky on 23.06.2018.
  */
object ValuesVariablesTypes extends App {

  //values are like constants and can't be reassigned
  val x: Int = 42
  val aString: String = "pewpew"
  val aBoolean: Boolean = true
  val aShort: Short = 4455
  val aLong: Long = 432423432L
  val aChar: Char = 'd'
  val aFloat: Float = 343434.0f
  val aDouble: Double = 323423.0
  println(x)

  //variables
  var y: Int = 24324
  y = 123 //reassignments introduce side effects
  println(y)

}
