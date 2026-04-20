import static com.github.tonivade.diesel.Program.memoize;
import static com.github.tonivade.diesel.Program.sequence;
import static com.github.tonivade.diesel.Program.suspend;
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

  static int part1(String input) {
    var servers = parse(input).plus("out", List.of());

    return new Dfs(servers, "you", "out").findAllPaths().getOrElseThrow();
  }

  static long part2(String input) {
    var servers = parse(input).plus("out", List.of());

    int svrToFft = new Dfs(servers, "svr", "fft").findAllPaths().getOrElseThrow();
    int fftToDac = new Dfs(servers, "fft", "dac").findAllPaths().getOrElseThrow();
    int dacToOut = new Dfs(servers, "dac", "out").findAllPaths().getOrElseThrow();

    return (long) svrToFft * (long) fftToDac * dacToOut;
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

  static PMap<String, List<String>> parse(String input) {
    return TreePMap.from(input.lines()
      .map(line -> {
        var splitted = line.split(":");
        return entry(splitted[0], List.of(splitted[1].trim().split(" ")));
      })
      .collect(toMap(Map.Entry::getKey, Map.Entry::getValue)));
  }

  static void main() throws IOException {
    var input = Files.readString(Paths.get("input/day11.txt"));
    println(part1(input));
    println(part2(input));
  }
}