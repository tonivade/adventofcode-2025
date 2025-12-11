package day11

import scala.io.Source
import aoc.timed
import java.util.PriorityQueue
import scala.collection.mutable.HashMap

// https://adventofcode.com/2025/day/11
object Day11:
  case class Server(output: List[String])

  case class Node(name: String, distance: Int) extends Comparable[Node]:
    override def compareTo(other: Node): Int = this.distance - other.distance

  def search(graph: Map[String, Server]): Int =
    val queue = PriorityQueue[Node]()
    graph.keys.foreach:
      n => queue.add(new Node(n, if n == "you" then 0 else Int.MaxValue))

    val index = HashMap.empty[String, Node]
    queue.forEach:
      n => index.put(n.name, n)

    while(!queue.isEmpty) {
      val current = queue.remove()
      val edges = graph(current.name)

      for (edge <- edges.output) {
        val distance = 1 + current.distance
        val other = index(edge)
        if (distance < other.distance) {
          val newNode = Node(other.name, distance)
          queue.remove(other)
          queue.add(newNode)
          index.put(other.name, newNode)
        }
      }
    }

    index.foreach(println)

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

