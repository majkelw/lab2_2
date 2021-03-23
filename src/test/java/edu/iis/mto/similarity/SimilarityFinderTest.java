package edu.iis.mto.similarity;

import edu.iis.mto.searcher.SearchResult;
import edu.iis.mto.searcher.SequenceSearcher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimilarityFinderTest {

    @Test
    void testEmptyArrays() {
        int[] array = {};
        int[] array2 = {};
        SequenceSearcher sequenceSearcher = (elem, sequence) -> SearchResult.builder().build();
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(1, finder.calculateJackardSimilarity(array, array2));
    }

    @Test
    void testEqualsArrays() {
        int[] array = {768, 1232, 9123, 50};
        int[] array2 = {768, 1232, 9123, 50};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return  SearchResult.builder().withPosition(0).withFound(true).build();
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(1, finder.calculateJackardSimilarity(array, array2));
    }


    @Test
    void testCompletelyDifferentArrays() {
        int[] array = {0, 2354, 100, 700};
        int[] array2 = {678, 2, 9120, 32, 600};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                return SearchResult.builder().withPosition(0).withFound(false).build();
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(0, finder.calculateJackardSimilarity(array, array2));
    }

    @Test
    void testDifferentArrays() {
        int[] array = {34, 55};
        int[] array2 = {5, 34};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                boolean[] types = {true, false};
                for (int i = 0; i < array.length; i++) {
                    if (elem == array[i]) {
                        searchResult = SearchResult.builder().withPosition(0).withFound(types[i]).build();
                    }
                }
                return searchResult;
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(1.0 / 3.0, finder.calculateJackardSimilarity(array, array2));
    }

    @Test
    void testArraysWithDifferentLengthButWithCommonPart() {
        int[] array = {34, 55, 456, 654, 66, 4};
        int[] array2 = {34, 55, 123};
        SequenceSearcher sequenceSearcher = new SequenceSearcher() {
            @Override
            public SearchResult search(int elem, int[] sequence) {
                SearchResult searchResult = null;
                boolean[] types = {true, true, false, false, false, false};
                for (int i = 0; i < array.length; i++) {
                    if (elem == array[i]) {
                        searchResult = SearchResult.builder().withPosition(0).withFound(types[i]).build();
                    }
                }
                return searchResult;
            }
        };
        SimilarityFinder finder = new SimilarityFinder(sequenceSearcher);
        assertEquals(2.0 / 7.0, finder.calculateJackardSimilarity(array, array2));
    }

}
