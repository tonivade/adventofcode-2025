package day7

import scala.io.Source
import aoc.timed
import aoc.Position
import aoc.parseMatrix
import scala.annotation.tailrec

// https://adventofcode.com/2025/day/7
object Day7:
  enum Item:
    case Free
    case Splitter
    case Beam
    case Touch
  
  import Item.*

  def split(m: Map[Position, Item])(p: Position): List[(Position, Item)] =
    m.get(p.up) match
      case Some(Splitter) => p -> Free :: p.up -> Touch :: p.leftUp -> Beam :: p.rightUp -> Beam :: Nil
      case Some(_) => p -> Free :: p.up -> Beam :: Nil
      case None => p -> Free :: Nil
  
  @tailrec
  def step1(matrix: Map[Position, Item]): Map[Position, Item] =
    val next = matrix.filter(_._2 == Beam)
      .keys
      .flatMap(split(matrix))

    if (next.isEmpty)
      matrix
    else 
      step1(matrix ++ next)
  
  def timelines(matrix: Map[Position, Item], beams: Iterable[Position], rowCount: Map[Int, Long]): Map[Int, Long] =
    beams.foldLeft(Map.empty[Int, Long]):
      case (current, p) => 
        val down = 
          if (matrix.getOrElse(p.down, Free) == Beam)
            rowCount.getOrElse(p.down.x, 0L)
          else 0L

        val left = 
          if (matrix.getOrElse(p.left, Free) == Splitter)
            rowCount.getOrElse(p.leftDown.x, 0L)
          else 0L

        val right = 
          if (matrix.getOrElse(p.right, Free) == Splitter)
            rowCount.getOrElse(p.rightDown.x, 0L)
          else 0L
        
        current.updated(p.x, down + left + right)

  @tailrec
  def step2(matrix: Map[Position, Item], rowCount: Map[Int, Long]): (Map[Position, Item], Map[Int, Long]) =
    val next = matrix.filter(_._2 == Beam)
      .keys
      .flatMap(split(matrix))

    if (next.isEmpty)
      (matrix, rowCount)
    else
      val beams = next.filter(_._2 == Beam).map(_._1)

      val nextRowCount = 
        if (beams.isEmpty)
          rowCount
        else
          timelines(matrix, beams, rowCount)

      step2(matrix ++ next, nextRowCount)

  def parse(input: String): Map[Position, Item] =
    parseMatrix(input):
      case 'S' => Beam
      case '^' => Splitter
      case _ => Free

  def part1(input: String): Int = 
    val matrix = parse(input)

    val result = step1(matrix)

    result.count(_._2 == Touch)

  def part2(input: String): Long =
    val matrix = parse(input)

    val start = matrix.find(_._2 == Beam).get._1
    
    val (_, count) = step2(matrix, Map(start.x -> 1L))

    count.values.sum

@main def main: Unit =
  val input = Source.fromFile("input/day7.txt").getLines().mkString("\n")
  timed():
    println(s"Part 1: ${Day7.part1(input)}")
  timed():
    println(s"Part 2: ${Day7.part2(input)}")

