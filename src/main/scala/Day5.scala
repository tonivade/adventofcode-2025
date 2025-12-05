package day5

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/5
object Day5:

  case class Interval(start: Long, end: Long):
    def contains(other: Long): Boolean =
      other >= start && other <= end
    def overlaps(other: Interval): Boolean =
      end >= other.start && start <= other.end
    def size: Long = (end - start) + 1
    def merge(other: Interval): Interval =
      require(overlaps(other), s"Intervals $this and $other do not overlap")
      Interval(
        start = start.min(other.start),
        end   = end.max(other.end)
      )
  
  def parseRanges(ranges: String): Array[Interval] =
    ranges.split("\n")
      .map(_.split("-")).map:
        case Array(start, end) => Interval(start.toLong, end.toLong)

  def part1(input: String): Int = 
    val Array(r, p) = input.split("\n\n")
    val ranges = parseRanges(r)
    val products = p.split("\n").map(_.toLong)

    products.count(i => ranges.exists(_.contains(i)))

  def part2(input: String): Long = 
    val Array(r, p) = input.split("\n\n")
    val ranges = parseRanges(r)

    val grouped = ranges.sortBy(_.start).foldLeft(List.empty[List[Interval]]):
      case (state, interval) => 
        val group = state.indexWhere(_.exists(_.overlaps(interval)))
        if (group >= 0)
          state.updated(group, state(group) :+ interval)
        else
          state :+ List(interval)

    grouped.map:
        group => group.reduce(_ `merge` _)
      .map(_.size)
      .sum

@main def main: Unit =
  val input = Source.fromFile("input/day5.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day5.part1(input)}")
  timed():
    println(s"Part 2: ${Day5.part2(input)}")

