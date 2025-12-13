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
    List(Position(minX, minY), Position(minX, maxY), Position(maxX, maxY), Position(maxX, minY))

  def contains(polygon: List[Position], rectangle: List[Position]): Boolean =
    val edges = polygon.zip(polygon.tail :+ polygon.head)
    val bounds = rectangle.zip(rectangle.tail :+ rectangle.head)

    inline def cross(a: Position, b: Position, c: Position): Long =
      (b.x - a.x).toLong * (c.y - a.y) - (b.y - a.y).toLong * (c.x - a.x)

    inline def onSegment(a: Position, b: Position, c: Position): Boolean =
      math.min(a.x, c.x) <= b.x && b.x <= math.max(a.x, c.x) &&
      math.min(a.y, c.y) <= b.y && b.y <= math.max(a.y, c.y)

    inline def isOnEdge(p: Position): Boolean = 
      edges.exists:
        case (a, b) => cross(a, p, b) == 0 && onSegment(a, p, b)

    inline def isInside(p: Position): Boolean =
      edges.foldLeft(false):
        case (inside, (a, b)) =>
          val intersects = (a.y > p.y) != (b.y > p.y) && (cross(a, b, p) < 0) == (b.y > a.y)
          intersects ^ inside

    inline def isInPolygon(p: Position): Boolean = isOnEdge(p) || isInside(p)

    inline def intersectsEdge(p1: Position, q1: Position, p2: Position, q2: Position): Boolean =
      val d1 = cross(p1, q1, p2)
      val d2 = cross(p1, q1, q2)
      val d3 = cross(p2, q2, p1)
      val d4 = cross(p2, q2, q1)
      (d1 > 0 && d2 < 0 || d1 < 0 && d2 > 0) && (d3 > 0 && d4 < 0 || d3 < 0 && d4 > 0)

    def inPolygon: Boolean = rectangle.forall(isInPolygon)
    def intersects: Boolean = 
      bounds.exists:
        case (r1, r2) =>
          edges.exists:
            case (p1, p2) => intersectsEdge(r1, r2, p1, p2)

    inPolygon && !intersects

  def part1(input: String): Long = 
    parsePoints(input)
      .combinations(2)
      .map:
        case List(p, q) => area(p, q)
      .max

  def part2(input: String): Long =
    val points = parsePoints(input)

    points.combinations(2)
      .filter:
        case List(p, q) => 
          contains(points, bounds(p, q))
      .map:
        case List(p, q) => area(p, q)
      .max

@main def main: Unit =
  val input = Source.fromFile("input/day9.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day9.part1(input)}")
  timed():
    println(s"Part 2: ${Day9.part2(input)}")

