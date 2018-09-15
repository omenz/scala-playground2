package advanced.lectures.part5typeSystem
import scala.collection.immutable

object Reflection extends App {

  // reflection + macros + quasiquotes => METAPROGRAMMING

  case class Person(name: String) {
    def sayMyName(): Unit = println(s"Hi, my name is $name")
  }

  // 0 - import
  import scala.reflect.runtime.{universe => ru}

  // 1 - instantiate MIRROR
  val m = ru.runtimeMirror(getClass.getClassLoader)
  // 2 - create a class object = "description"
  val clazz = m.staticClass("advanced.lectures.part5typeSystem.Reflection.Person") // creating a class object by name
  // 3 - create a reflected mirror = "can DO things"
  val cm = m.reflectClass(clazz)
  // 4 - get the constructor
  val constructor = clazz.primaryConstructor.asMethod
  // 5 - reflect the constructor
  val constructorMirror = cm.reflectConstructor(constructor)
  // 6 - invoke the constructor
  val instance = constructorMirror.apply("John")

  println(instance)

  // I have an instance
  val p = Person("Mary") // from the wire as serialized object
  // method name computed from somewhere else
  val methodName = "sayMyName"
  // 1 - mirror
  // 2 - reflect the instance
  val reflected = m.reflect(p)
  // 3 - method symbol
  val methodSymbol = ru.typeOf[Person].decl(ru.TermName(methodName)).asMethod
  // 4 - reflect the method
  val method = reflected.reflectMethod(methodSymbol)
  // 5 - invoke
  
  method.apply()

  // type erasure

  // pp(pain point) #1: differentiate types at runtime
  val numbers = List(1,2,3)
  numbers match {
    case listOfStrings: List[String] => println("list of strings")// type erasure
    case listOfNumbers: List[Int] => println("list of numbers")
  }

  // pp #2: limitations on overloads
//  def processList(list: List[Int]): Int = 43
//  def processList(list: List[String]): Int = 45  // identical definition after type erasure

  // TypeTags
  // 0 - import
  import ru._

  // 1 - crating a type tag "manually"
  val ttag = typeTag[Person]
  println(ttag.tpe)

  class MyMap[K, V]

  // 2 - pass type tags as implicit parameters
  def getTypeArguments[T](value: T)(implicit typeTag: TypeTag[T]): immutable.Seq[ru.Type] =
    typeTag.tpe match {
      case TypeRef(_, _, typeArguments) => typeArguments
      case _ => List()
    }

  val myMap = new MyMap[Int, String]
  val typeArgs = getTypeArguments(myMap)//(typeTag: TypeTag[MyMap[Int, String]])
  println(typeArgs)

  def isSubType[A, B](implicit ttagA: TypeTag[A], ttagB: TypeTag[B]): Boolean = {
    ttagA.tpe <:< ttagB.tpe
  }

  class Animal
  class Dog extends Animal
  println(isSubType[Dog, Animal])



  // another way to get method symbol
  // 3 - method symbol
  val anotherMethodSymbol = typeTag[Person].tpe.decl(ru.TermName(methodName)).asMethod
  // 4 - reflect the method
  val someMethod = reflected.reflectMethod(anotherMethodSymbol)
  // 5 - invoke
  someMethod.apply()
}
