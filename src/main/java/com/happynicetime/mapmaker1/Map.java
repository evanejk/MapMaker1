/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happynicetime.mapmaker1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evane
 */
public class Map {
    static float mapSizeX;
    static float mapSizeZ;
    LinkedList<Building> buildings = new LinkedList<>();
    public Map(float mapSizeX, float mapSizeZ) {
        this.mapSizeX = mapSizeX;
        this.mapSizeZ = mapSizeZ;
        makeBuilding:
        for(int buildingNum = 0;buildingNum < 500;buildingNum++){//to crowd houses use bigger number than 500
            float buildingCenterX = randFloat(-mapSizeX,mapSizeX);
            float buildingCenterZ = randFloat(-mapSizeZ,mapSizeZ);
            float buildingSizeX = randFloat(5,20);
            float buildingSizeZ = randFloat(5,20);
            //check if overlapping
            //find corners
            float c1X = buildingCenterX - buildingSizeX;//OO
            float c1Z = buildingCenterZ - buildingSizeZ;//XO

            float c2X = buildingCenterX + buildingSizeX;//OO
            float c2Z = buildingCenterZ - buildingSizeZ;//OX

            float c3X = buildingCenterX - buildingSizeX;//XO
            float c3Z = buildingCenterZ + buildingSizeZ;//OO

            float c4X = buildingCenterX + buildingSizeX;//OX
            float c4Z = buildingCenterZ + buildingSizeZ;//OO
            for(Building building:buildings){
                //check if overlaps existing building
                for(float x = c1X - 2 ; x <= c2X + 2 ; x += .1f){//seperate houses with gap
                    for(float z = c1Z - 2; z <= c3Z + 2 ; z+=.1f){
                        if(x < building.c2X && x > building.c1X && z < building.c3Z && z > building.c1Z)
                            continue makeBuilding;//overlapping so skip
                    }
                }
            }
            Building b = new Building(buildingCenterX,buildingCenterZ,buildingSizeX,buildingSizeZ);
            buildings.add(b);
        }
    }
    static Random random = new Random();
    private float randFloat(float low, float high) {
        return low + (random.nextFloat() * (high - low));
    }

    void exportObjs() {
        int houseNum = 0;
        for(Building building:buildings){
            StringBuilder text = new StringBuilder();
            text.append(building.printObjPoints());
            text.append(building.printObjTriangles());
            try {
                houseNum++;
                FileWriter outputStream = new FileWriter("house"+houseNum+".obj");
                outputStream.write(text.toString());
                outputStream.flush();
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
