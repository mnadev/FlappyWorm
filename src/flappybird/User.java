package flappybird;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	
	public String name;
	public ArrayList<Integer> scores = new ArrayList<Integer>();
	
	public User(String name) {
		this.name = name;
	}
	
	
	public void addScore(int score) {
		int i = 0;
		for(Integer sc: scores) {
			if(score > sc) {
				scores.add(i + 1, score);;
			}
			i++;
		}
	}
	
	public static ArrayList<User> sortUsers(ArrayList<User> users) {
		ArrayList<User> sortedUser = new ArrayList<User>();
		
		for(int i = 0; i < users.size(); i++) {
			int j = 0;
			int maxScore = 0;
			int maxUserIndex = 0;
			
			for(User u: users) {
				for(Integer s: u.scores) {
					if(s > maxScore) {
						maxScore = s;
						maxUserIndex = j;
					}
				}
				j++;
			}
			
			sortedUser.add(users.remove(maxUserIndex));
		}
		
		return sortedUser;
	}
}
