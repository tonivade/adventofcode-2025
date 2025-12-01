package day1

import scala.io.Source

// https://adventofcode.com/2025/day/1
object Day1:
  enum Movement:
    case Left
    case Right

  import Movement.*

  case class Rotation(value: Int, movement: Movement)

  def part1(input: String): Int = 
    input.split("\n")
      .map: line => 
        val movement = line.charAt(0) match
          case 'L' => Left
          case 'R' => Right
        Rotation(Integer.parseInt(line.substring(1)), movement)
      .foldLeft(50 :: Nil):
        case (current, Rotation(value, Right)) => (current.head + value) % 100 :: current
        case (current, Rotation(value, Left)) if (current.head - value) > 0 => current.head - value :: current
        case (current, Rotation(value, Left)) => (100 + (current.head - value)) % 100 :: current
      .count(_ == 0)

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day1.txt").getLines().mkString("\n")
  println(Day1.part1(input))
  println(Day1.part2(input))

