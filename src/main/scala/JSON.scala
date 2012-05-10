import scala.util.parsing.combinator._

class JSON extends JavaTokenParsers {
  def value: Parser[Any] = (
      obj
    | arr
    | stringLiteral
    | floatingPointNumber ^^ (_.toDouble)
    | "null" ^^ (x => null)
    | "true" ^^ (x => true)
    | "false" ^^ (x => false)
  )

  def obj: Parser[Map[String, Any]] = "{"~> repsep(member, ",") <~"}" ^^ (Map() ++ _)

  def arr: Parser[List[Any]] = "["~> repsep(value, ",") <~"]"

  def member: Parser[(String, Any)] = stringLiteral~":"~value ^^
    { case name~":"~value => (name, value) }
}

import java.io.FileReader

object ParseJSON extends JSON with App{
  val reader = new FileReader(args(0))
  println(parseAll(value, reader))
}
