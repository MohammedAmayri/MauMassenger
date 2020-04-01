package MAU_new_Massenger;
import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private ImageIcon photo;
    private ArrayList<User> contacts;

    //,ArrayList<User> contacts
    public User(String name){
        this.name=name;
        //this.photo=photo;
    }
    
    

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}



	public String getName(){
        return name;
    }

    public ImageIcon getPhoto() {
        return photo;
    }

    public ArrayList<User> getContacts() {
        return contacts;
    }
    
}
