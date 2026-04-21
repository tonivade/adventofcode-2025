import static com.github.tonivade.diesel.Program.memoize;
import static com.github.tonivade.diesel.Program.pipe;
import static com.github.tonivade.diesel.Program.sequence;
import static com.github.tonivade.diesel.Program.success;
import static com.github.tonivade.diesel.Program.supply;
import static com.github.tonivade.diesel.Program.suspend;
import static com.github.tonivade.diesel.Program.zip;
import static java.lang.IO.println;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.pcollections.PMap;
import org.pcollections.TreePMap;

import com.github.tonivade.diesel.Program;

class Day11 {

  static Program<Void, Void, Integer> part1(String input) {
    return pipe(
      parse(input), 
      success(s -> s.plus("out", List.of())), 
      s -> new Dfs(s, "you", "out").findAllPaths());
  }

  static Program<Void, Void, Long> part2(String input) {
    return pipe(
      parse(input), 
      success(s -> s.plus("out", List.of())), 
      s -> zip(
        new Dfs(s, "svr", "fft").findAllPaths(), 
        new Dfs(s, "fft", "dac").findAllPaths(),
        new Dfs(s, "dac", "out").findAllPaths(),
        (svrToFft, fftToDac, dacToOut) -> (long) svrToFft * (long) fftToDac * dacToOut));
  }

  static class Dfs {

    final PMap<String, List<String>> graph;
    final String start;
    final String end;

    public Dfs(PMap<String, List<String>> graph, String start, String end) {
      this.graph = graph;
      this.start = start;
      this.end = end;
    }

    private final Function<String, Program<Void, Void, Integer>> dfs = memoize(new Function<>() {
      public Program<Void, Void, Integer> apply(String current) {
        var result = graph.get(current).stream().map(output -> {
          if (output.equals(end)) {
            return Program.<Void, Void, Integer>success(1);
          }
          return suspend(() -> dfs.apply(output));
        }).toList();
        return sequence(result).map(list -> list.stream().reduce(0, Integer::sum));
      }
    });

    Program<Void, Void, Integer> findAllPaths() {
      return dfs.apply(start);
    }
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

  static void main() throws IOException {
    var input = Files.readString(Paths.get("input/day11.txt"));
    println(part1(input).getOrElseThrow());
    println(part2(input).getOrElseThrow());
  }
}