package day10

import scala.io.Source
import aoc.timed
import scala.collection.parallel.immutable.ParVector

import scala.collection.parallel.CollectionConverters._
import scala.annotation.tailrec

// https://adventofcode.com/2025/day/10
object Day10:

  @tailrec
  def factorial(n: Int, acc: BigInt = 1): BigInt =
    if (n <= 1) acc else factorial(n - 1, acc * n)

  case class Machine(lightsPattern: List[Boolean], buttons: List[List[Int]]):
    def permutations: BigInt = factorial(buttons.size)
    def turnOn: Int =
      @tailrec
      def go(lights: List[Boolean], bs: List[Int], steps: Int, maxSteps: Int): Int =
        if (bs.isEmpty)
          steps
        else if (steps >= maxSteps)
          steps
        else if (lights == lightsPattern)
          steps
        else
          go(click(lights, bs.head), bs.tail, steps + 1, maxSteps)

      val result = buttons.zipWithIndex.map(_._2).permutations.foldLeft(Int.MaxValue):
        (steps, bs) => 
          val newSteps = go(List.fill(lightsPattern.size)(false), bs, 0, steps)
          math.min(steps, newSteps)
      println(s"done: $lightsPattern -> $result")
      result

    def click(lights: List[Boolean], button: Int): List[Boolean] =
      buttons(button).foldLeft(lights):
        case (current, b) => current.updated(b, !current(b))

  def parse(input: String): List[Machine] =
    val regex = """^\[([.#]+)\]\s+((?:\([\d,]+\)\s*)+)\{([\d,]+)\}$""".r

    input.linesIterator
      .map:
        case regex(lights, buttons, _) => 
          Machine(
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

