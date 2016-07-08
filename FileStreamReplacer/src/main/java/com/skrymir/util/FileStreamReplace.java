package com.skrymir.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

public class FileStreamReplace {

	public static void main(String[] args) {
		try {
			FileStreamReplace.removeDoubleQuotes(args[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void removeDoubleQuotes(String fileName) throws IOException {
		Path inputFilePath = Paths.get(fileName);
		Path outputFilePath = Paths.get(fileName + "_fixed");
		// try-with-resources -- let java close my stream and writer!
		// files can be big, read line by line
		try (Stream<String> inputStream = Files.lines(inputFilePath);
				PrintWriter outputWriter = new PrintWriter(outputFilePath.toString(), "UTF-8")) {
			inputStream.map(s -> s.replaceAll("\"", "")) // do the replace
			.forEachOrdered(outputWriter::println);		 // write fixed line to new file
		}
		
		Files.move(outputFilePath, outputFilePath.resolveSibling(inputFilePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
	}
	
	
}
