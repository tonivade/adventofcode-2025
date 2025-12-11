package day11

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/11
object Day11:
  case class Node(name: String, distance: Int) extends Comparable[Node]:
    override def compareTo(other: Node): Int = this.distance - other.distance

  def findAllPaths(graph: Map[String, List[String]]): List[List[String]] =
    def dfs(current: String, visited: Set[String], path: List[String]): List[List[String]] =
      // If we reach the end, return the completed path
      if (current == "out") return List(path)
      // Otherwise explore neighbors
      graph.getOrElse(current, Nil).flatMap: neighbor =>
        // Avoid revisiting nodes to prevent cycles
        if (!visited.contains(neighbor))
          dfs(neighbor, visited + neighbor, path :+ neighbor)
        else
          Nil

    dfs("you", Set("you"), List("you"))

  def part1(input: String): Int = 
    val servers = input
      .linesIterator
      .map(_.split(":")).map:
        case Array(name, output) => name -> output.trim().split(" ").toList
      .toMap

    findAllPaths(servers + ("out" -> List.empty)).size

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

