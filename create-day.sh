#!/bin/bash

# import year and session_id from .env file
source .env

next_day="${1?required day}"

mkdir -p src/{main,test}/scala
mkdir -p mal

cat << EOF > "src/main/scala/Day${next_day}.scala"
package day${next_day}

import scala.io.Source
import aoc.timed

// https://adventofcode.com/${year}/day/${next_day}
object Day${next_day}:
  def part1(input: String): Int = ???
  def part2(input: String): Int = ???

@main def main: Unit =
  val input = Source.fromFile("input/day${next_day}.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: \${Day${next_day}.part1(input)}")
  timed():
    println(s"Part 2: \${Day${next_day}.part2(input)}")

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

cat << EOF > "mal/day${next_day}.mal"
; https://adventofcode.com/${year}/day/${next_day}

(import java.lang.String lines)

(load-file "mal/aoc.mal")

(def! input (if-defined DEBUG-EVAL "input/day${next_day}-test.txt" "input/day${next_day}.txt"))

(defn parse [file]
    (let* (input (lines (slurp file)))
        input
    )
)
EOF

./download-day.sh $next_day
