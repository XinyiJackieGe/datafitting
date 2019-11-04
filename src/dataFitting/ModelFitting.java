package dataFitting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import dataFitting.input.Dataset;

public class ModelFitting {
  public static void main(String[] args) throws Exception {
    String dataSetPath = "";
    double[][]data = Dataset.load(dataSetPath);
    
    Model<Double> lr = new LinearRegressionImpl();
    lr.fit(data);
    String linearRegressionOutputPath = "";
    //lr.plot(linearRegressionOutputPath);
    
    int k = 3;
    Model<Point> kMeans = new KMeansClusteringImpl(k);
    String kMeansOutputPath = "";
    //kMeans.plot(kMeansOutputPath);
    
    /// /////////////
    //new ControllerImplt(new InputStreamReader(System.in), System.out).go(new Calculator());
//    Model<Double> lr = new LinearRegressionImpl();
//    //View view = new View();
//    String filename1 = "input/linedata-1.txt";
//    BufferedReader buffer1 = new BufferedReader(new FileReader(filename1));
//    new ControllerImpl(buffer1, System.out).go(lr);
//    
//    Model<Point> km = new KMeansClusteringImpl(3);
//    //View view = new View();
//    String filename2 = "input/clusterdata-2.txt";
//    BufferedReader buffer2 = new BufferedReader(new FileReader(filename2));
//    new ControllerImpl(buffer2, System.out).go(km);
    
    
  }
}

