package day11

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/11
object Day11:

  def findAllPaths(graph: Map[String, List[String]], start: String, end: String): List[List[String]] =
    def dfs(current: String, visited: Set[String], path: List[String]): List[List[String]] =
      if (current == end) 
        List(path)
      else
        graph.getOrElse(current, Nil).flatMap: neighbor =>
          if (!visited.contains(neighbor))
            dfs(neighbor, visited + neighbor, path :+ neighbor)
          else
            Nil

    dfs(start, Set(start), List(start))

  def parse(input: String): Map[String, List[String]] =
    input
      .linesIterator
      .map(_.split(":")).map:
        case Array(name, output) => name -> output.trim().split(" ").toList
      .toMap

  def part1(input: String): Int = 
    val servers = parse(input) + ("out" -> Nil)

    findAllPaths(servers, "you", "out").size

  def part2(input: String): Int = 
    val servers = parse(input) + ("out" -> Nil)

    val pathFromSVRToFFT = findAllPaths(servers, "svr", "fft")
    val pathFromFFTtoDAC = findAllPaths(servers, "fft", "dac")
    val pathFromDACtoOUT = findAllPaths(servers, "dac", "out")

    pathFromSVRToFFT.size * pathFromFFTtoDAC.size * pathFromDACtoOUT.size

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

