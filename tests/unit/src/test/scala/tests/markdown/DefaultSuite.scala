package tests.markdown

class DefaultSuite extends BaseMarkdownSuite {

  check(
    "one",
    """
      |```scala mdoc
      |val x = List(1).map(_ + 1)
      |```
    """.stripMargin,
    """|```scala
       |val x = List(1).map(_ + 1)
       |// x: List[Int] = List(2)
       |```
    """.stripMargin
  )

  check(
    "two",
    """
      |# Hey Scala!
      |
      |```scala mdoc
      |val xs = List(1, 2, 3)
      |```
      |
      |```scala mdoc
      |val ys = xs.map(_ * 2)
      |```
    """.stripMargin,
    """|# Hey Scala!
       |
       |```scala
       |val xs = List(1, 2, 3)
       |// xs: List[Int] = List(1, 2, 3)
       |```
       |
       |```scala
       |val ys = xs.map(_ * 2)
       |// ys: List[Int] = List(2, 4, 6)
       |```
    """.stripMargin
  )

  check(
    "res0",
    """
      |```scala mdoc
      |List(1).map(_ + 1)
      |res0.length
      |```
      |
      |```scala mdoc
      |println(1)
      |```
      """.stripMargin,
    """|```scala
       |List(1).map(_ + 1)
       |// res0: List[Int] = List(2)
       |
       |res0.length
       |// res1: Int = 1
       |```
       |
       |```scala
       |println(1)
       |// 1
       |```
      """.stripMargin
  )

  check(
    "defn",
    """
      |```scala mdoc
      |case class User(name: String, age: Int)
      |User("John", 42)
      |```
    """.stripMargin,
    """|```scala
       |case class User(name: String, age: Int)
       |
       |User("John", 42)
       |// res0: User = User("John", 42)
       |```
    """.stripMargin
  )

  check(
    "import",
    """
      |```scala mdoc
      |import scala.concurrent.Future
      |Future.successful(1)
      |```
    """.stripMargin,
    """|```scala
       |import scala.concurrent.Future
       |
       |Future.successful(1)
       |// res0: Future[Int] = Future(Success(1))
       |```
    """.stripMargin
  )

  check(
    "many",
    """
      |```scala mdoc
      |println(1)
      |val x = 42
      |```
      |
      |```scala mdoc
      |println(x)
      |```
    """.stripMargin.replace("'''", "\"\"\""),
    """|```scala
       |println(1)
       |// 1
       |
       |val x = 42
       |// x: Int = 42
       |```
       |
       |```scala
       |println(x)
       |// 42
       |```
    """.stripMargin
  )

}
