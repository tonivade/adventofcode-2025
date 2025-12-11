package day11

import scala.io.Source
import aoc.timed
import scala.collection.mutable.{Map => MutableMap}
import scala.collection.mutable.PriorityQueue

// https://adventofcode.com/2025/day/11
object Day11:
  case class Server(output: List[String])

  case class Node(name: String, distance: Int) extends Comparable[Node]:
    override def compareTo(other: Node): Int = this.distance - other.distance

  def search(graph: Map[String, Server]): Int =
    val distances: MutableMap[String, Int] =
      MutableMap(graph.keys.map(_ -> Int.MaxValue).toSeq: _*)

    distances("you") = 0

    val pq = PriorityQueue(Node("you", 0))

    while (pq.nonEmpty)
      val Node(current, distance) = pq.dequeue()

      if (distance == distances(current))
        for (neighbor <- graph(current).output)
          val newDist = distance + 1
          if (newDist < distances(neighbor))
            distances(neighbor) = newDist
            pq.enqueue(Node(neighbor, newDist))

    distances.foreach(println)

    0

  def part1(input: String): Int = 
    val servers = input
      .linesIterator
      .map(_.split(":")).map:
        case Array(name, output) => name -> new Server(output.trim().split(" ").toList)
      .toMap

    search(servers + ("out" -> Server(List.empty)))


    0
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

