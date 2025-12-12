package day12

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/12
object Day12:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day12.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day12.part1(input)}")
  timed():
    println(s"Part 2: ${Day12.part2(input)}")

