package com.gpit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScenarioLoader {

    private int scenarioID;
    private List<ArrayList<Integer>> playerScenario;
    private List<ArrayList<Integer>> enemyScenario;


    public ScenarioLoader(int id) throws IOException, URISyntaxException {
        this.scenarioID = id;
        this.playerScenario = this.loadScenario("player_" + String.valueOf(id) + ".txt");
        this.enemyScenario = this.loadScenario("enemy_" + String.valueOf(id) + ".txt");

    }



    public List<ArrayList<Integer>> loadScenario(String filename) throws IOException, URISyntaxException {


        File file = Paths.get(getClass().getClassLoader().getResource(filename).toURI()).toFile();
        BufferedReader br = new BufferedReader(new FileReader(file));

        String line;
        List<ArrayList<Integer>> scenario = new ArrayList<ArrayList<Integer>>();;
        while ((line = br.readLine()) != null) {

            ArrayList<Integer> lineParams = new ArrayList<Integer>();

            for (String param: line.split(",")) {
                lineParams.add(Integer.parseInt(param));
            }

            scenario.add(lineParams);

        }
        return scenario;

    }

    public List<ArrayList<Integer>> getPlayerScenario() {
        return playerScenario;
    }

    public List<ArrayList<Integer>> getEnemyScenario() {
        return enemyScenario;
    }

}
