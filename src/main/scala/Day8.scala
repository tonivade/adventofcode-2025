package day8

import scala.io.Source
import aoc.timed
import scala.math.*

// https://adventofcode.com/2025/day/8
object Day8:
  case class Point3D(x: Int, y: Int, z: Int):
    def distance(other: Point3D): Double =
      sqrt(pow(this.x - other.x, 2) + pow(this.y - other.y, 2) + pow(this.z - other.z, 2))

  def part1(input: String, maxConnections: Int): Int = 
    val points = input.linesIterator
      .map(_.split(",")).map:
        case Array(x, y, z) => Point3D(x.toInt, y.toInt, z.toInt)
      .toArray

    val connections = points.combinations(2)
      .map:
        case Array(p, q) => (p, q, p.distance(q))
      .toArray
      .sortBy(_._3)
      .take(maxConnections)

    var groups = connections.foldLeft(points.map(p => Set(p))):
        case (circuits, (p, q, _)) =>
          var indexP = circuits.indexWhere(_.contains(p))
          var indexQ = circuits.indexWhere(_.contains(q))
          var merge = circuits(indexP) ++ circuits(indexQ)
          val newCircuits = circuits.zipWithIndex.filter:
              case (_, x) => x != indexP && x != indexQ
            .map(_._1)
          newCircuits :+ merge
    
    groups.map(_.size).sorted.reverse.take(3).foldLeft(1)(_ * _)

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day8.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day8.part1(input, 1000)}")
  timed():
    println(s"Part 2: ${Day8.part2(input)}")

