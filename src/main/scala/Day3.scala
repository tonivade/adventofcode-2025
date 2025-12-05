package day3

import scala.io.Source
import scala.annotation.tailrec
import aoc.timed

// https://adventofcode.com/2025/day/3
object Day3:
  // brute force solution
  def active(bank: Seq[Int]): Int =
    var all = for {
      i <- 0 until bank.length
      j <- (i + 1) until bank.length
    } yield (bank(i), bank(j))
    all.map:
        case (i, j) => (i * 10) + j
      .max

  // monotonic stack solution
  def active2(bank: Seq[Long]): Long = 
    val toKeep = 12
    val maxDrop = bank.length - toKeep

    @tailrec
    def process(remaining: Seq[Long], stack: List[Long] = List.empty, dropped: Int = 0): List[Long] =
      remaining match
        case Nil => stack
        case digit +: rest =>
          stack match
            case head :: tail if dropped < maxDrop && head < digit =>
              process(remaining, tail, dropped + 1)
            case _ =>
              process(rest, digit :: stack, dropped)

    process(bank).reverse.take(toKeep).mkString.toLong

  def part1(input: String): Int = 
    input.split("\n")
      .map(_.map(_.toInt).map(_ - '0'.toInt))
      .map(active)
      .sum
  def part2(input: String): Long = 
    input.split("\n")
      .map(_.map(_.toLong).map(_ - '0'.toLong))
      .map(active2)
      .sum

@main def main: Unit =
  val input = Source.fromFile("input/day3.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day3.part1(input)}")
  timed():
    println(s"Part 2: ${Day3.part2(input)}")

