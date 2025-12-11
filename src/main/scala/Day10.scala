package day10

import scala.io.Source
import aoc.timed
import scala.collection.parallel.immutable.ParVector

import scala.collection.parallel.CollectionConverters._

// https://adventofcode.com/2025/day/10
object Day10:

  case class Machine(lightsPattern: List[Boolean], buttons: List[List[Int]]):
      def turnOn: Int =
        val result = buttons.zipWithIndex.map(_._2).permutations.map:
            bs => 
              bs.foldLeft((List.fill(lightsPattern.size)(false), 0)):
                case ((lights, count), b) if lightsPattern != lights => (click(lights, b), count + 1)
                case (state, _) => state
          .filter(_._1 == lightsPattern)
          .map(_._2)
          .min
        println(s"done: $lightsPattern -> $result")
        result

      def click(lights: List[Boolean], button: Int): List[Boolean] =
        buttons(button).foldLeft(lights):
          case (current, b) => current.updated(b, !current(b))

  def part1(input: String): Int = 
    val regex = """^\[([.#]+)\]\s+((?:\([\d,]+\)\s*)+)\{([\d,]+)\}$""".r

    input.linesIterator
      .map:
        case regex(lights, buttons, _) => 
          Machine(
            lights.trim.map(x => x == '#').toList, 
            buttons.trim.split(" ").map(_.drop(1).dropRight(1).split(",").map(_.toInt).toList).toList)
      .toList
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

