package com.dmitrykologrivkogmail.todolist.common.util;

import com.dmitrykologrivkogmail.todolist.BaseTest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PreconditionsTest extends BaseTest {

    @Test
    public void testIsNullOrEmpty_whenStringArgumentIsNull_shouldReturnTrue() {
        String arg = null;
        assertEquals(true, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenStringArgumentIsEmpty_shouldReturnTrue() {
        String arg = "";
        assertEquals(true, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenStringArgumentIsNotEmpty_shouldReturnFalse() {
        String arg = "argument";
        assertEquals(false, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenCollectionArgumentIsNull_shouldReturnTrue() {
        List<String> arg = null;
        assertEquals(true, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenCollectionArgumentIsEmpty_shouldReturnTrue() {
        List<String> arg = Collections.emptyList();
        assertEquals(true, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenCollectionArgumentIsNotEmpty_shouldReturnFalse() {
        List<String> arg = new ArrayList<String>() {{
            add("item");
        }};
        assertEquals(false, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenMapArgumentIsNull_shouldReturnTrue() {
        Map<String, String> arg = null;
        assertEquals(true, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenMapArgumentIsEmpty_shouldReturnTrue() {
        Map<String, String> arg = Collections.emptyMap();
        assertEquals(true, Preconditions.isNullOrEmpty(arg));
    }

    @Test
    public void testIsNullOrEmpty_whenMapArgumentIsNotEmpty_shouldReturnFalse() {
        Map<String, String> arg = new HashMap<String, String>() {{
            put("key", "value");
        }};
        assertEquals(false, Preconditions.isNullOrEmpty(arg));
    }

}
