package pl.karbi.demo.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.karbi.android.annotation.ParcelableProxy;
import android.os.Parcel;

public class ParcelTest {

    public ParcelTest() {

        final Test parcelableTest = new Test() {

            @Override
            public int test() {
                return singleParcelableTest();
            }
        };

        final Test nonParcelableTest = new Test() {

            @Override
            public int test() {
                return singleNonParcelableTest();
            }

        };

        LOG.info("-- parcelable warmup");
        testSet(WARMUP_ITERATIONS, parcelableTest);

        LOG.info("-- parcelable test");
        testSet(TEST_ITERATIONS, parcelableTest);

        LOG.info("-- non-parcelable warmup");
        testSet(WARMUP_ITERATIONS, nonParcelableTest);

        LOG.info("-- non-parcelable test");
        testSet(TEST_ITERATIONS, nonParcelableTest);
    }

    private void testSet(final long iterations, final Test test) {
        int x = 0;

        final long start = System.currentTimeMillis();
        for (int i = 0; i < iterations; ++i) {
            x += test.test();
        }
        LOG.info("-- {} iterations took {} ms", iterations, System.currentTimeMillis() - start);

        LOG.info("x == {}", x);
    }

    private int singleNonParcelableTest() {
        final Parcel parcel = Parcel.obtain();

        parcel.writeParcelable(new ParcelableProxy(new ParcelableObject("default value")), 0);

        parcel.setDataPosition(0);

        final ParcelableProxy proxy = parcel.readParcelable(ParcelableProxy.class.getClassLoader());
        parcel.recycle();

        return proxy.getObject().hashCode();
    }

    private int singleParcelableTest() {
        final Parcel parcel = Parcel.obtain();

        parcel.writeParcelable(new ParcelableObject("default value"), 0);

        parcel.setDataPosition(0);

        final ParcelableObject deparceled = parcel.readParcelable(ParcelableObject.class.getClassLoader());

        parcel.recycle();

        return deparceled.hashCode();
    }

    private interface Test {
        int test();
    }

    private static final long WARMUP_ITERATIONS = 10000;
    private static final long TEST_ITERATIONS = 40000;

    private static final Logger LOG = LoggerFactory.getLogger(ParcelTest.class);

}
