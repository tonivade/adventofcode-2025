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

  def bounds(p: Position, q: Position): List[Position] = 
    val minX = math.min(p.x, q.x)
    val maxX = math.max(p.x, q.x)
    val minY = math.min(p.y, q.y)
    val maxY = math.max(p.y, q.y)
    List(Position(minX, minY), Position(minX, maxY), Position(maxX, minY), Position(maxX, maxY))

  def containsPoint(rows: Map[Int, (Int, Int)], columns: Map[Int, (Int, Int)])(p: Position): Boolean =
    val row = rows(p.y)
    val col = columns(p.x)
    row._1 <= p.x && row._2 >= p.x && col._1 <= p.y && col._2 >= p.y
  
  def part1(input: String): Long = 
    parsePoints(input)
      .combinations(2)
      .map:
        case List(p, q) => area(p, q)
      .max

  def drawPerimeter(points: List[Position]): List[Position] =
    (points :+ points.head).sliding(2).foldLeft(List.empty[Position]):
      case (current, List(p, q)) if (p.x == q.x) => 
        val y0 = math.min(p.y, q.y)
        val yN = math.max(p.y, q.y)
        val line = (y0 to yN).map(Position(p.x, _))
        current ++ line
      case (current, List(p, q)) if (p.y == q.y) => 
        val x0 = math.min(p.x, q.x)
        val xN = math.max(p.x, q.x)
        val line = (x0 to xN).map(Position(_, p.y))
        current ++ line

  def part2(input: String): Long =
    val points = parsePoints(input)
    val perimeter = drawPerimeter(points)
    
    val columns = perimeter.groupBy(_.x).map:
      case (x, ys) => x -> (ys.map(_.y).min, ys.map(_.y).max)
    val rows = perimeter.groupBy(_.y).map:
      case (y, xs) => y -> (xs.map(_.x).min, xs.map(_.x).max)

    points.combinations(2)
      .filter:
        case List(p, q) => bounds(p, q).forall(containsPoint(rows, columns))
      .map:
        case List(p, q) => area(p, q)
      .max

@main def main: Unit =
  val input = Source.fromFile("input/day9.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day9.part1(input)}")
  timed():
    println(s"Part 2: ${Day9.part2(input)}")

