package com.skrymir.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FileStreamReplace {

	public static void main(String[] args) {
		try {
			Path dir = Paths.get(args[0]);
			for(Path file : FileStreamReplace.listFilesInDir(dir, "*.{txt,java}")) {
				FileStreamReplace.removeDoubleQuotes(file.toString());
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Removes all " characters from given file
	 * @param fileName path and name of file to modify
	 * @throws IOException
	 */
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
		
		Files.move(outputFilePath, outputFilePath.resolveSibling(inputFilePath.getFileName()), 
				StandardCopyOption.REPLACE_EXISTING);
	}
	
	/**
	 * Searches given directory for files that match the pattern glob.
	 * "*.{txt,java}" will filter out files that don't end in .txt or .java
	 * @param dir Directory to read
	 * @param filePatternGlob include filter pattern
	 * @return List of Path containing the files in the directory and met the pattern
	 * @throws IOException
	 */
	public static List<Path> listFilesInDir(Path dir, String filePatternGlob) throws IOException {
	       List<Path> pathList = new ArrayList<>();
	       // Not really a stream in the Java 8 sense, for have to loop over it
	       try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, filePatternGlob)) {
	           for(Path entry: stream) {
	        	   pathList.add(entry);
	           }
	       } catch(DirectoryIteratorException ex) {
	           throw ex.getCause();
	       }
	       return pathList;
	   }
	
	
}
