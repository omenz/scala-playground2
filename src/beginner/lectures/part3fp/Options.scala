package beginner.lectures.part3fp

import scala.util.Random

object Options extends App {


  val myFirstOption: Option[Int] = Some(4)
  val noOption: Option[Int] = None

  //unsafe api
  def unsafeMethod(): String = null

  val result = Option(unsafeMethod())
  println(result)

  //chained methods
  def backupMethod(): String = "A valid result"
  val chainedResult = Option(unsafeMethod()).orElse(Option(backupMethod()))


  //DESIGN unsafe api
  def betterUnsafeMethod(): Option[String] = None
  def betterBackupMethod(): Option[String] = Some("A valid result")
  val betterChainedResult = betterBackupMethod() orElse betterBackupMethod()

  //functions on options
  println(myFirstOption.isEmpty)
  println(myFirstOption.get)//do not use this
  //map, flatMap, filter
  println(myFirstOption.map(_ * 2))
  println(myFirstOption.filter(x => x > 10))
  println(myFirstOption.flatMap(x => Option(x * 10)))


  val config: Map[String, String] = Map(
    //fetched from somewhere else
    "host" -> "176.45.36.1",
    "port" -> "80",
  )

  class Connection {
    def connect = "Connected"

  }

  object Connection {
    val random = new Random(System.nanoTime())

    def apply(host: String, port: String): Option[Connection] =
      if (random.nextBoolean()) Some(new Connection)
      else None
  }


  val host = config.get("host")
  val port = config.get("port")
  val connection = host.flatMap(h => port.flatMap(p => Connection(h, p)))
  connection.foreach(c => println(c.connect))


  //chained calls
  config.get("port")
    .flatMap(p => config.get("host")
      .flatMap(h => Connection(p, h))
      .map(c => c.connect))
    .foreach(println)


  //for-comprehensions
  val connectionStatus = for {
    host <- config.get("host")
    port <- config.get("port")
    connection <- Connection(host, port)
  } yield connection.connect

  connectionStatus.foreach(println)
}
