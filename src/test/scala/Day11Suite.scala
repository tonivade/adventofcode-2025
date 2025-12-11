package day11

import Day11._

class Day11Suite extends munit.FunSuite:

  val input1 = """aaa: you hhh
                 |you: bbb ccc
                 |bbb: ddd eee
                 |ccc: ddd eee fff
                 |ddd: ggg
                 |eee: out
                 |fff: out
                 |ggg: out
                 |hhh: ccc fff iii
                 |iii: out""".stripMargin

  val input2 = """svr: aaa bbb
                 |aaa: fft
                 |fft: ccc
                 |bbb: tty
                 |tty: ccc
                 |ccc: ddd eee
                 |ddd: hub
                 |hub: fff
                 |eee: dac
                 |dac: fff
                 |fff: ggg hhh
                 |ggg: out
                 |hhh: out""".stripMargin

  test("Day11 part1"):
    assertEquals(part1(input1), 5)

  test("Day11 part2"):
    assertEquals(part2(input2), 2)

