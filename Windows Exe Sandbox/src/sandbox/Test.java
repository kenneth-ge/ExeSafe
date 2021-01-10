package sandbox;

import java.io.File;
import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException, UnconfiguredException {
		Sandbox s = new Sandbox();
		
		s.executable = new File("zoom.exe");
		
		s.start();
	}
	
}
