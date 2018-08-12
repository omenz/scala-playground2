package beginner.lectures.part1basics

/**
  * Created by Alexander Krasovsky on 23.06.2018.
  */
object Expressions extends App {

  val x = 1 + 2

  println(x)

  println(1 + 2 * 4)
  //operators: - + * % / & | ^ << >> >>>(right shift with zero extension )

  println(1 == x)
  // <> <= >= !=

  println(!(1 == x))
  //! && ||

  var aVariable = 3
  aVariable += 123 //-= *= /= ONLY WORKS WITH A VARIABLE
  println(aVariable)

  //Instructions (DO) vs Expressions (VALUE)

  //IF expression
  val aCondition = true
  val aConditionedValue = if(aCondition) 5 else 3
  println(aConditionedValue)

  //LOOPS
  var i = 0
  while (i < 10) {
    println(i)
    i += 1
  }

  //EVERYTHING in Scala is an Expression!!!
  val aWeirdVal = (aVariable = 3)//Unit === void
  println(aWeirdVal)

  //code blocks
  val aCodeBlock = {
    val y = 3
    val z = 55

    if (z > y) "aaa" else "bbb"
  }

}
