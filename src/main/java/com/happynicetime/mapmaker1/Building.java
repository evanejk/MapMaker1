/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happynicetime.mapmaker1;

import java.util.LinkedList;

/**
 *
 * @author evane
 */
class Building {

    final float c1X;
    final float c1Z;
    final float c2X;
    final float c2Z;
    final float c3X;
    final float c3Z;
    final float c4X;
    final float c4Z;
    
    int pointNumber = 1;
    LinkedList<Point> points = new LinkedList<>();
    LinkedList<Triangle> triangles = new LinkedList<>();

    Building(float buildingCenterX, float buildingCenterZ, float buildingSizeX, float buildingSizeZ) {
        c1X = buildingCenterX - buildingSizeX;//OO
        c1Z = buildingCenterZ - buildingSizeZ;//XO

        c2X = buildingCenterX + buildingSizeX;//OO
        c2Z = buildingCenterZ - buildingSizeZ;//OX

        c3X = buildingCenterX - buildingSizeX;//XO
        c3Z = buildingCenterZ + buildingSizeZ;//OO

        c4X = buildingCenterX + buildingSizeX;//OX
        c4Z = buildingCenterZ + buildingSizeZ;//OO
        
        //make floor
        buildBox(c1X,c2X,0,.1f,c1Z,c3Z);
        //make roof
        buildRoof(c1X - .5f,c2X + .5f,3f,7f,c1Z - .5f,c3Z + .5f);
        //make walls
        if(Map.random.nextInt() % 3 == 0){//make wall 1 with door
            if(Map.random.nextInt() % 2 == 0)
                buildBox(c1X, c1X + .2f, 0, 3f, c1Z + 4f, c4Z);
            else
                buildBox(c1X, c1X + .2f, 0, 3f, c1Z, c4Z - 4f);
        }else{//make wall 1
            buildBox(c1X, c1X + .2f, 0, 3f, c1Z , c4Z);
        }
        if(Map.random.nextInt() % 3 == 0){//make wall 2 with door
            if(Map.random.nextInt() % 2 == 0)
                buildBox(c2X - .2f, c2X, 0, 3f, c1Z + 4f, c4Z);
            else
                buildBox(c2X - .2f, c2X, 0, 3f, c1Z, c4Z - 4f);
        }else{//make wall 2
            buildBox(c2X - .2f, c2X, 0, 3f, c1Z, c4Z);
        }
        if(Map.random.nextInt() % 3 == 0){//make wall 3 with door
            if(Map.random.nextInt() % 2 == 0)
                buildBox(c1X + 4f, c2X, 0, 3f, c4Z - .2f, c4Z);
            else
                buildBox(c1X, c2X - 4f, 0, 3f, c4Z - .2f, c4Z);
        }else{//make wall 3
            buildBox(c1X, c2X, 0, 3f, c4Z - .2f, c4Z);
        }
        if(Map.random.nextInt() % 3 == 0){//make wall 4 with door
            if(Map.random.nextInt() % 2 == 0)
                buildBox(c1X + 4f, c2X, 0, 3f, c1Z, c1Z + .2f);
            else
                buildBox(c1X, c2X - 4f, 0, 3f, c1Z, c1Z + .2f);
        }else{//make wall 4
            buildBox(c1X, c2X, 0, 3f, c1Z, c1Z + .2f);
        }
    }

    StringBuilder printObjTriangles() {
        StringBuilder sb = new StringBuilder();
        for(Triangle tri:triangles){
            sb.append("f "+tri.point1.pointID+" "+tri.point2.pointID+" "+tri.point3.pointID+"\n");
        }
        return sb;
    }

    private void buildBox(float lowX, float highX, float lowY, float highY, float lowZ, float highZ) {
        Point p1 = new Point(lowX,highY,lowZ,pointNumber++);
        //OO
        //XO
        Point p2 = new Point(highX,highY,lowZ,pointNumber++);
        //OO
        //OX
        Point p3 = new Point(lowX,highY,highZ,pointNumber++);
        //XO
        //OO
        Point p4 = new Point(highX,highY,highZ,pointNumber++);
        //OX
        //OO
        Point p5 = new Point(lowX,lowY,lowZ,pointNumber++);
        //OO
        //XO
        Point p6 = new Point(highX,lowY,lowZ,pointNumber++);
        //OO
        //OX
        Point p7 = new Point(lowX,lowY,highZ,pointNumber++);
        //XO
        //OO
        Point p8 = new Point(highX,lowY,highZ,pointNumber++);
        //OX
        //OO
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        triangles.add(new Triangle(p1,p3,p2));
        triangles.add(new Triangle(p4,p2,p3));
        triangles.add(new Triangle(p1,p6,p5));
        triangles.add(new Triangle(p6,p1,p2));
        triangles.add(new Triangle(p2,p8,p6));
        triangles.add(new Triangle(p4,p8,p2));
        triangles.add(new Triangle(p3,p5,p7));
        triangles.add(new Triangle(p1,p5,p3));
        triangles.add(new Triangle(p4,p7,p8));
        triangles.add(new Triangle(p3,p7,p4));
        triangles.add(new Triangle(p5,p8,p7));
        triangles.add(new Triangle(p6,p8,p5));
    }
    static final float SCALE = 4;
    StringBuilder printObjPoints() {
        StringBuilder sb = new StringBuilder();
        for(Point p:points){
            sb.append("v "+p.x * SCALE+" "+p.y * SCALE+" "+p.z * SCALE+"\n");
        }
        return sb;
    }

    private void buildRoof(float lowX, float highX, float lowY, float highY, float lowZ, float highZ) {
        if(highX - lowX > highZ - lowZ){
            //lengthyX
            float middleZ = (lowZ + highZ) / 2f;
            Point p1 = new Point(lowX,highY,middleZ,pointNumber++);
            Point p2 = new Point(highX,highY,middleZ,pointNumber++);
            Point p3 = new Point(lowX,lowY,lowZ,pointNumber++);
            Point p4 = new Point(highX,lowY,lowZ,pointNumber++);
            Point p5 = new Point(lowX,lowY,highZ,pointNumber++);
            Point p6 = new Point(highX,lowY,highZ,pointNumber++);
            points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(p4);
            points.add(p5);
            points.add(p6);
            triangles.add(new Triangle(p1,p4,p3));
            triangles.add(new Triangle(p1,p2,p4));
            triangles.add(new Triangle(p2,p6,p4));
            triangles.add(new Triangle(p1,p3,p5));
            triangles.add(new Triangle(p2,p5,p6));
            triangles.add(new Triangle(p2,p1,p5));
            triangles.add(new Triangle(p3,p6,p5));
            triangles.add(new Triangle(p3,p4,p6));
        }else{
            //lengthyZ
            float middleX = (lowX + highX) / 2f;
            Point p1 = new Point(middleX,highY,lowZ,pointNumber++);
            Point p2 = new Point(middleX,highY,highZ,pointNumber++);
            Point p3 = new Point(lowX,lowY,lowZ,pointNumber++);
            Point p4 = new Point(highX,lowY,lowZ,pointNumber++);
            Point p5 = new Point(lowX,lowY,highZ,pointNumber++);
            Point p6 = new Point(highX,lowY,highZ,pointNumber++);
            points.add(p1);
            points.add(p2);
            points.add(p3);
            points.add(p4);
            points.add(p5);
            points.add(p6);
            triangles.add(new Triangle(p1,p4,p3));
            triangles.add(new Triangle(p2,p5,p6));
            triangles.add(new Triangle(p1,p6,p4));
            triangles.add(new Triangle(p1,p2,p6));
            triangles.add(new Triangle(p2,p3,p5));
            triangles.add(new Triangle(p2,p1,p3));
            triangles.add(new Triangle(p3,p6,p5));
            triangles.add(new Triangle(p3,p4,p6));
            
            
        }
    }
    
}
