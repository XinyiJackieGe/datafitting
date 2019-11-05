import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import model.Clusterer;
import model.Regression;
import model.impl.KMeans;
import model.impl.LinearRegression;
import plot.Plotter;
import plot.impl.ClustererPlotter;
import plot.impl.RegressionPlotter;

public class Runner {
  public static void main(String[] args) throws IOException {
    String filePath = "input/linedata-1.txt"; // FIXME
    double[][] data = dataset.DataSet.load(filePath);
    Regression linearRegression = new LinearRegression();
    linearRegression.fit(data);
   
    String linearRegressionOutputPath = "output/linear.png";
    Plotter<Regression> regressionPlotter = new RegressionPlotter();
    regressionPlotter.plot(linearRegression, data, linearRegressionOutputPath);

    //    double a = 2.0;
    //    double b = 1.5;
    //    double c = 3.5;
    //
    //    Random rand = new Random();
    //    double minX = -20.0;
    //    double maxX = 50;
    //    double minY = -c / b - a / b * minX;
    //    double maxY = -c / b - a / b * maxX;
    //    double[][] dataTest = new double[100][2];
    //    for (int i = 0; i < 100; i++) {
    //      dataTest[i][0] = minX + (maxX - misnX) * rand.nextDouble();
    //      dataTest[i][1] = -c / b - a / b * dataTest[i][0];
    //    }
    //    Regression linearRegressionTest = new LinearRegression();
    //    linearRegressionTest.fit(dataTest);
    //    System.out.println( linearRegressionTest.getParameters()[0]);
    //    System.out.println( linearRegressionTest.getParameters()[1]);
    //    System.out.println( linearRegressionTest.getParameters()[2]);
    //    String linearRegressionOutputPath = "output/linearTest.png";
    //    Plotter<Regression> regressionPlotter = new RegressionPlotter();
    //    regressionPlotter.plot(linearRegressionTest, dataTest, linearRegressionOutputPath);
    //

    //    String filePath = "input/clusterdata-6.txt"; // FIXME
    //    double[][] data = dataset.DataSet.load(filePath);
    //    int k = 6;
    //    KMeans kMeans = new KMeans(k);
    //    kMeans.fit(data);
    //    String kMeansOutputPath = "output/cluster.png"; // FIXME
    //    Plotter<Clusterer> clustererPlotter = new ClustererPlotter();
    //    clustererPlotter.plot(kMeans, data, kMeansOutputPath);

    System.out.println("Done");
  }
}
