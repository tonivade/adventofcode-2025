package day3

import Day3._

class Day3Suite extends munit.FunSuite:

  val input = """987654321111111
                |811111111111119
                |234234234234278
                |818181911112111""".stripMargin

  test("Day3 part1"):
    assertEquals(part1(input), 357)

  test("Day3 part2".ignore):
    assertEquals(part2(input), 1)

