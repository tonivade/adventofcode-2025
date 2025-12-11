package day10

import scala.io.Source
import aoc.timed
import scala.collection.parallel.CollectionConverters._
import scala.annotation.tailrec

// https://adventofcode.com/2025/day/10
object Day10:

  @tailrec
  def factorial(n: Int, acc: BigInt = 1): BigInt =
    if (n <= 1) acc else factorial(n - 1, acc * n)

  def generateAllPermutations(size: Int): Iterable[List[Int]] =
    val list = (0 until size).toList
    Iterable.range(1, size)
      .view
      .flatMap: k =>
        list.combinations(k)
          .flatMap(_.permutations)

  case class Machine(id: Int, lightsPattern: List[Boolean], buttons: List[List[Int]]):
    def permutations: BigInt = factorial(buttons.size)
    def turnOn: Int =
      @tailrec
      def go(lights: List[Boolean], bs: List[Int]): List[Boolean] =
        if (bs.isEmpty)
          lights
        else
          go(click(lights, bs.head), bs.tail)

      val result = generateAllPermutations(buttons.size).find: bs =>
        go(List.fill(lightsPattern.size)(false), bs) == lightsPattern

      println(s"done: $id -> ${result.get.size}")
      result.get.size

    def click(lights: List[Boolean], button: Int): List[Boolean] =
      buttons(button).foldLeft(lights):
        case (current, b) => current.updated(b, !current(b))

  def parse(input: String): List[Machine] =
    val regex = """^\[([.#]+)\]\s+((?:\([\d,]+\)\s*)+)\{([\d,]+)\}$""".r

    input.linesIterator
      .zipWithIndex
      .map:
        case (regex(lights, buttons, _), pos) => 
          Machine(
            pos,
            lights.trim.map(x => x == '#').toList, 
            buttons.trim.split(" ").map(_.drop(1).dropRight(1).split(",").map(_.toInt).toList).toList)
      .toList

  def part1(input: String): Int = 
    var machines = parse(input)

    machines
      .sortBy(_.permutations)
      .par
      .map(_.turnOn)
      .sum

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day10.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day10.part1(input)}")
  timed():
    println(s"Part 2: ${Day10.part2(input)}")

