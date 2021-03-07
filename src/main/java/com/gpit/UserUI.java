package com.gpit;

public class UserUI {

    private BoardUI boardUI;
    private UserInfo userInfo;

    public UserUI(String userTag) {
        this.boardUI = new BoardUI();
        this.userInfo = new UserInfo(userTag);
    }

    public BoardUI getBoardUI() {
        return boardUI;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }
}
