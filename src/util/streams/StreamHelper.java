package util.streams;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamHelper {

	public static <T, U> List<U> convertList(List<T> from, Function<T, U> func) {
		return from.stream().map(func).collect(Collectors.toList());
	}

}
