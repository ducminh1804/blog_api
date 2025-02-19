package com.ducminh.blogapi.constant;

public enum VoteEnum {
    UP_VOTE(1),
    DOWN_VOTE(-1),
    CANCEL_VOTE(0),
    ;
    private int voteStatus;

    public int getVoteStatus() {
        return voteStatus;
    }

    VoteEnum(int voteStatus) {
        this.voteStatus = voteStatus;
    }
}
