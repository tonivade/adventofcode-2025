package day2

import Day2._

class Day2Suite extends munit.FunSuite:

  val input = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"

  test("Day2 part1"):
    assertEquals(part1(input), 1227775554l)

  test("Day2 part2"):
    assertEquals(part2(input), 4174379265L)

