package day11

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/11
object Day11:

  def findAllPaths(graph: Map[String, List[String]], start: String): List[Set[String]] =
    def dfs(current: String, visited: Set[String], path: Set[String]): List[Set[String]] =
      if (current == "out") 
        List(path)
      else
        graph.getOrElse(current, Nil).flatMap: neighbor =>
          if (!visited.contains(neighbor))
            dfs(neighbor, visited + neighbor, path + neighbor)
          else
            Nil

    dfs(start, Set(start), Set(start))

  def parse(input: String): Map[String, List[String]] =
    input
      .linesIterator
      .map(_.split(":")).map:
        case Array(name, output) => name -> output.trim().split(" ").toList
      .toMap

  def part1(input: String): Int = 
    val servers = parse(input)

    findAllPaths(servers + ("out" -> List.empty), "you").size

  def part2(input: String): Int = 
    val servers = parse(input)

    findAllPaths(servers + ("out" -> List.empty), "svr").filter:
        path => path.contains("dac") && path.contains("fft")
      .size

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

