package day9

import Day9._
import aoc.Position

class Day9Suite extends munit.FunSuite:

  val input = """7,1
                |11,1
                |11,7
                |9,7
                |9,5
                |2,5
                |2,3
                |7,3""".stripMargin

  test("Day9 part1"):
    assertEquals(part1(input), 50L)

  test("Day9 part2"):
    assertEquals(part2(input), 24L)

