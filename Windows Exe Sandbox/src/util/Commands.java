package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Commands {

	public static void execute(String command) throws IOException {
		Process process = Runtime.getRuntime().exec("powershell.exe " + command);
		
		BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
		
		new Thread(() -> {
			String line;
			
			try {
				while((line = error.readLine()) != null) {
					System.err.println(line);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
		String line;
		
		while((line = input.readLine()) != null) {
			System.out.println(line);
		}
	}
	
}
