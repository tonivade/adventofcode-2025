package day11

import scala.io.Source
import aoc.timed
import scala.collection.mutable.HashMap

// https://adventofcode.com/2025/day/11
object Day11:

  def memoize[I, O](f: I => O): I => O =
    val cache = HashMap.empty[I, O]
    (i: I) => cache.getOrElseUpdate(i, f(i))

  def findAllPaths(graph: Map[String, List[String]], start: String, end: String): Int =
    lazy val dfs: String => Int =
      memoize:
        current => 
          graph(current).map:
              output =>
                if (output == end)
                  1
                else if (graph.contains(output))
                  dfs(output)
                else
                  0
            .sum

    dfs(start)

  def parse(input: String): Map[String, List[String]] =
    input
      .linesIterator
      .map(_.split(":")).map:
        case Array(name, output) => name -> output.trim().split(" ").toList
      .toMap

  def part1(input: String): Int = 
    val servers = parse(input) + ("out" -> Nil)

    findAllPaths(servers, "you", "out")

  def part2(input: String): Long = 
    val servers = parse(input) + ("out" -> Nil)

    val svrToFft = findAllPaths(servers, "svr", "fft")
    val fftToDac = findAllPaths(servers, "fft", "dac")
    val dacToOut = findAllPaths(servers, "dac", "out")

    svrToFft.toLong * fftToDac.toLong * dacToOut.toLong

@main def main: Unit =
  val input = Source.fromFile("input/day11.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day11.part1(input)}")
  timed():
    println(s"Part 2: ${Day11.part2(input)}")

