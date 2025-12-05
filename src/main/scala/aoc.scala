package aoc

def timed[R](label: String = "")(block: => R): R =
  val start = System.nanoTime()
  val result = block
  val end = System.nanoTime()
  val elapsedMs = (end - start) / 1_000_000.0
  if (label.nonEmpty) 
      println(s"[$label] Elapsed time: $elapsedMs ms")
  else 
      println(s"Elapsed time: $elapsedMs ms")
  result
