package org.example;

import java.util.ArrayList;
import java.util.List;

public class Faction {

    private String name;
    private List<Character> memberList;
    public Faction(String ally) {
        this.name = ally;
        memberList = new ArrayList<>();
    }


    public void take(Character character) {
        memberList.add(character);
    }

    public List<Character> getPlayers() {
        return this.memberList;
    }
}
