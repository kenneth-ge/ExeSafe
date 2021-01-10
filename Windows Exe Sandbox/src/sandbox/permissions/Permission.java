package sandbox.permissions;

public class Permission {

	public static enum Value {Enabled, Disabled};
	
	public String displayName;
	public String internalName;
	public Value value;
	
	public Permission(String displayName, String internalName, Value value) {
		this.displayName = displayName;
		this.internalName = internalName;
		this.value = value;
	}
	
	public String getCommand() {
		return "<" + internalName + ">" + value.toString() + "</" + internalName + ">\r\n";
	}
	
	@Override
	public int hashCode() {
		return internalName.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Permission)) {
			return false;
		}
		
		return ((Permission) other).internalName.equals(internalName);
	}
	
	public static Permission[] perms = {new Permission("Access to the Internet", "Networking", Value.Enabled),
										new Permission("Access to your GPU", "vGPU", Value.Enabled),
										new Permission("Access to copy/paste", "ClipboardRedirection", Value.Enabled),
										new Permission("Access to your microphone", "AudioInput", Value.Disabled),
										new Permission("Access to your webcam", "VideoInput", Value.Disabled),
										new Permission("Access to your printer", "PrinterRedirection", Value.Disabled),
										new Permission("Enable extra security features (may cause some applications to malfunction)", "ProtectedClient", Value.Disabled)};
	
}
