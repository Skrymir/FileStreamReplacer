package com.skrymir.util;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Test;

public class FileStreamReplaceTest {

	@Test
	public void testRemoveDoubleQuotes() throws IOException {
		FileStreamReplace.removeDoubleQuotes("/Users/joshc/testFile");
		try(Stream<String> outputFileStream = 
				Files.lines(Paths.get("/Users/joshc/testFile_fixed"))) {
			long count = outputFileStream.filter(line -> line.contains("\""))
			.count();
			
			assertTrue("Line count is not zero", count == 0);
			
		}
	}

}
