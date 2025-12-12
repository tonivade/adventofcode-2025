package day12

import scala.io.Source
import aoc.timed

// https://adventofcode.com/2025/day/12
object Day12:

  case class Tree(width: Int, height: Int, presents: List[Int]):
    def totalArea: Int = width * height
    def requiredArea(allPresents: Array[Present]): Int = 
      presents.zip(allPresents).map:
        case (times, present) => present.area * times
      .sum

  case class Present(number: Int, shape: List[List[Int]]):
    def area: Int = shape.flatMap(identity).count(_ == 1)

  def part1(input: String): Int = 
    val groups = input.split("\n\n")

    val presents = groups.dropRight(1).map:
      present => 
        val number = present.split("\n").head.split(":").head.toInt
        val shape = present.split("\n").tail.map(_.map(ch => if ch == '#' then 1 else 0).toList).toList
        Present(number, shape)

    val trees = groups.last.linesIterator.map:
        line => 
          val Array(dimensions, presents) = line.split(":")
          val Array(width, height) = dimensions.split("x")
          Tree(width.toInt, height.toInt, presents.trim.split(" ").map(_.toInt).toList)
      .toList

    presents.foreach(println)
    trees.foreach(println)

    trees.count:
        tree => 
          println(s"${tree.totalArea} vs ${tree.requiredArea(presents)}")
          tree.totalArea >= tree.requiredArea(presents);

  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day12.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day12.part1(input)}")
  timed():
    println(s"Part 2: ${Day12.part2(input)}")

