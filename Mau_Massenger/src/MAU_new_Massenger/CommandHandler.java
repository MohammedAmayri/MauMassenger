package MAU_new_Massenger;

public class CommandHandler {

	User user;
	String command;
	public CommandHandler(String command,User user)
	{
		this.user=user;
		this.command=command;
	}
	public User getUser() {
		return user;
	}
	public String getCommand() {
		return command;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	

}