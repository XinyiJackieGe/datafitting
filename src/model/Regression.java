package model;

public interface Regression extends Model {
  double regress(double instance);

  double[] getParameters();
}
