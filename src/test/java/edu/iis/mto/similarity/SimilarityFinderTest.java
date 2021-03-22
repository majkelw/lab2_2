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
        assertEquals(1,finder.calculateJackardSimilarity(array, array2));
    }

}
