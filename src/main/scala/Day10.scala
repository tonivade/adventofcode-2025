package day10

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/10
object Day10:

  case class Machine(
    currentLights: List[Boolean], 
    lightsPattern: List[Boolean], 
    buttons: List[List[Int]],
    iterations: Int = 0):
      def isOn: Boolean = currentLights == lightsPattern
      def turnOn: Machine =
        buttons.zipWithIndex.map(_._2).permutations.map:
            case bs => bs.foldLeft(this):
              case (m, b) if !m.isOn => m.click(b)
              case (m, _) => m
          .filter(_.isOn)
          .minBy(_.iterations)

      def click(button: Int): Machine =
        val nextLights = buttons(button).foldLeft(currentLights):
          case (current, b) => current.updated(b, !current(b))
        Machine(nextLights, lightsPattern, buttons, iterations + 1)

  def part1(input: String): Int = 
    val regex = """^\[([.#]+)\]\s+((?:\([\d,]+\)\s*)+)\{([\d,]+)\}$""".r

    input.linesIterator
      .map:
        case regex(lights, buttons, _) => 
          Machine(
            lights.trim.map(_ => false).toList,
            lights.trim.map(x => x == '#').toList, 
            buttons.trim.split(" ").map(_.drop(1).dropRight(1).split(",").map(_.toInt).toList).toList)
      .map(_.turnOn)
      .map(_.iterations)
      .sum

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day10.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day10.part1(input)}")
  timed():
    println(s"Part 2: ${Day10.part2(input)}")

