package day5

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/5
object Day5:
  case class Interval(start: BigInt, end: BigInt):
    def contains(other: BigInt): Boolean =
      other >= start && other <= end

  def part1(input: String): Int = 
    val Array(r, p) = input.split("\n\n")

    val ranges = r.split("\n")
      .map(_.split("-")).map:
        case Array(start, end) => Interval(BigInt(start), BigInt(end))

    val products = p.split("\n").map(BigInt(_))

    products.foreach(println)
    
    products.count(i => ranges.exists(_.contains(i)))

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day5.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day5.part1(input)}")
  timed():
    println(s"Part 2: ${Day5.part2(input)}")

