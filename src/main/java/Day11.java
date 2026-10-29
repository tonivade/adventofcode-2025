import com.github.tonivade.diesel.Combine;
import static com.github.tonivade.diesel.Combine.pipe;
import static com.github.tonivade.diesel.Combine.sequence;
import static com.github.tonivade.diesel.Concurrent.parZip;
import static com.github.tonivade.diesel.Program.success;
import static com.github.tonivade.diesel.Program.supply;
import static java.lang.IO.println;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

import com.github.tonivade.diesel.Program;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.pcollections.PMap;
import org.pcollections.TreePMap;

class Day11 {

  static Program<Void, Void, Integer> part1(String input) {
    return pipe(
      parse(input),
      success(s -> s.plus("out", List.of())),
      graph -> findAllPaths(graph, "you", "out"));
  }

  static Program<Void, Void, Long> part2(String input) {
    return pipe(
      parse(input),
      success(s -> s.plus("out", List.of())),
      graph -> parZip(
        findAllPaths(graph, "svr", "fft"),
        findAllPaths(graph, "fft", "dac"),
        findAllPaths(graph, "dac", "out"),
        (svrToFft, fftToDac, dacToOut) -> (long) svrToFft * (long) fftToDac * dacToOut));
  }

  static Program<Void, Void, Integer> findAllPaths(
      PMap<String, List<String>> graph, String start, String end) {
    return Program.<Void, Void, String, Integer>memoizeRecursive((self, current) -> {
          var result = graph.get(current).stream().map(output -> {
            if (output.equals(end)) {
              return Program.<Void, Void, Integer>success(1);
            }
            return self.apply(output);
          }).toList();
          return sequence(result)
              .map(list -> list.stream().reduce(0, Integer::sum));
        }
      ).apply(start);
  }

  static Program<Void, Void, PMap<String, List<String>>> parse(String input) {
    return supply(() -> {
      return TreePMap.from(input.lines()
        .map(line -> {
          var splitted = line.split(":");
          return entry(splitted[0], List.of(splitted[1].trim().split(" ")));
        })
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
    });
  }

  public static void main(String...args) throws IOException {
    var input = Files.readString(Paths.get("input/day11.txt"));
    println(part1(input).timed().evalOrElseThrow());
    println(part2(input).timed().evalOrElseThrow());
  }
}