package day4

import Day4._

class Day4Suite extends munit.FunSuite:

  val input = """..@@.@@@@.
                |@@@.@.@.@@
                |@@@@@.@.@@
                |@.@@@@..@.
                |@@.@@@@.@@
                |.@@@@@@@.@
                |.@.@.@.@@@
                |@.@@@.@@@@
                |.@@@@@@@@.
                |@.@.@@@.@.""".stripMargin

  test("Day4 part1"):
    assertEquals(part1(input), 13)

  test("Day4 part2"):
    assertEquals(part2(input), 43)

