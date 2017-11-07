package com.dmitrykologrivkogmail.todolist.common.util;

import java.util.Collection;
import java.util.Map;

public final class Preconditions {

    public static boolean isNullOrEmpty(String arg) {
        return arg == null || arg.isEmpty();
    }

    public static boolean isNullOrEmpty(Collection arg) {
        return arg == null || arg.isEmpty();
    }

    public static boolean isNullOrEmpty(Map arg) {
        return arg == null || arg.isEmpty();
    }

}
