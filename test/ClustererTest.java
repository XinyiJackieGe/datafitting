import static org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.Test;
import model.impl.KMeans;

/** JUnit tests for Regression. */
public class ClustererTest {
  public static final double DELTA = 1e-13;

  @Rule public ExpectedException thrown = ExpectedException.none();

  @Test
  public void invalidTestKMeansDataFewerThanK() throws IllegalArgumentException {
    // Insufficient data for KMeans Clusterer fitting.
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Not sufficient data!");

    int k = 3;
    KMeans kMeans = new KMeans(k);
    double[][] testData = {{1.0, 1.0}, {2.0, 2.0}};
    kMeans.fit(testData);
  }
  
  @Test
  public void invalidTestKMeansDataMissing() throws IllegalArgumentException {
    // Too many missing, insufficient data for KMeans Clusterer fitting.
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("Not sufficient data!");

    int k = 3;
    KMeans kMeans = new KMeans(k);
    double[][] testData = {{1.0, 1.0}, {2.0, 2.0}, {3.0, }};
    kMeans.fit(testData);
  }

  @Test
  public void invalidTestKMeansKEqualsZero() throws IllegalArgumentException {
    // k cannot be zero.
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("k cannot be non-positive!");
    int k = 0;
    new KMeans(k);
  }

  @Test
  public void invalidTestKMeanskNegative() throws IllegalArgumentException {
    // k cannot be negative.
    thrown.expect(IllegalArgumentException.class);
    thrown.expectMessage("k cannot be non-positive!");
    int k = -1;
    new KMeans(k);
  }

  @Test
  public void testKMeansKEqualsOne() {
    int k = 1;
    KMeans kMeans = new KMeans(k);
    double[][] testData = {
      {1.5, 2.0}, {1.5, 1.5}, {2.0, 1.5}, {2.0, 2.5}, {1.8, 5.0},
      {2.2, 2.2}, {3.0, 2.0}, {2.5, 4.0}, {4.0, 5.0}, {5.0, 3.0}
    };
    kMeans.fit(testData);
    double[][] para = kMeans.getParameters();
    assertEquals(1, para.length);
    assertEquals(2.55, para[0][0], 1.0);
    assertEquals(2.87, para[0][1], 1.0);
    assertEquals(0, kMeans.cluster(new double[] {2.2, 2.2}));
    assertEquals(0, kMeans.cluster(new double[] {4.5, 5.0}));
  }

  @Test
  public void testKMeansKequalsTwo() {
    int k = 2;
    KMeans kMeans = new KMeans(k);
    double[][] testData = {
      {1.5, 2.0}, {1.5, 1.5}, {2.0, 1.5}, {2.0, 2.5}, {1.8, 5.0},
      {2.2, 2.2}, {3.0, 2.0}, {2.5, 4.0}, {4.0, 5.0}, {5.0, 3.0},
      {150.5, 250.6}, {140.0, 252.0}, {145.0, 245.0}, {155.0, 255.0}, {150.0, 240.0},
      {164.0, 253.0}, {160.0, 260.0}, {170.0, 270.0}, {175.0, 245.0}, {160.0, 270.0}
    };
    kMeans.fit(testData);
    double[][] para = kMeans.getParameters();
    assertEquals(2, para.length);
    // Clusters are randomly set.
    if (para[0][0] > 100 && para[0][1] > 100 && para[1][0] < 10 && para[1][1] < 10) {
      assertEquals(2.55, para[1][0], 1.0);
      assertEquals(2.87, para[1][1], 1.0);
      assertEquals(156.95, para[0][0], 1.0);
      assertEquals(254.06, para[0][1], 1.0);
      // Training instances.
      assertEquals(1, kMeans.cluster(new double[] {1.5, 2.0}));
      assertEquals(0, kMeans.cluster(new double[] {160, 260}));
      // New instances.
      assertEquals(1, kMeans.cluster(new double[] {10, 12.0}));
      assertEquals(0, kMeans.cluster(new double[] {140, 280}));
    } else {
      assertEquals(2.55, para[0][0], 1.0);
      assertEquals(2.87, para[0][1], 1.0);
      assertEquals(156.95, para[1][0], 1.0);
      assertEquals(254.06, para[1][1], 1.0);
      // Training instances.
      assertEquals(0, kMeans.cluster(new double[] {1.5, 2.0}));
      assertEquals(1, kMeans.cluster(new double[] {160, 260}));
      // New instances.
      assertEquals(0, kMeans.cluster(new double[] {10, 12.0}));
      assertEquals(1, kMeans.cluster(new double[] {140, 280}));
    }
  }
}
