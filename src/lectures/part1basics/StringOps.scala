package lectures.part1basics

/**
  * Created by Alexander Krasovsky on 25.06.2018.
  */
object StringOps extends App {

  val str: String = "Hello, I am learning Scala"

  println(str.charAt(2))

  val numStr = "44"
  val number = numStr.toInt
  println('a' + number + 'z')
  println('a' + numStr + 'z')
  println('a' +: numStr :+ 'z')


  //Scala-specific: String interpolators

  //S-interpolators
  val age = 12
  val name = "David"
  val greeting = s"Hello, my name is $name and I am $age years old."
  val anotherGreeting = s"Hello, my name is $name and I will be turning ${age + 1} years old."
  println(anotherGreeting)

  //F-interpolators
  val speed = 1.2f
  val myth = f"$name%s can eat $speed%2.2f burgers per minute"
  println(myth)

  //raw-interpolator
  println(raw"This is \n a new line")
  val escaped = "This is \n a new line"
  println(raw"$escaped")
}
