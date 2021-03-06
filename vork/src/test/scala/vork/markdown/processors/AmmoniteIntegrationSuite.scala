package vork.markdown.processors

import vork.markdown.repl.Evaluator

class AmmoniteIntegrationSuite extends BaseMarkdownSuite {
  check(
    "code",
    """
      |# Hey Scala!
      |
      |```scala vork
      |val xs = List(1, 2, 3)
      |val ys = xs.map(_ + 1)
      |```
      |
      |```scala vork
      |val zs = ys.map(_ * 2)
      |```
    """.stripMargin,
    """
      |# Hey Scala!
      |
      |```scala
      |@ val xs = List(1, 2, 3)
      |xs: List[Int] = List(1, 2, 3)
      |@ val ys = xs.map(_ + 1)
      |ys: List[Int] = List(2, 3, 4)
      |```
      |
      |```scala
      |@ val zs = ys.map(_ * 2)
      |zs: List[Int] = List(4, 6, 8)
      |```
    """.stripMargin
  )

  check(
    "passthrough",
    """
      |```scala vork:passthrough
      |println("# Header\n\nparagraph\n\n* bullet")
      |```
    """.stripMargin,
    """
      |# Header
      |
      |paragraph
      |
      |* bullet
    """.stripMargin
  )

  check(
    "fail",
    """
      |```scala vork:fail
      |val x: Int = "String"
      |```
    """.stripMargin,
    """
      |```scala
      |@ val x: Int = "String"
      |cmd0.sc:1: type mismatch;
      | found   : String("String")
      | required: Int
      |val x: Int = "String"
      |             ^
      |Compilation Failed
      |```
    """.stripMargin
  )

  checkError[Evaluator.CodeFenceFailure](
    "fail-error",
    """
      |```scala vork
      |foobar
      |```
    """.stripMargin,
    """Vork found evaluation failures.
      |
      |<path>:1:3: unexpected failure
      |>  cmd0.sc:1: not found: value foobar
      |>  val res0 = foobar
      |>             ^
      |>  Compilation Failed
      |""".stripMargin
  )

  checkError[Evaluator.CodeFenceFailure](
    "fail-success",
    """
      |```scala vork:fail
      |1.to(2)
      |```
    """.stripMargin,
    """
      |Vork found evaluation failures.
      |
      |<path>:1:3: unexpected success of
      |```
      |1.to(2)
      |```
      |""".stripMargin
  )

  checkError[Evaluator.CodeFenceFailure](
    "mixed-fail-success-error",
    """
      |```scala vork
      |foobar
      |```
      |
      |```scala vork:fail
      |1.to(2)
      |```
    """.stripMargin,
    """
      |Vork found evaluation failures.
      |
      |<path>:1:3: unexpected failure
      |>  cmd0.sc:1: not found: value foobar
      |>  val res0 = foobar
      |>             ^
      |>  Compilation Failed
      |
      |<path>:5:7: unexpected success of
      |```
      |1.to(2)
      |```
      |""".stripMargin
  )
}
