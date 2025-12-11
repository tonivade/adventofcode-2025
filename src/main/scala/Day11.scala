package day11

import scala.io.Source
import aoc.timed
import scala.collection.mutable.HashMap

// https://adventofcode.com/2025/day/11
object Day11:

  val cache = HashMap.empty[String, List[List[String]]]

  def findAllPaths(graph: Map[String, List[String]], start: String, end: String): List[List[String]] =
    def dfs(current: String, visited: Set[String], path: List[String]): List[List[String]] =
      if (cache.contains(current))
        cache.get(current).get
      else if (current == end) 
        cache.put(current, path :: Nil)
        List(path)
      else
        graph.getOrElse(current, Nil).flatMap: neighbor =>
          if (!visited.contains(neighbor))
            val result = dfs(neighbor, visited + neighbor, path :+ neighbor)
            cache.put(current, result)
            result
          else
            cache.put(current, Nil)
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

  def part2(input: String): Long = 
    val servers = parse(input) + ("out" -> Nil)

    val pathFromSVRToFFT = findAllPaths(servers, "svr", "fft")
    val pathFromFFTtoDAC = findAllPaths(servers, "fft", "dac")
    val pathFromDACtoOUT = findAllPaths(servers, "dac", "out")

    pathFromSVRToFFT.size.toLong * pathFromFFTtoDAC.size.toLong * pathFromDACtoOUT.size.toLong

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

