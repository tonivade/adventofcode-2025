package day10

import scala.annotation.tailrec
import scala.io.Source
import aoc.timed
import aoc.memoize

// https://adventofcode.com/2025/day/10
object Day10:

  def nonEmptySubsets(size: Int): Iterator[List[Int]] =
    val list = (0 until size).toList
    Iterator.range(1, size)
      .flatMap: k =>
        list.combinations(k)

  case class Machine(lightsPattern: List[Boolean], buttons: List[List[Int]], joltsPattern: List[Int]):

    lazy val buttonEffects: Vector[Vector[Int]] =
      buttons.map(_.toVector).toVector

    lazy val patterns: Map[Set[Int], List[List[Int]]] =
      @tailrec
      def go(lights: List[Boolean], bs: List[Int]): Set[Int] =
        if (bs.isEmpty) 
          oddIndices(lights)
        else 
          go(clickLights(lights, bs.head), bs.tail)

      val n = buttons.size
      val base = nonEmptySubsets(n).toList

      val all =
        base ++ base.flatMap: 
            bs => (0 until n).map(b => bs :+ b)

      all
        .map: 
          bs => go(List.fill(lightsPattern.size)(false), bs) -> bs
        .groupMap(_._1)(_._2)

    def oddIndices(ls: List[Boolean]) = 
        ls.zipWithIndex.collect:
              case (on, i) if on => i
            .toSet

    def lightsOn: Int = patterns(oddIndices(lightsPattern)).map(_.size).min

    // here I'm applying this algorithm https://aoc.winslowjosiah.com/solutions/2025/day/10/
    def joltsOn: Int =

      inline def simulate(target: List[Int], presses: List[Int]): List[Int] =
        val targetAfter = target.toArray 
        presses.iterator.flatMap(buttonEffects).foreach:
          index => targetAfter(index) -= 1
        targetAfter.toList

      lazy val getMinPresses: List[Int] => Option[Int] =
        memoize: target =>
          if (target.forall(_ == 0))
            Some(0)
          else
            val indicators = oddIndices(target.map(_ % 2 == 1))

            patterns
              .getOrElse(indicators, Nil)
              .iterator
              .flatMap:
                presses =>
                  val targetAfter = simulate(target, presses)
                  if (targetAfter.exists(_ < 0))
                    None
                  else
                    val halfTarget = targetAfter.map(_ / 2)
                    getMinPresses(halfTarget).map: 
                      nh => (2 * nh) + presses.size
              .minOption

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

