package day5

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/5
object Day5:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day5.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day5.part1(input)}")
  timed():
    println(s"Part 2: ${Day5.part2(input)}")

