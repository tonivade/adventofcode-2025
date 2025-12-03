package day2

import scala.io.Source

// https://adventofcode.com/2025/day/2
object Day2:

  def parse(input: String): Array[Long] =
    input.split(",").map(_.split("-"))
      .map:
        case Array(a, b) => a.toLong to b.toLong
      .flatMap(_.toList)

  def isInvalid1(number: Long): Boolean =
    val string = number.toString
    val mid = string.size / 2
    (string.size % 2 == 0) && string.substring(0, mid) == string.substring(mid)

  def isInvalid2(number: Long): Boolean =
    val string = number.toString
    val mid = string.size / 2
    (1 to mid).map(i => string.sliding(i, i)).filter(_.distinct.size == 1).nonEmpty

  def part1(input: String): Long = parse(input).filter(isInvalid1).sum

  def part2(input: String): Long = parse(input).filter(isInvalid2).sum

@main def main: Unit =
  val input = Source.fromFile("input/day2.txt").getLines().mkString("\n")
  println(Day2.part1(input))
  println(Day2.part2(input))

