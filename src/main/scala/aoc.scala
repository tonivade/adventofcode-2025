package aoc

def timed[R](label: String = "")(block: => R): R =
  val start = System.nanoTime()
  val result = block
  val end = System.nanoTime()
  val elapsedMs = (end - start) / 1_000_000.0
  if (label.nonEmpty) 
    println(s"[$label] Elapsed time: $elapsedMs ms")
  else 
    println(s"Elapsed time: $elapsedMs ms")
  result

def peek[R]: (R => R) =
  r => 
    println(r)
    r

case class Position(x: Int, y: Int):
  def right = Position(x + 1, y)
  def left = Position(x - 1, y)
  def up = Position(x, y + 1)
  def down = Position(x, y - 1)
  def rightUp = Position(x + 1, y + 1)
  def rightDown = Position(x + 1, y - 1)
  def leftUp = Position(x - 1, y + 1)
  def leftDown = Position(x - 1, y - 1)
  def adjacent = List(up, down, left, right, rightUp, rightDown, leftUp, leftDown)

def parseMatrix[I](input: String)(converter: Char => I): Map[Position, I] =
  input.linesIterator
    .zipWithIndex.flatMap:
      case (line, y) => line.zipWithIndex.map:
        case (ch, x) => Position(x, y) -> converter(ch)
    .toMap