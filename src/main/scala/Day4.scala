package day4

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/4
object Day4:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day4.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day4.part1(input)}")
  timed():
    println(s"Part 2: ${Day4.part2(input)}")

