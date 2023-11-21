package ru.appline.logic;

import java.util.HashMap;
import java.util.Map;

public class Compass {
    private final static Compass instance = new Compass();
    private static final Map<Integer, String> map = new HashMap<>();

    private Compass() {
    }

    public void fillCompass(String value, String degree) throws NumberFormatException {
        String[] degreeRange = degree.split("-");
        int a = Integer.parseInt(degreeRange[0]);
        int b = Integer.parseInt(degreeRange[1]);
        if ((!(a > 359) && !(b > 359) && !(a < 0) && !(b < 0))) {
            if (a > b) {
                for (int i = a; i <= 359; i++) {
                    map.put(i, value);
                }
                for (int i = 0; i <= b; i++) {
                    map.put(i, value);
                }
            } else if (a < b) {
                for (int i = a; i <= b; i++) {
                    map.put(i, value);
                }
            } else throw new NumberFormatException();
        }
    }

    public static Compass getInstance() {
        return instance;
    }

    public Map<Integer, String> getResults() {
        return map;
    }

    public String getById(int id) {
        return map.get(id);
    }
}


