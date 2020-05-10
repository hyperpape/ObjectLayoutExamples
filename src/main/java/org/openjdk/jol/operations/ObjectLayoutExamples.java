package org.openjdk.jol.operations;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.text.SimpleDateFormat;
import java.util.*;

public class ObjectLayoutExamples {

    public static void main(String[] args) {
        try {
            if (args.length < 1) {
                System.err.println("No operation specified");
                System.exit(1);
            }
            if (args[0].equals("builtin")) {
                printBuiltinInternals();
            }
            if (args[0].equals("arrays")) {
                printArrays();
            }
            if (args[0].equals("strings")) {
                printStrings();
            }
            if (args[0].equals("custom")) {
                printCustomInternals();
            }
            if (args[0].equals("collections")) {
                printCollections();
            }
            if (args[0].equals("misc")) {
                printMisc();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    private static void printMisc() {
        List<String> externals = new ArrayList<>();

        addExternals(externals, new SimpleDateFormat("hh mm ss"), "SimpleDateFormat(hh mm ss)");
        externals.forEach(System.out::println);
    }

    private static void printStrings() {
        var externals = new ArrayList<String>();

        addExternals(externals, "", "\"\"");
        addExternals(externals, "1", "\"1\"");
        addExternals(externals, "劫", "劫");
        addExternals(externals, "井山 裕太", "井山 裕太");
        addExternals(externals, "01234567", "\"01234567\"");
        addExternals(externals, "012345678", "\012345678\"");

        externals.forEach(System.out::println);
    }

    private static void printCollections() {
        List<String> externals = new ArrayList<>();

        addListExternals(externals,0, false);
        addListExternals(externals, 1, false);
        addListExternals(externals, 10, false);
        addListExternals(externals, 11, false);
        addListExternals(externals, 16, false);
        // ArrayList grows by a factor of 1.5
        // So capacities are: 1, 10, 15, 22, 33, 49, 73, 109, 163, 244, 366, 549, 823
        addListExternals(externals, 825, false);

        addListExternals(externals,0, true);
        addListExternals(externals, 1, true);
        addListExternals(externals, 10, true);
        addListExternals(externals, 11, true);
        addListExternals(externals, 16, true);
        addListExternals(externals, 825, true);

        addHashMapExternals(externals, 0, false);
        addHashMapExternals(externals, 1, false);
        addHashMapExternals(externals, 16, false);
        addHashMapExternals(externals, 769, false);
        addHashMapExternals(externals, 1536, false);

        externals.forEach(System.out::println);
    }

    private static void addListExternals(List<String> externals, int count, boolean ensureCapacity) {
        ArrayList<Integer> list = new ArrayList<>();
        if (ensureCapacity) {
            list.ensureCapacity(count);
        }

        for (var i = 0; i < count; i++) {
            list.add(i);
        }
        String msg = count + " item list" + (ensureCapacity ? "ensureCapacity=true" : "");
        addExternals(externals, list, msg);
    }

    private static void addHashMapExternals(List<String> externals, int count, boolean ensureCapacity) {
        var map = ensureCapacity ? new HashMap<>(count) : new HashMap<>();

        for (var i = 0; i < count; i++) {
            map.put(i, i);
        }
        addExternals(externals, map, "Label " + count + " item map" + (ensureCapacity ? "ensureCapacity=true" : ""));
    }

    static void printCustomInternals() throws Exception {
    }

    static void printBuiltinInternals() throws Exception {
        var internals = new ObjectInternals();
        List<String> sizes = new ArrayList<>();

        sizes.add(internalSize(internals, Object.class));
        sizes.add(internalSize(internals, Boolean.class));
        sizes.add(internalSize(internals, Character.class));
        sizes.add(internalSize(internals, Integer.class));
        sizes.add(internalSize(internals, Float.class));
        sizes.add(internalSize(internals, Boolean.class));
        sizes.add(internalSize(internals, Double.class));
        sizes.add(internalSize(internals, Long.class));
        sizes.forEach(System.out::println);
    }

    static void printArrays() {
        List<String> sizes = new ArrayList<>();
        var internals = new ObjectInternals();

        sizes.add(objectSize(internals, new boolean[0], "boolean[0]"));
        sizes.add(objectSize(internals, new boolean[1], "boolean[1]"));
        sizes.add(objectSize(internals, new boolean[8], "boolean[8]"));
        sizes.add(objectSize(internals, new boolean[9], "boolean[9]"));
        sizes.add(objectSize(internals, new boolean[1024], "boolean[1024]"));

        sizes.add(objectSize(internals, new byte[0], "byte[0]"));
        sizes.add(objectSize(internals, new byte[1], "byte[1]"));
        sizes.add(objectSize(internals, new byte[8], "byte[8]"));
        sizes.add(objectSize(internals, new byte[9], "byte[9]"));
        sizes.add(objectSize(internals, new byte[1024], "byte[1024]"));

        sizes.add(objectSize(internals, new char[0], "char[0]"));
        sizes.add(objectSize(internals, new char[1], "char[1]"));
        sizes.add(objectSize(internals, new char[8], "char[8]"));
        sizes.add(objectSize(internals, new char[9], "char[9]"));
        sizes.add(objectSize(internals, new char[1024], "char[1024]"));

        sizes.add(objectSize(internals, new int[0], "int[0]"));
        sizes.add(objectSize(internals, new int[1], "int[1]"));
        sizes.add(objectSize(internals, new int[8], "int[8]"));
        sizes.add(objectSize(internals, new int[9], "int[9]"));
        sizes.add(objectSize(internals, new int[1024], "int[1024]"));

        sizes.add(objectSize(internals, new long[0], "long[0]"));
        sizes.add(objectSize(internals, new long[1], "long[1]"));
        sizes.add(objectSize(internals, new long[8], "long[8]"));
        sizes.add(objectSize(internals, new long[9], "long[9]"));
        sizes.add(objectSize(internals, new long[1024], "long[1024]"));

        sizes.add(objectSize(internals, new Object[0], "Object[0]"));
        sizes.add(objectSize(internals, new Object[1], "Object[1]"));
        sizes.add(objectSize(internals, new Object[8], "Object[8]"));
        sizes.add(objectSize(internals, new Object[9], "Object[9]"));
        sizes.add(objectSize(internals, new Object[1024], "Object[1024]"));

        sizes.forEach(System.out::println);
    }

    static String objectSize(ObjectInternals internals, Object o, String label) {
        return label + ": " + ClassLayout.parseInstance(o).instanceSize();
    }

    static String internalSize(ObjectInternals internals, Class<?> klass) throws Exception {
        Object o = internals.tryInstantiate(klass);
        return klass.getCanonicalName() + ": " + ClassLayout.parseInstance(o).instanceSize();
    }

    static String externals(Object o) {
	// Note: System.gc() is not required. It just (sometimes) makes the output more readable, when it reorders
	// the objects from the graph to be contiguous in memory, instead of having unrelated objects mixed in.
        System.gc();
        return GraphLayout.parseInstance(new Object[]{o}).toPrintable();
    }

    static void addExternals(List<String> output, Object o, String label) {
        output.add("Label " + label);
        output.add(GraphLayout.parseInstance(new Object[] {o}).toPrintable());
    }
}
