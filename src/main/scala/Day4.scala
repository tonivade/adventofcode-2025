package day4

import scala.io.Source
import aoc.timed
import aoc.Position

// https://adventofcode.com/2025/day/4
object Day4:

  enum Item:
    case Free
    case Paper
  
  import Item.*

  def part1(input: String): Int = 
    val matrix = input.split("\n")
      .zipWithIndex.flatMap:
        case (line, y) => line.zipWithIndex.map:
          case ('@', x) => Position(x, y) -> Paper
          case (_, x) => Position(x, y) -> Free
      .toMap

    matrix.filter(_._2 == Paper).keys.filter:
        p => p.adjacent.count(matrix.getOrElse(_, Free) == Paper) < 4
      .size

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day4.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day4.part1(input)}")
  timed():
    println(s"Part 2: ${Day4.part2(input)}")

