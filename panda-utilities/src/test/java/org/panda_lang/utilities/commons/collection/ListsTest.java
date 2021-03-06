/*
 * Copyright (c) 2015-2020 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.utilities.commons.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

final class ListsTest {

    private static final List<String> LIST = Arrays.asList("a", "b", "c");

    @Test
    void get() {
        Assertions.assertEquals(3, LIST.size());

        Assertions.assertAll(
                () -> Assertions.assertNotNull(Lists.get(LIST, 0)),
                () -> Assertions.assertNotNull(Lists.get(LIST, 2)),

                () -> Assertions.assertNull(Lists.get(LIST, -1)),
                () -> Assertions.assertNull(Lists.get(LIST, 3))
        );
    }

    @Test
    void subList() {
        List<String> subList = Lists.subList(LIST, 1);
        Assertions.assertEquals(Arrays.asList("b", "c"), subList);

        ArrayList<String> mutList = Lists.subList(LIST, 0, LIST.size());
        for (String element : LIST) {
            Assertions.assertDoesNotThrow(() -> mutList.add(element));
        }
        Assertions.assertEquals(Arrays.asList("a", "b", "c", "a", "b", "c"), mutList);
    }

    @Test
    void sort() {
        List<Integer> a = Arrays.asList(3, 2, 1);
        List<Integer> b = Arrays.asList(3, 2, 1);
        Lists.sort(Comparator.naturalOrder(), a, b);

        List<Integer> expected = Arrays.asList(1, 2, 3);
        Assertions.assertEquals(expected, a);
        Assertions.assertEquals(expected, b);
    }

    @Test
    void reverse() {
        Assertions.assertEquals(Arrays.asList("c", "b", "a"), Lists.reverse(Lists.mutableOf("a", "b", "c")));
    }

    @Test
    void split() {
        List<String>[] lists = Lists.split(LIST, "b");

        Assertions.assertEquals(2, lists.length);
        Assertions.assertAll(
                () -> Assertions.assertEquals(Collections.singletonList("a"), lists[0]),
                () -> Assertions.assertEquals(Collections.singletonList("c"), lists[1])
        );
    }

    @Test
    void mutableOf() {
        List<String> list = Lists.mutableOf("a", "b");
        list.add("c");

        Assertions.assertEquals(Arrays.asList("a", "b", "c"), list);
    }

    @Test
    void add() {
        Assertions.assertEquals("a", Lists.add(Lists.mutableOf(), "a"));
    }

}