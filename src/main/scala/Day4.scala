package day4

import scala.io.Source
import aoc.timed
import aoc.Position
import aoc.parseMatrix
import scala.annotation.tailrec

// https://adventofcode.com/2025/day/4
object Day4:

  enum Item:
    case Free
    case Paper
  
  import Item.*

  def parse(input: String): Map[Position, Item] =
    parseMatrix(input):
      case '@' => Paper
      case _ => Free
  
  def removable(matrix: Map[Position, Item]): Seq[Position] =
    matrix.filter(_._2 == Paper).keys.filter:
        p => p.adjacent.count(matrix.getOrElse(_, Free) == Paper) < 4
      .toList

  def part1(input: String): Int = 
    val matrix = parse(input)
    removable(matrix).size

  @tailrec
  def step(matrix: Map[Position, Item], acc: Int = 0): Int =
    val toRemove = removable(matrix);
    if (toRemove.isEmpty)
      acc
    else
      val next = matrix.removedAll(toRemove)
      step(next, acc + toRemove.size)

  def part2(input: String): Int = 
    val matrix = parse(input)
    step(matrix)

@main def main: Unit =
  val input = Source.fromFile("input/day4.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day4.part1(input)}")
  timed():
    println(s"Part 2: ${Day4.part2(input)}")

