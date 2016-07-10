package com.skrymir.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileStreamReplaceTest {
	static final String resPath = "src/test/resources/";
	static final Path testFilePath = Paths.get(resPath + "TempTestFile.txt");
	
	@BeforeClass
	public static void copyFiles() throws IOException {
		Files.copy(Paths.get(resPath + "origFiles/FileWithDoubleQuotes.txt"), testFilePath);
	}

	@Test
	public void testRemoveDoubleQuotes() throws IOException {
		FileStreamReplace.removeDoubleQuotes(testFilePath.toString());
		try(Stream<String> outputFileStream = 
				Files.lines(testFilePath)) {
			long count = outputFileStream.filter(line -> line.contains("\""))
			.count();
			
			assertTrue("Line count is not zero", count == 0);
			
		}
	}
	
	@Test
	public void testlistFilesInDir() throws IOException {
		Path orig = Paths.get(resPath + "origFiles/");
		List<Path> files = FileStreamReplace.listFilesInDir(orig, "*.{txt,java}");
		
		assertTrue(files.size() == 1);
	}
	
	@AfterClass
	public static void deleteTempFiles() throws IOException {
		Files.delete(testFilePath);
	}

}
