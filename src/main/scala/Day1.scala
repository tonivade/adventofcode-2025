package day1

import scala.io.Source

// https://adventofcode.com/2025/day/1
object Day1:
  enum Movement:
    case Left
    case Right

  import Movement.*

  case class Rotation(reminder: Int, times: Int, movement: Movement):
    def move(position:Int): Int =
      movement match
        case Right => (position + reminder) % 100
        case Left => (position + (100 - reminder)) % 100

    def zeros(position:Int): Int = 
      movement match
        case Right if (position + reminder) >= 100 => 1 + times
        case Left if position > 0 && (position - reminder) <= 0 => 1 + times
        case _ => times

  case class State(position: Int, zeros: Int):
    def apply(rotation: Rotation): State =
      State(rotation.move(position), zeros + rotation.zeros(position))

  def parse(input: String): Array[Rotation] =
    input.split("\n")
      .map: line => 
        val movement = line.charAt(0) match
          case 'L' => Left
          case 'R' => Right
        val value = line.substring(1).toInt
        Rotation(value % 100, value / 100, movement)

  def part1(input: String): Int = 
    parse(input)
      .foldLeft(50 :: Nil):
        case (current, rotation) => rotation.move(current.head) :: current
      .count(_ == 0)

  def part2(input: String): Int = 
    parse(input)
      .foldLeft(State(50, 0)):
        case (current, rotation) => current(rotation)
      .zeros

@main def main: Unit =
  val input = Source.fromFile("input/day1.txt").getLines().mkString("\n")
  println(Day1.part1(input))
  println(Day1.part2(input))

