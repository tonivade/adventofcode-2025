package day2

import scala.io.Source

// https://adventofcode.com/2025/day/2
object Day2:

  def isInvalid(number:Long):Boolean =
    val string = number.toString
    val mid = string.size / 2
    (string.size % 2 == 0) && string.substring(0, mid) == string.substring(mid)

  def part1(input: String): Long = 
    input.split(",").map(_.split("-"))
      .map:
        case Array(a, b) => a.toLong to b.toLong
      .flatMap(_.toList)
      .filter(isInvalid)
      .sum

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day2.txt").getLines().mkString("\n")
  println(Day2.part1(input))
  println(Day2.part2(input))

