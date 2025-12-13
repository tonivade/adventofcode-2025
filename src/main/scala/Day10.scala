package day10

import scala.annotation.tailrec
import scala.io.Source
import aoc.timed
import aoc.memoize

// https://adventofcode.com/2025/day/10
object Day10:

  def generateAllPermutations(size: Int): Iterator[List[Int]] =
    val list = (0 until size).toList
    Iterator.range(1, size)
      .flatMap: k =>
        list.combinations(k)

  case class Machine(lightsPattern: List[Boolean], buttons: List[List[Int]], joltsPattern: List[Int]):

    def toSet(ls: List[Boolean]) = 
        ls.zipWithIndex.collect:
              case (on, i) if on => i
            .toSet

    def validMoves: Map[Set[Int], List[List[Int]]] =
      @tailrec
      def go(lights: List[Boolean], bs: List[Int]): Set[Int] =
        if (bs.isEmpty) 
          toSet(lights)
        else 
          go(clickLights(lights, bs.head), bs.tail)

      val n = buttons.size
      val base = generateAllPermutations(n).toList

      val all =
        base ++
          base.flatMap { bs =>
            (0 until n).map(b => bs :+ b)
          }

      all
        .map { bs =>
          go(List.fill(lightsPattern.size)(false), bs) -> bs
        }
        .groupMap(_._1)(_._2)

    def lightsOn: Int = validMoves(toSet(lightsPattern)).map(_.size).min

    def joltsOn: Int = 
      val patterns = validMoves

      lazy val getMinPresses: List[Int] => Option[Int] =
        memoize:
          target =>
            if (target.forall(_ == 0)) 
              Some(0)
            else
              // Identify indicators with odd joltage levels
              val indicators: Set[Int] = target.zipWithIndex.collect {
                case (joltage, i) if joltage % 2 == 1 => i
              }.toSet

              var result: Option[Int] = None

              for (presses <- patterns.getOrElse(indicators, List.empty)) {
                // Simulate button presses
                val targetAfter = target.toArray
                for (button <- presses; index <- buttons(button)) {
                  targetAfter(index) -= 1
                }

                if (!targetAfter.exists(_ < 0)) {
                  // All new target levels are even; compute half-target
                  val halfTarget = targetAfter.map(_ / 2).toList
                  val halfTargetPresses = getMinPresses(halfTarget)
                  halfTargetPresses.foreach { nh =>
                    val numPresses = (2 * nh) + presses.size
                    result = result match {
                      case None => Some(numPresses)
                      case Some(prev) => 
                        val next = math.min(prev, numPresses)
                        Some(next)
                    }
                  }
                }
              }

              result

      getMinPresses(joltsPattern).get

    def clickLights(lights: List[Boolean], button: Int): List[Boolean] =
      buttons(button).foldLeft(lights):
        case (current, b) => current.updated(b, !current(b))

    def clickJolts(jolts: List[Int], button: Int): List[Int] =
      buttons(button).foldLeft(jolts):
        case (current, b) => current.updated(b, current(b) + 1)

  def parse(input: String): Iterator[Machine] =
    val regex = """^\[([.#]+)\]\s+((?:\([\d,]+\)\s*)+)\{([\d,]+)\}$""".r

    input.linesIterator
      .map:
        case regex(lights, buttons, joltages) => 
          Machine(
            lights.trim.map(x => x == '#').toList, 
            buttons.trim.split(" ").map(_.drop(1).dropRight(1).split(",").map(_.toInt).toList).toList,
            joltages.trim.split(",").map(_.toInt).toList) 

  def part1(input: String): Int = 
    parse(input)
      .map(_.lightsOn)
      .sum

  def part2(input: String): Int =
    parse(input)
      .map(_.joltsOn)
      .sum

@main def main: Unit =
  val input = Source.fromFile("input/day10.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day10.part1(input)}")
  timed():
    println(s"Part 2: ${Day10.part2(input)}")

