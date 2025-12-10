package day9

import scala.io.Source
import aoc.timed
import aoc.parsePoints
import aoc.Position

// https://adventofcode.com/2025/day/9
object Day9:

  def area(p: Position, q: Position): Long = 
    val l1 = math.abs(p.x - q.x).toLong
    val l2 = math.abs(p.y - q.y).toLong
    (l1 + 1) * (l2 + 1)
  
  def shape(p: Position, q: Position): Set[Position] =
    val points = for {
      x <- math.min(p.x, q.x) to math.max(p.x, q.x)
      y <- math.min(p.y, q.y) to math.max(p.y, q.y)
    } yield Position(x, y)
    points.toSet

  def part1(input: String): Long = 
    parsePoints(input)
      .combinations(2)
      .map:
        case List(p, q) => area(p, q)
      .max

  def part2(input: String): Int =
    val points = parsePoints(input)

    val minX = points.map(_.x).min
    val maxX = points.map(_.x).max
    val minY = points.map(_.y).min
    val maxY = points.map(_.y).max

    val fillY = (for {
        x <- minX to maxX
      } yield (x, points.filter(_.x == x).map(_.y).minOption, points.filter(_.x == x).map(_.y).maxOption))
      .flatMap:
        case (x, Some(y0), Some(yN)) => (y0 to yN).map(Position(x, _))
        case (x, _, _) => Nil

    val allPoints = (for {
        y <- minY to maxY
      } yield (y, fillY.filter(_.y == y).map(_.x).min, fillY.filter(_.y == y).map(_.x).max))
      .flatMap:
        case (y, x0, xN) => (x0 to xN).map(Position(_, y))
      .toSet

    points
      .combinations(2)
      .map:
        case List(p, q) => shape(p, q)
      .filter(_.subsetOf(allPoints))
      .map(_.size)
      .max

@main def main: Unit =
  val input = Source.fromFile("input/day9.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day9.part1(input)}")
  timed():
    println(s"Part 2: ${Day9.part2(input)}")

