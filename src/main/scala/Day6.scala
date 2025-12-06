package day6

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/6
object Day6:

  def solve(input: Array[String]): Long =
    input.foldRight(("", 0L)):
        case ("+", (_, acc)) => ("+", 0L)
        case ("*", (_, acc)) => ("*", 1L)
        case (a, ("+", acc)) => ("+", acc + a.toLong)
        case (a, ("*", acc)) => ("*", acc * a.toLong)
      ._2

  def parse(input: String): Iterable[Array[String]] =
    input.split("\n")
      .map(_.trim)
      .map(_.split("\\s+"))
      .flatMap(_.zipWithIndex)
      .groupMap(_._2)(_._1)
      .values

  def part1(input: String): Long = 
    parse(input)
      .map(solve)
      .sum

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day6.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day6.part1(input)}")
  timed():
    println(s"Part 2: ${Day6.part2(input)}")

