package day11

import Day11._

class Day11Suite extends munit.FunSuite:

  val input = """aaa: you hhh
                |you: bbb ccc
                |bbb: ddd eee
                |ccc: ddd eee fff
                |ddd: ggg
                |eee: out
                |fff: out
                |ggg: out
                |hhh: ccc fff iii
                |iii: out""".stripMargin

  test("Day11 part1".ignore):
    assertEquals(part1(input), 1)

  test("Day11 part2".ignore):
    assertEquals(part2(input), 1)

