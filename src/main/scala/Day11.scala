package day11

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/11
object Day11:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

