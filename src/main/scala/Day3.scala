package day3

import scala.io.Source

// https://adventofcode.com/2025/day/3
object Day3:
  def active(bank: Seq[Int]): Int =
    var all = for {
      i <- 0 until bank.length
      j <- (i + 1) until bank.length
    } yield (bank(i), bank(j))
    all.map:
        case (i, j) => (i * 10) + j
      .max

  def part1(input: String): Int = 
    input.split("\n")
      .map(_.map(_.toInt).map(_ - '0'.toInt))
      .map(active)
      .sum
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day3.txt").getLines().mkString("\n")
  println(Day3.part1(input))
  println(Day3.part2(input))

