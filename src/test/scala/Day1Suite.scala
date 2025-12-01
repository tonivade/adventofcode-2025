package day1

import Day1._

class Day1Suite extends munit.FunSuite:

  val input = """L68
                |L30
                |R48
                |L5
                |R60
                |L55
                |L1
                |L99
                |R14
                |L82""".stripMargin

  test("Day1 part1"):
    assertEquals(part1(input), 3)

  test("Day1 part2".ignore):
    assertEquals(part2(input), 1)

