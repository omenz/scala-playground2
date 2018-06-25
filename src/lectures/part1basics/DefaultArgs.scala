package lectures.part1basics

/**
  * Created by Alexander Krasovsky on 25.06.2018.
  */
object DefaultArgs extends App {

  def trFact(n: Int, acc: Int = 1): Int = {
    if (n <= 1) acc
    else trFact(n - 1, n * acc)
  }
  val aaa = trFact(10, 1)
  val bbb = trFact(10)

  println(aaa)
  println(bbb)


  def savePicture(format: String = "jpg", width: Int = 800, height: Int = 600): Unit = println("Picture saved!")

  savePicture("bmp")
  savePicture(width = 600)
  savePicture(width = 600, height = 100)
  savePicture("png", 506)
}
