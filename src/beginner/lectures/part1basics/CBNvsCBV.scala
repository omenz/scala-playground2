package beginner.lectures.part1basics

/**
  * Created by Alexander Krasovsky on 25.06.2018.
  */
object CBNvsCBV extends App {

  def calledByValue(x: Long): Unit = {
    println("by value: " + x)//x will be the same always
    println("by value: " + x)//x will be the same always
  }

  def calledByName(x: => Long): Unit = {
    println("by name: " + x)//x will be evaluated each time
    println("by name: " + x)//x will be evaluated each time
  }

  calledByValue(System.nanoTime())
  calledByName(System.nanoTime())



  def infinite(): Int = 1 + infinite()
  def printFirst(x: Int, y: => Int) = println(x)

//  printFirst(infinite(), 34)
  printFirst(34, infinite())// call to infinite() is lazily evaluated so you don't get a stackoverflow exception
}
