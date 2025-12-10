package day10

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/10
object Day10:

  case class Machine(
    currentLights: List[Boolean], 
    lightsPattern: List[Boolean], 
    buttons: List[List[Int]]):
      def isOn: Boolean = currentLights == lightsPattern
      def turnOn: Machine =
        buttons.zipWithIndex.map(_._2).permutations.foldLeft(this):
          case (current, bs) => bs.foldLeft(current):
            case (m, b) if !m.isOn => m.click(b)
            case (m, _) => m

      def click(button: Int): Machine =
        val nextLights = buttons(button).foldLeft(currentLights):
          case (current, b) => current.updated(b, !current(b))
        Machine(nextLights, lightsPattern, buttons)

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
      .foreach(println)

    0
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day10.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day10.part1(input)}")
  timed():
    println(s"Part 2: ${Day10.part2(input)}")

