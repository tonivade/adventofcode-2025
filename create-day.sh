#!/bin/bash

# import year and session_id from .env file
source .env

next_day="${1?required day}"

mkdir -p src/{main,test}/scala

cat << EOF > "src/main/scala/Day${next_day}.scala"
package day${next_day}

import scala.io.Source

// https://adventofcode.com/${year}/day/${next_day}
object Day${next_day}:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day${next_day}.txt").getLines().mkString("\n")
  println(Day${next_day}.part1(input))
  println(Day${next_day}.part2(input))

EOF

cat << EOF > "src/test/scala/Day${next_day}Suite.scala"
package day${next_day}

import Day${next_day}._

class Day${next_day}Suite extends munit.FunSuite:

  val input = """""".stripMargin

  test("Day${next_day} part1".ignore):
    assertEquals(part1(input), 1)

  test("Day${next_day} part2".ignore):
    assertEquals(part2(input), 1)

EOF

./download-day.sh $next_day
