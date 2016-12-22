package home.maintenance.dao;

import org.junit.Test;

/**
 * Created by Buibi on 22.12.2016.
 */
public class MaximumDelta {

    @Test
    public void calc() throws Exception {
        int[] data = {220, 210, 230, 190, 170, 185, 215, 225, 195, 170, 150, 140, 165, 180, 205, 170, 150, 130, 120, 130, 140, 160, 150};

        if (data.length == 1) {
            print(data[0], data[0], 0);
            return;
        }

        int min = data[0], max = data[1];
        int delta = 0;

        for (int min_i = 0, max_i = 1; min_i < data.length - 1 && max_i < data.length; min_i++, max_i++) {
            if (data[min_i] < min) {
                min = data[min_i];
                max = data[max_i];
            }

            if (data[max_i] > max) {
                max = data[max_i];
            }

            if (max - min > delta) {
                delta = max - min;
                print(min, max, delta);
            }

        }

    }

    private void print(int min, int max, int delta) {
        System.out.println(String.format("Min = %s, max = %s; Maximum delta = %s", min, max, delta));
    }

}
