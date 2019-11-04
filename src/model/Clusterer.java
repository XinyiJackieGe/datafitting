package model;

public interface Clusterer extends Model {
  int cluster(double[] instance);
  
  double[][] getParameters();
}
