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

  test("Day4 part1".ignore):
    assertEquals(part1(input), 1)

  test("Day4 part2".ignore):
    assertEquals(part2(input), 1)

