import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import model.Regression;
import model.impl.LinearRegression;

/** JUnit tests for Regression. */
public class RegressionTest {
  public static final double DELTA = 1e-13;

  @Test
  public void testLinearRegression1() {
    // linear regression: y = x
    Regression linearRegression = new LinearRegression();
    double[][] testData = {
      {1.0, 1.0}, {2.0, 2.0}, {3.0, 3.0}, {4.0, 4.0}, {5.0, 5.0},
      {6.0, 6.0}, {7.0, 7.0}, {8.0, 8.0}, {9.0, 9.0}, {10.0, 10.0},
      {11.0, 11.0}, {12.0, 12.0}, {13.0, 13.0}, {14.0, 14.0}, {15.0, 15.0},
      {16.0, 16.0}, {17.0, 17.0}, {18.0, 18.0}, {19.0, 19.0}, {20.0, 20.0},
      {16.0, 16.0}, {17.0, 17.0}, {18.0, 18.0}, {19.0, 19.0}, {20.0, 20.0},
      {16.0, 16.0}, {17.0, 17.0}, {18.0, 18.0}, {19.0, 19.0}, {20.0, 20.0}
    };
    linearRegression.fit(testData);
    double[] para = linearRegression.getParameters();

    assertEquals(2, para.length);
    assertEquals(0.0, para[0], DELTA);
    assertEquals(1.0, para[1], DELTA);
    assertEquals(3.0, linearRegression.regress(3.0), DELTA);
    assertEquals(0.0, linearRegression.regress(0.0), DELTA);
  }

  @Test
  public void testLinearRegression2() {
    Regression linearRegression = new LinearRegression();
    double[][] testData = {
      {1.0, 1.0}, {2.0, 2.0}, {3.0, 3.0}, {4.0, 4.0}, {5.0, 5.0},
      {6.0, 6.0}, {7.0, 7.0}, {8.0, 8.0}, {9.0, 9.0}, {10.0, 10.0},
      {1.0, 2.0}, {2.0, 3.0}, {3.0, 4.0}, {4.0, 5.0}, {5.0, 6.0},
      {6.0, 7.0}, {7.0, 8.0}, {8.0, 9.0}, {9.0, 10.0}, {10.0, 11.0},
      {16.0, 16.0}, {17.0, 17.0}, {18.0, 18.0}, {19.0, 19.0}, {20.0, 20.0},
      {16.0, 16.0}, {17.0, 17.0}, {18.0, 18.0}, {19.0, 19.0}, {20.0, 20.0}
    };
    linearRegression.fit(testData);
    double[] para = linearRegression.getParameters();
    assertEquals(2, para.length);
    assertEquals(0.6616847826086989, para[0], DELTA);
    assertEquals(0.9660326086956519, para[1], DELTA);
    assertEquals(3.5597826086956545, linearRegression.regress(3), DELTA);
    assertEquals(0.6616847826086989, linearRegression.regress(0.0), DELTA);
  }

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void invalidTestLinearRegression1() throws IllegalArgumentException {
    // insufficient data for linearRegression
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Insufficient data for Linear Regression fitting!");
    Regression linearRegression = new LinearRegression();
    double[][] testData = {
      {1.0, 1.0}, {2.0, 2.0}, {3.0, 3.0}, {4.0, 4.0}, {5.0, 5.0},
      {6.0, 6.0}, {7.0, 7.0}, {8.0, 8.0}, {9.0, 9.0}, {10.0, 10.0},
      {11.0, 11.0}, {12.0, 12.0}, {13.0, 13.0}, {14.0, 14.0}, {15.0, 15.0}
    };
    linearRegression.fit(testData);
  }

  @Test
  public void invalidTestLinearRegression2() throws IllegalArgumentException {
    // data with too much missing values for linear regression
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Insufficient data for Linear Regression fitting!");
    Regression linearRegression = new LinearRegression();
    double[][] testData = {
      {1.0, 1.0, 1.0},
      {
        2.0,
      },
      {3.0, 3.0},
      {4.0, 4.0},
      {5.0, 5.0, 5.4},
      {6.0, 6.0, 3.6},
      {7.0, 7.0},
      {
        8.0,
      },
      {9.0, 9.0},
      {10.0, 10.0},
      {1.0, 2.0, 3.4},
      {2.0, 3.0},
      {
        3.0,
      },
      {4.0, 5.0},
      {5.0, 6.0},
      {6.0, 7.0},
      {7.0, 8.0},
      {
        8.0,
      },
      {9.0, 10.0},
      {10.0, 11.0},
      {16.0, 16.0},
      {
        17.0,
      },
      {
        18.0,
      },
      {19.0, 19.0},
      {
        20.0,
      },
      {16.0, 16.0},
      {
        17.0,
      },
      {
        18.0,
      },
      {
        19.0,
      },
      {
        20.0,
      }
    };
    linearRegression.fit(testData);
  }
}
