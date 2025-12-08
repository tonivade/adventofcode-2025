package day8

import scala.io.Source
import aoc.timed
import scala.math.*
import scala.annotation.tailrec

// https://adventofcode.com/2025/day/8
object Day8:
  case class Point3D(x: Int, y: Int, z: Int):
    def distance(other: Point3D): Double =
      sqrt(pow(this.x - other.x, 2) + pow(this.y - other.y, 2) + pow(this.z - other.z, 2))

  def parse(input: String): Array[Point3D] =
    input.linesIterator
      .map(_.split(",")).map:
        case Array(x, y, z) => Point3D(x.toInt, y.toInt, z.toInt)
      .toArray

  def makeConnections(points: Array[Point3D]): Array[(Point3D, Point3D)] = 
    points.combinations(2)
      .toArray
      .sortBy:
        case Array(p, q) => p `distance` q
      .map:
        case Array(p, q) => (p, q)

  def merge(circuits: Array[Set[Point3D]], p: Point3D, q: Point3D): Array[Set[Point3D]] =
    val indexP = circuits.indexWhere(_.contains(p))
    val indexQ = circuits.indexWhere(_.contains(q))
    val merge = circuits(indexP) ++ circuits(indexQ)
    val newCircuits = circuits.zipWithIndex.filter:
        case (_, x) => x != indexP && x != indexQ
      .map(_._1)
    newCircuits :+ merge

  @tailrec
  def connect(circuits: Array[Set[Point3D]], connections: Array[(Point3D, Point3D)], last: (Point3D, Point3D) = null): (Point3D, Point3D) =
    if (circuits.size > 1)
      val (p, q) = connections.head
      val newCircuits = merge(circuits, p, q)
      connect(newCircuits, connections.tail, connections.head)
    else
      last

  def part1(input: String, maxConnections: Int): Int = 
    val points = parse(input)
    val connections = makeConnections(points).take(maxConnections)

    var groups = connections.foldLeft(points.map(p => Set(p))):
        case (circuits, (p, q)) => merge(circuits, p, q)
    
    groups.map(_.size).sorted.reverse.take(3).foldLeft(1)(_ * _)

  def part2(input: String): Long =
    val points = parse(input)
    val connections = makeConnections(points)
    val (p, q) = connect(points.map(p => Set(p)), connections)
    p.x.toLong * q.x.toLong

@main def main: Unit =
  val input = Source.fromFile("input/day8.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day8.part1(input, 1000)}")
  timed():
    println(s"Part 2: ${Day8.part2(input)}")

