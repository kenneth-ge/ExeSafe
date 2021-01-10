package sandbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sandbox.permissions.Permission;
import util.Commands;

public class Sandbox {

	public File executable;
	public List<Permission> permissions;
	public File config;
	
	public Sandbox() {
		permissions = new ArrayList<>();
	}
	
	public void start() throws IOException, UnconfiguredException {
		write();
		
		Commands.execute("./" + config.getName());
	}
	
	public void write() throws IOException, UnconfiguredException {
		if(!executable()) {
			throw new UnconfiguredException();
		}
		
		Random random = new Random();
		
		//find random file name that works
		config = new File("sandbox.wsb");
		
		StringBuffer sb = new StringBuffer();
		while(config.exists()) {
			sb.append(random.nextInt(10));
			config = new File("sandbox" + sb.toString() + ".wsb");
		}
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(config));
		
		bw.append("<Configuration>\r\n");
		
		//configure permissions
		for(Permission p: permissions) {
			bw.append(p.getCommand()); //the permissions appends the newline automatically
		}
		
		//configure files
		bw.append("<MappedFolders>\r\n" + 
				"<MappedFolder>\r\n" + 
				"<HostFolder>" + executable.getAbsoluteFile().getParent() + "</HostFolder>\r\n" + 
				"<SandboxFolder>C:\\Users\\WDAGUtilityAccount\\Desktop</SandboxFolder>\r\n" + 
				"<ReadOnly>true</ReadOnly>\r\n" + 
				"</MappedFolder>\r\n" + 
				"</MappedFolders>\r\n");
		
		//run the exe
		bw.append("<LogonCommand>\r\n" + 
				"<Command>cmd /C \"C:\\users\\WDAGUtilityAccount\\Desktop\\" + executable.getAbsoluteFile().getParentFile().getName() + "\\" + executable.getName() + "\"</Command>\r\n" + 
				"</LogonCommand>\r\n");
		
		bw.append("</Configuration>\r\n");
		
		bw.close();
	}
	
	public boolean executable() {
		System.out.println(executable + " " + executable.exists());
		return executable != null && executable.exists();
	}
	
}
