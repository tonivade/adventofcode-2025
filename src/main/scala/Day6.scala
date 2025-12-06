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

  def parse1(input: String): Iterable[Array[String]] =
    input.split("\n")
      .map(_.trim)
      .map(_.split("\\s+"))
      .flatMap(_.zipWithIndex)
      .groupMap(_._2)(_._1)
      .values

  def parse2(input: String): Iterable[Array[String]] =
    val lines = input.split("\n").toArray
    val widths = (lines.last + " ")
      .split("[\\*\\+]")
      .tail
      .map(_.size + 1)
      .toList
    lines
      .map:
        line => widths.scanLeft(0)(_ + _)
          .sliding(2)
          .map:
            case List(start, end) => line.slice(start, end).take(end - start - 1)
      .flatMap(_.zipWithIndex)
      .groupMap(_._2)(_._1)
      .values

  def transform(column: Array[String]): Array[String] =
    val operation = column.last.trim
    val numbers = column.dropRight(1)
      .flatMap(_.zipWithIndex)
      .groupMap(_._2)(_._1)
      .values
      .map(_.mkString)
      .map(_.trim)
      .toArray
    numbers :+ operation

  def part1(input: String): Long = 
    parse1(input)
      .map(solve)
      .sum

  def part2(input: String): Long = 
    parse2(input)
      .map(transform)
      .map(solve)
      .sum

@main def main: Unit =
  val input = Source.fromFile("input/day6.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day6.part1(input)}")
  timed():
    println(s"Part 2: ${Day6.part2(input)}")

