package day9

import scala.io.Source
import aoc.timed
import aoc.parsePoints
import aoc.Position

// https://adventofcode.com/2025/day/9
object Day9:
  def area(p: Position, q: Position): Long = 
    val l1 = math.max(p.x, q.x).toLong - math.min(p.x, q.x).toLong
    val l2 = math.max(p.y, q.y).toLong - math.min(p.y, q.y).toLong
    (l1 + 1) * (l2 + 1)

  def part1(input: String): Long = 
    parsePoints(input)
      .combinations(2)
      .map:
        case List(p, q) => area(p, q)
      .max

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day9.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day9.part1(input)}")
  timed():
    println(s"Part 2: ${Day9.part2(input)}")

