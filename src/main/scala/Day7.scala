package day7

import scala.io.Source
import aoc.timed
import aoc.Position
import aoc.parseMatrix
import scala.annotation.tailrec

// https://adventofcode.com/2025/day/7
object Day7:
  enum Item:
    case Free
    case Splitter
    case Beam
    case Touch
  
  import Item.*

  def split(m: Map[Position, Item])(p: Position): List[(Position, Item)] =
    m.get(p.up) match
      case Some(Splitter) => p -> Free :: p.up -> Touch :: p.leftUp -> Beam :: p.rightUp -> Beam :: Nil
      case Some(_) => p -> Free :: p.up -> Beam :: Nil
      case None => p -> Free :: Nil
  
  @tailrec
  def step(matrix: Map[Position, Item]): Map[Position, Item] =
    val beams = matrix.filter:
        case (p, i) => i == Beam
      .keys
      .flatMap(split(matrix))

    if (beams.isEmpty)
      matrix
    else 
      step(matrix ++ beams)

  def parse(input: String): Map[Position, Item] =
    parseMatrix(input):
      case 'S' => Beam
      case '|' => Beam
      case '^' => Splitter
      case _ => Free

  def part1(input: String): Int = 
    val matrix = parse(input)

    val result = step(matrix)

    result.count:
      case (p, i) => i == Touch

  def part2(input: String): Int =
    val matrix = parse(input)

    0

@main def main: Unit =
  val input = Source.fromFile("input/day7.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day7.part1(input)}")
  timed():
    println(s"Part 2: ${Day7.part2(input)}")

