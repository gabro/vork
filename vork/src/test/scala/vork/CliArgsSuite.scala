package vork

import java.nio.file.Files
import scala.meta.testkit.DiffAssertions
import org.scalatest.FunSuite

class CliArgsSuite extends FunSuite with DiffAssertions {

  def checkError(args: List[String], expected: String): Unit = {
    test(args.mkString(" ")) {
      Options.fromCliArgs(args).toEither match {
        case Left(obtained) =>
          assertNoDiff(obtained.toString(), expected)
        case Right(ok) =>
          fail(s"Expected error. Obtained $ok")
      }
    }
  }

  checkError(
    "--include-files" :: "*" :: Nil,
    // TODO(olafur) automatically include flag name in metaconfig error message
    """|Dangling meta character '*' near index 0
       |*
       |^
       |""".stripMargin
  )

  private val tmp = Files.createTempDirectory("vork")
  Files.delete(tmp)
  checkError(
    "--in" :: tmp.toString :: Nil,
    s"File $tmp does not exist."
  )

}
