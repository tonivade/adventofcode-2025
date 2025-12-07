package day7

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/7
object Day7:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day7.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day7.part1(input)}")
  timed():
    println(s"Part 2: ${Day7.part2(input)}")

