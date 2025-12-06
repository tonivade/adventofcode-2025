package day6

import Day6._

class Day6Suite extends munit.FunSuite:

  val input = """123 328  51 64 
                | 45 64  387 23 
                |  6 98  215 314
                |*   +   *   +  """.stripMargin

  test("Day6 part1"):
    assertEquals(part1(input), 4277556L)

  test("Day6 part2"):
    assertEquals(part2(input), 3263827L)
