package day12

import Day12._

class Day12Suite extends munit.FunSuite:

  val input = """0:
                |###
                |##.
                |##.
                |
                |1:
                |###
                |##.
                |.##
                |
                |2:
                |.##
                |###
                |##.
                |
                |3:
                |##.
                |###
                |##.
                |
                |4:
                |###
                |#..
                |###
                |
                |5:
                |###
                |.#.
                |###
                |
                |4x4: 0 0 0 0 2 0
                |12x5: 1 0 1 0 2 2
                |12x5: 1 0 1 0 3 2""".stripMargin

  test("Day12 part1".ignore):
    assertEquals(part1(input), 2)

  test("Day12 part2".ignore):
    assertEquals(part2(input), 1)

