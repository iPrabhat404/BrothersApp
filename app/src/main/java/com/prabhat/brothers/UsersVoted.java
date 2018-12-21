package com.prabhat.brothers;

/**
 * Created by HP on 3/31/2018.
 */

public class UsersVoted {

    String userId;
    String userVote;

    public UsersVoted(String userId, String userVote) {
        this.userId = userId;
        this.userVote = userVote;
    }

    public UsersVoted() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserVote() {
        return userVote;
    }

    public void setUserVote(String userVote) {
        this.userVote = userVote;
    }
}
