package day5

import Day5._

class Day5Suite extends munit.FunSuite:

  val input = """3-5
                |10-14
                |16-20
                |12-18
                |
                |1
                |5
                |8
                |11
                |17
                |32""".stripMargin

  test("Day5 part1".ignore):
    assertEquals(part1(input), 1)

  test("Day5 part2".ignore):
    assertEquals(part2(input), 1)

