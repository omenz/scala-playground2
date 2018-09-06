package advanced.lectures.part4implicits

import java.util.Date

object TypeClasses extends App {

  trait HTMLWritable {
    def toHtml: String
  }

  case class User(name: String, age: Int, email: String) extends HTMLWritable {
    override def toHtml: String = s"<div>$name ($age yo) <a href=$email/> </div>"
  }

  User("John", 32, "john@rockthejvm.com").toHtml
  /*
    1 - for the types WE write
    2 - ONE implementation out of quite a number
   */

  // option 2 - pattern matching
  object HTMLSerializerPM {
    def serializeToHtml(value: Any) = value match {
      case User(n, a, e) =>
      case _ =>
    }
  }

  /*
     1 - lost type safety
     2 - need to modify code each time new type is added
     3 - still ONE implementation
   */

  trait HTMLSerializer[T] {
    def serialize(value: T): String
  }

  implicit object UserSerializer extends HTMLSerializer[User] {
    def serialize(user: User): String = s"<div>${user.name} (${user.age} yo) <a href=${user.email}/> </div>"
  }

  val john = User("John", 32, "john@rockthejvm.com")
  val anotherJohn = User("John", 45, "anotherJohn@rtvm.com")
  println(UserSerializer.serialize(john))

  // 1 - we can define serializers for other types
  object DateSerializer extends HTMLSerializer[Date] {
    override def serialize(date: Date): String = s"<div>$date</div>"
  }

  // 2 - we can define MULTIPLE serializers
  object PartialUserSerializer extends HTMLSerializer[User] {
    override def serialize(user: User): String = s"<div>${user.name}</div>"
  }


  // part 2
  object HTMLSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLSerializer[T]): HTMLSerializer[T] = serializer
  }

  implicit object IntSerializer extends HTMLSerializer[Int] {
    override def serialize(value: Int): String = s"<div style='color:blue'>$value</div>"
  }

  println(HTMLSerializer.serialize(42)) //implicit serializer parameter was applied
  println(HTMLSerializer.serialize(john))

  // access to the entire class interface thanks to apply method
  println(HTMLSerializer[User].serialize(john))


  // part 3
  implicit class HTMLEnrichment[T](value: T) {
    def toHTML(implicit serializer: HTMLSerializer[T]): String = serializer.serialize(value)
  }
  //COOL !
  /*
    - extend to new types
    - choose implementation
    - super expressive
   */

  println(john.toHTML) // println(new HTMLEnrichment[User](john).toHTML(UserSerializer)
  println(3.toHTML)

  /*
    - type class itself --- HTMLSerializer[T] { .. }
    - type class instances (some of which are implicit) --- UserSerializer, IntSerializer
    - conversion with implicit classes --- HTMLEnrichment
   */

  /*
    Exercise - improve Equal TC with implicit conversion class
    ===(anotherValue: T)
    !==(anotherValue: T)
   */

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  implicit class EqualEnrichment[T](value: T) {
     def ===(anotherValue: T)(implicit equal: Equal[T]): Boolean = equal(value, anotherValue)
     def !==(anotherValue: T)(implicit equal: Equal[T]): Boolean = !equal(value, anotherValue)
   }


  println(john === john)
  println(john === anotherJohn)
  println(john === User("Jack", 23, "jack@rtjvm.com"))
  /*
    john.===(anotherJohn)
    new TypeSafeEqual[User](john).===(anotherJohn)
    new TypeSafeEqual[User](john).===(anotherJohn)(NameEquality)
   */

  // context bounds
  def htmlBoilerplate[T](content: T)(implicit serializer: HTMLSerializer[T]): String =
    s"<html><body>${content.toHTML(serializer)}</body></html>"

  def htmlSugar[T : HTMLSerializer](content: T): String =
    s"<html><body>${content.toHTML}</body></html>"

  //compact method signature and getting an implicit argument too
  def htmlSugar2[T : HTMLSerializer](content: T): String =
    s"<html><body>${content.toHTML(implicitly[HTMLSerializer[T]])}</body></html>"

  // implicitly
  case class Permissions(mask: String)
  implicit val defaultPermissions: Permissions = Permissions("0744")

  // in some other part of the code
  val standardPerms = implicitly[Permissions]
}
